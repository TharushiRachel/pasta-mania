package com.pastamania.service.impl;

import com.pastamania.dto.reportDto.CategoryDto;
import com.pastamania.dto.reportDto.DailySalesMixReportDto;
import com.pastamania.dto.reportDto.ItemDto;
import com.pastamania.dto.reportDto.ItemTypeDto;
import com.pastamania.modelmapper.ModelMapper;
import com.pastamania.report.DailySalesMixReport;
import com.pastamania.repository.ReceiptRepository;
import com.pastamania.service.ReceiptSupportedReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pasindu Lakmal
 */
@Service
@Transactional
@Slf4j
public class ReceiptSupportedReportServiceImpl implements ReceiptSupportedReportService {

    @Autowired
    ReceiptRepository receiptRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public String parseThymeleafTemplateForDailySaleReport() {

        Map<String, CategoryDto> categoryDtoMap = getStringCategoryDtoMap();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("categoryList", categoryDtoMap);

        return templateEngine.process("daily_sale_report_template", context);

    }

    @Override
    public String parseThymeleafTemplateForDailySaleMixReport() {
        Map<String, CategoryDto> categoryDtoMap = getStringCategoryDtoMap();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("categoryList", categoryDtoMap);

        return templateEngine.process("daily_sale_mix_report_template", context);
    }

    private Map<String, CategoryDto> getStringCategoryDtoMap() {
        List<DailySalesMixReport> dataForDailySaleMixReport = receiptRepository.findDataForDailySaleMixReport(null, null, null);
        List<DailySalesMixReportDto> dailySalesMixReportDtoList = modelMapper.map(dataForDailySaleMixReport, DailySalesMixReportDto.class);

        Map<String, CategoryDto> categoryDtoMap = new HashMap<>();

        dailySalesMixReportDtoList.forEach(dailySalesMixReportDto -> {

            //category

            //get cat dto
            CategoryDto categoryDto1 = categoryDtoMap.get(dailySalesMixReportDto.getCategoryId());

            //create new cat dto
            if (categoryDto1 == null) {
                CategoryDto categoryDto = new CategoryDto();
                categoryDto.setCategoryId(dailySalesMixReportDto.getCategoryId());
                categoryDto.setCategory(dailySalesMixReportDto.getCategory());
                categoryDtoMap.put(categoryDto.getCategoryId(), categoryDto);
            }

            // item

            //item dto map
            if (categoryDtoMap.get(dailySalesMixReportDto.getCategoryId()).getItemDtoMap() == null) {
                Map<String, ItemDto> itemDtoMap = new HashMap<>();
                categoryDtoMap.get(dailySalesMixReportDto.getCategoryId()).setItemDtoMap(itemDtoMap);
            }

            ItemDto itemDto1 = categoryDtoMap.get(dailySalesMixReportDto.getCategoryId()).getItemDtoMap().get(dailySalesMixReportDto.getItemId());

            if (itemDto1 == null) {
                ItemDto itemDto = new ItemDto();
                itemDto.setItemId(dailySalesMixReportDto.getItemId());
                itemDto.setItemName(dailySalesMixReportDto.getItemName());
                itemDto.setGrossSale(dailySalesMixReportDto.getGrossTotalMoney());
                categoryDtoMap.get(dailySalesMixReportDto.getCategoryId()).getItemDtoMap().put(dailySalesMixReportDto.getItemId(), itemDto);
            } else {
                itemDto1.setItemSoldCount(itemDto1.getItemSoldCount() == null ? 0 : itemDto1.getItemSoldCount() + 1);
                itemDto1.setGrossSale(itemDto1.getGrossSale() + dailySalesMixReportDto.getGrossTotalMoney());
            }

            //itemType
            if (categoryDtoMap.get(dailySalesMixReportDto.getCategoryId()).getItemDtoMap().get(dailySalesMixReportDto.getItemId()).getItemTypeDtoMap() == null) {
                Map<String, ItemTypeDto> itemTypeDtoMap = new HashMap<>();
                categoryDtoMap.get(dailySalesMixReportDto.getCategoryId()).getItemDtoMap().get(dailySalesMixReportDto.getItemId()).setItemTypeDtoMap(itemTypeDtoMap);
            }

            ItemTypeDto itemTypeDto = categoryDtoMap.get(dailySalesMixReportDto.getCategoryId()).getItemDtoMap().get(dailySalesMixReportDto.getItemId()).getItemTypeDtoMap().get(dailySalesMixReportDto.getVariantName());

            if (itemTypeDto == null) {
                ItemTypeDto itemTypeDto1 = new ItemTypeDto();
                itemTypeDto1.setVariantName(dailySalesMixReportDto.getVariantName()==null?"-":dailySalesMixReportDto.getVariantName());
                if (dailySalesMixReportDto.getReceiptType().equalsIgnoreCase("REFUND")) {
                    itemTypeDto1.setGrossSale( round((itemTypeDto1.getGrossSale() - dailySalesMixReportDto.getGrossTotalMoney()),2));
                    itemTypeDto1.setRefundedAmount(itemTypeDto1.getRefundedAmount() + dailySalesMixReportDto.getGrossTotalMoney());
                    itemTypeDto1.setRefundedCount(itemTypeDto1.getRefundedCount()+1);
                } else {
                    itemTypeDto1.setGrossSale(round(dailySalesMixReportDto.getGrossTotalMoney(),2));
                }
                itemTypeDto1.setItemSoldCount(1);
                categoryDtoMap.get(dailySalesMixReportDto.getCategoryId()).getItemDtoMap().get(dailySalesMixReportDto.getItemId()).getItemTypeDtoMap().put(dailySalesMixReportDto.getVariantName() == null ? "-" : dailySalesMixReportDto.getVariantName(), itemTypeDto1);
            } else {
                if (dailySalesMixReportDto.getReceiptType().equalsIgnoreCase("REFUND")) {
                    itemTypeDto.setGrossSale(round(itemTypeDto.getGrossSale() - dailySalesMixReportDto.getGrossTotalMoney(),2));
                    itemTypeDto.setRefundedAmount(itemTypeDto.getRefundedAmount() + dailySalesMixReportDto.getGrossTotalMoney());
                    itemTypeDto.setRefundedCount(itemTypeDto.getRefundedCount()+1);
                }
                itemTypeDto.setGrossSale(round(itemTypeDto.getGrossSale() + dailySalesMixReportDto.getGrossTotalMoney(),2));
                itemTypeDto.setItemSoldCount(itemTypeDto.getItemSoldCount() + 1);
            }

        });


        categoryDtoMap.forEach((s, categoryDto) -> {
            categoryDto.getItemDtoMap().forEach((s1, itemDto) -> {
                itemDto.setItemSoldCount(itemDto.getItemTypeDtoMap().size());
                itemDto.getItemTypeDtoMap().forEach((s2, itemTypeDto) -> {
                    itemTypeDto.setNetTotalSale(round(itemTypeDto.getGrossSale()- itemTypeDto.getRefundedAmount(),2));
                });
            });

        });
        return categoryDtoMap;
    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
