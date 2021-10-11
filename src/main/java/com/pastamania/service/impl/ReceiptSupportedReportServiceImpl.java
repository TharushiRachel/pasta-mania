package com.pastamania.service.impl;

import com.pastamania.dto.reportDto.*;
import com.pastamania.modelmapper.ModelMapper;
import com.pastamania.report.DailySalesMixReport;
import com.pastamania.report.HourlySaleReport;
import com.pastamania.report.SaleSummaryReport;
import com.pastamania.report.SettlementModeWiseReport;
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
import java.util.LinkedHashMap;
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

        TemplateEngine templateEngine = getTemplateEngine();
        Context context = new Context();
        context.setVariable("categoryList", categoryDtoMap);
        return templateEngine.process("daily_sale_report_template", context);

    }

    @Override
    public String parseThymeleafTemplateForDailySaleMixReport() {
        Map<String, CategoryDto> categoryDtoMap = getStringCategoryDtoMap();
        TemplateEngine templateEngine = getTemplateEngine();
        Context context = new Context();
        context.setVariable("categoryList", categoryDtoMap);
        return templateEngine.process("daily_sale_mix_report_template", context);
    }

    @Override
    public String parseThymeleafTemplateForSalesSummaryReport() {
        List<SaleSummaryReport> dataForSaleSummaryReportDiningType = receiptRepository.findDataForSaleSummaryReportDiningType(null, null, null);
        List<SaleSummaryReport> dataForSaleSummaryReportCategory = receiptRepository.findDataForSaleSummaryReportCategory(null, null, null);
        List<SaleSummaryReport> dataForSaleSummaryReportPaymentMode = receiptRepository.findDataForSaleSummaryReportPaymentMode(null, null, null);

        List<SalesSummaryReportDto> dataForSaleSummaryReportDiningTypeDto = modelMapper.map(dataForSaleSummaryReportDiningType, SalesSummaryReportDto.class);
        List<SalesSummaryReportDto> dataForSaleSummaryReportCategoryDto = modelMapper.map(dataForSaleSummaryReportCategory, SalesSummaryReportDto.class);
        List<SalesSummaryReportDto> dataForSaleSummaryReportPaymentModeDto = modelMapper.map(dataForSaleSummaryReportPaymentMode, SalesSummaryReportDto.class);

        TemplateEngine templateEngine = getTemplateEngine();
        Context context = new Context();
        context.setVariable("dataForSaleSummaryReportDiningTypeDto", dataForSaleSummaryReportDiningTypeDto);
        context.setVariable("dataForSaleSummaryReportCategoryDto", dataForSaleSummaryReportCategoryDto);
        context.setVariable("dataForSaleSummaryReportPaymentModeDto", dataForSaleSummaryReportPaymentModeDto);

        return templateEngine.process("sale_summary_report_template", context);

    }

    @Override
    public String parseThymeleafTemplateForHourlySaleReport() {
        List<HourlySaleReport> dataForHourlySaleReport = receiptRepository.findDataForHourlySaleReport(null, null, null);

        List<HourlySaleReportDto> hourlySaleReportDto = modelMapper.map(dataForHourlySaleReport, HourlySaleReportDto.class);

        LinkedHashMap<Integer,HourlySaleReportDataDto> hourlySaleMap = new LinkedHashMap();


        hourlySaleReportDto.forEach(hourlySale->{

            switch(hourlySale.getCreatedAt().substring(11,13)) {
                case "03":
                    extracted(hourlySaleMap, hourlySale,3);
                    break;
                case "04":
                    extracted(hourlySaleMap, hourlySale,4);
                case "05":
                    extracted(hourlySaleMap, hourlySale,5);
                    break;
                case "06":
                    extracted(hourlySaleMap, hourlySale,6);
                case "07":
                    extracted(hourlySaleMap, hourlySale,7);
                    break;
                case "08":
                    extracted(hourlySaleMap, hourlySale,8);
                case "09":
                    extracted(hourlySaleMap, hourlySale,9);
                    break;
                case "10":
                    extracted(hourlySaleMap, hourlySale,10);
                case "11":
                    extracted(hourlySaleMap, hourlySale,11);
                    break;
                case "12":
                    extracted(hourlySaleMap, hourlySale,12);
                case "13":
                    extracted(hourlySaleMap, hourlySale,13);
                    break;
                case "14":
                    extracted(hourlySaleMap, hourlySale,14);
                case "15":
                    extracted(hourlySaleMap, hourlySale,15);
                case "16":
                    extracted(hourlySaleMap, hourlySale,15);
                    break;
                case "17":
                    extracted(hourlySaleMap, hourlySale,17);
                case "18":
                    extracted(hourlySaleMap, hourlySale,18);
                    break;
                case "19":
                    extracted(hourlySaleMap, hourlySale,19);
                case "20":
                    extracted(hourlySaleMap, hourlySale,20);
                case "21":
                    extracted(hourlySaleMap, hourlySale,21);
                    break;
                case "22":
                    extracted(hourlySaleMap, hourlySale,22);
                case "23":
                    extracted(hourlySaleMap, hourlySale,23);
                case "24":
                    extracted(hourlySaleMap, hourlySale,24);
                    break;
                case "01":
                    extracted(hourlySaleMap, hourlySale,1);
                case "02":
                    extracted(hourlySaleMap, hourlySale,2);
            }

        });

        TemplateEngine templateEngine = getTemplateEngine();
        Context context = new Context();
        context.setVariable("hourlySaleMap", hourlySaleMap);

        return templateEngine.process("hourly_sale_report_template", context);


    }

    @Override
    public String parseThymeleafTemplateForSettlementModeWiseReport() {
        List<SettlementModeWiseReport> settlementModeWiseReports = receiptRepository.findDataForSettlementModeViewReport(null, null, null);

        List<SettlementModeWiseReportDto> settlementModeWiseReportDto = modelMapper.map(settlementModeWiseReports, SettlementModeWiseReportDto.class);

        LinkedHashMap<String,SettlementModeWiseReportDto> settlementModeWiseReportDtoLinkedHashMap = new LinkedHashMap();

        settlementModeWiseReportDto.forEach(settlementModeWiseReport->{

            SettlementModeWiseReportDto reportDto = settlementModeWiseReportDtoLinkedHashMap.get(settlementModeWiseReport.getSettlement());

            if(reportDto==null){
                SettlementModeWiseReportDto modeWiseReportDto =  new SettlementModeWiseReportDto();

            }

        });
    }

    private void extracted(LinkedHashMap<Integer, HourlySaleReportDataDto> hourlySaleMap, HourlySaleReportDto hourlySale ,int no) {
        HourlySaleReportDataDto hourlySaleReportDataDto = hourlySaleMap.get(no);
        if(hourlySaleReportDataDto==null){
            HourlySaleReportDataDto newHourlySaleReportDataDto =  new HourlySaleReportDataDto();
            newHourlySaleReportDataDto.setSale(round(hourlySale.getTotalMoney(),2));
            newHourlySaleReportDataDto.setItems(hourlySale.getLineItemId());
            newHourlySaleReportDataDto.setTransactions(1);
            hourlySaleMap.put(no,newHourlySaleReportDataDto);
        }else{
            hourlySaleReportDataDto.setSale(hourlySaleReportDataDto.getSale()+ hourlySale.getTotalMoney());
            hourlySaleReportDataDto.setTransactions(hourlySaleReportDataDto.getTransactions()+1);
            hourlySaleReportDataDto.setSale(round(hourlySaleReportDataDto.getSale(),2));
            hourlySaleReportDataDto.setAvgSale(round(hourlySaleReportDataDto.getSale()/hourlySaleReportDataDto.getTransactions(),2));
        }
    }


    private TemplateEngine getTemplateEngine() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
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
                itemTypeDto1.setVariantName(dailySalesMixReportDto.getVariantName() == null ? "-" : dailySalesMixReportDto.getVariantName());
                if (dailySalesMixReportDto.getReceiptType().equalsIgnoreCase("REFUND")) {
                    itemTypeDto1.setGrossSale(round((itemTypeDto1.getGrossSale() - dailySalesMixReportDto.getGrossTotalMoney()), 2));
                    itemTypeDto1.setRefundedAmount(itemTypeDto1.getRefundedAmount() + dailySalesMixReportDto.getGrossTotalMoney());
                    itemTypeDto1.setRefundedCount(itemTypeDto1.getRefundedCount() + 1);
                } else {
                    itemTypeDto1.setGrossSale(round(dailySalesMixReportDto.getGrossTotalMoney(), 2));
                }
                itemTypeDto1.setItemSoldCount(1);
                categoryDtoMap.get(dailySalesMixReportDto.getCategoryId()).getItemDtoMap().get(dailySalesMixReportDto.getItemId()).getItemTypeDtoMap().put(dailySalesMixReportDto.getVariantName() == null ? "-" : dailySalesMixReportDto.getVariantName(), itemTypeDto1);
            } else {
                if (dailySalesMixReportDto.getReceiptType().equalsIgnoreCase("REFUND")) {
                    itemTypeDto.setGrossSale(round(itemTypeDto.getGrossSale() - dailySalesMixReportDto.getGrossTotalMoney(), 2));
                    itemTypeDto.setRefundedAmount(itemTypeDto.getRefundedAmount() + dailySalesMixReportDto.getGrossTotalMoney());
                    itemTypeDto.setRefundedCount(itemTypeDto.getRefundedCount() + 1);
                }
                itemTypeDto.setGrossSale(round(itemTypeDto.getGrossSale() + dailySalesMixReportDto.getGrossTotalMoney(), 2));
                itemTypeDto.setItemSoldCount(itemTypeDto.getItemSoldCount() + 1);
            }

        });


        categoryDtoMap.forEach((s, categoryDto) -> {
            categoryDto.getItemDtoMap().forEach((s1, itemDto) -> {
                itemDto.setItemSoldCount(itemDto.getItemTypeDtoMap().size());
                itemDto.getItemTypeDtoMap().forEach((s2, itemTypeDto) -> {
                    itemTypeDto.setNetTotalSale(round(itemTypeDto.getGrossSale() - itemTypeDto.getRefundedAmount(), 2));
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
