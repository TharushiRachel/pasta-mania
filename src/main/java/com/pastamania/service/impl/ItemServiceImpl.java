package com.pastamania.service.impl;

import com.pastamania.dto.response.ItemResponse;
import com.pastamania.entity.Item;
import com.pastamania.entity.ItemVariant;
import com.pastamania.entity.VariantStore;
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

import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public void initialStorePersist() {

        RestTemplate restTemplate = new RestTemplate();
        ModelMapper modelMapper = new ModelMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + "d22e68278c144eb8b22c50a2623bccc9");
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestJson = "{}";
        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        //newly created
        ResponseEntity<ItemResponse> createdResponse = restTemplate.exchange("https://api.loyverse.com/v1.0/items", HttpMethod.GET, entity, ItemResponse.class);

        List<Item> items = createdResponse.getBody().getItems().stream().map(item ->
                modelMapper.map(item, Item.class)).collect(Collectors.toList());

        createdResponse.getBody().getItems().forEach(item -> {
            Item item1 = modelMapper.map(item, Item.class);
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


        System.out.println("=============================");

    }


}
