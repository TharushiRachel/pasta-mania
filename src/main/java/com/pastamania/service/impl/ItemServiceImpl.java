package com.pastamania.service.impl;

import com.pastamania.component.RestApiClient;
import com.pastamania.configuration.ConfigProperties;
import com.pastamania.dto.response.ItemResponse;
import com.pastamania.entity.Company;
import com.pastamania.entity.Item;
import com.pastamania.entity.ItemVariant;
import com.pastamania.entity.VariantStore;
import com.pastamania.repository.CompanyRepository;
import com.pastamania.repository.ItemRepository;
import com.pastamania.repository.ItemVariantRepository;
import com.pastamania.repository.VariantStoreRepository;
import com.pastamania.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Pasindu Lakmal
 */
@Service
@Transactional
@Slf4j
public class ItemServiceImpl implements ItemService {


    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemVariantRepository itemVariantRepository;

    @Autowired
    VariantStoreRepository variantStoreRepository;

    @Autowired
    private RestApiClient restApiClient;

    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private ConfigProperties configProperties;


    @Override
    public void retrieveItemAndPersist(Date date, Company company) throws ParseException {

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        df.setTimeZone(tz); // get the time zone
        String nowAsISO = df.format(date);

        List<Item> lastUpdatedItems = itemRepository.findItemWithMaxUpdatedDateAndCompany(company);
        List<Item> finalCreatedItem = itemRepository.findItemWithMinCreatedDateAndCompany(company);


        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + company.getToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        var val = "";
        if (finalCreatedItem.isEmpty()) {
            val = nowAsISO;
        } else {
            String updatedAt = finalCreatedItem.get(0).getCreatedAt();
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date convertedDate = sourceFormat.parse(updatedAt);
            Calendar c = Calendar.getInstance();
            c.setTime(convertedDate);
            c.add(Calendar.SECOND, -1);
            val = sourceFormat.format(c.getTime());

        }
        ResponseEntity<ItemResponse> itemResponseResponseEntity = restApiClient.getRestTemplate().exchange(configProperties.getLoyvers().getBaseUrl() + "items?created_at_min=1998-02-28T23:11:13.962Z&created_at_max=" + val + "&limit=100", HttpMethod.GET, entity, ItemResponse.class);
        Company companyOp = companyRepository.findById(company.getId()).get();
        saveItemWithMappedValues(modelMapper, itemResponseResponseEntity, companyOp);

        if (itemResponseResponseEntity.getBody().getItems().isEmpty()) {
            String updatedAt = lastUpdatedItems.get(0).getUpdatedAt();
            SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sourceFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date convertedDate = sourceFormat.parse(updatedAt);
            Calendar c = Calendar.getInstance();
            c.setTime(convertedDate);
            c.add(Calendar.SECOND, 1);
            String oneSecondAddedDate = sourceFormat.format(c.getTime());
            ResponseEntity<ItemResponse> itemResponseResponseEntityUpdated = restTemplate.exchange("https://api.loyverse.com/v1.0/items?updated_at_min=" + oneSecondAddedDate + "&updated_at_max=" + nowAsISO + "", HttpMethod.GET, entity, ItemResponse.class);
            if (itemResponseResponseEntityUpdated.getBody().getItems() != null) {
                saveItemWithMappedValues(modelMapper, itemResponseResponseEntity, companyOp);
            }
        }

    }

    private void saveItemWithMappedValues(ModelMapper modelMapper, ResponseEntity<ItemResponse> itemResponseResponseEntity, Company companyOp) {
        itemResponseResponseEntity.getBody().getItems().forEach(item -> {
            Item item1 = modelMapper.map(item, Item.class);
            item1.setCompany(companyOp);
            item1.getVariants().clear();
            Item saveItem = itemRepository.save(item1);
            item.getVariants().forEach(itemVariant -> {
                ItemVariant variant = modelMapper.map(itemVariant, ItemVariant.class);
                variant.setItem(saveItem);
                variant.getStores().clear();
                ItemVariant saveVariant = itemVariantRepository.save(variant);
                itemVariant.getStores().forEach(store -> {
                    VariantStore variantStore = modelMapper.map(store, VariantStore.class);
                    variantStore.setItemVariant(saveVariant);
                    variantStoreRepository.save(variantStore);
                });
            });
        });
    }


}
