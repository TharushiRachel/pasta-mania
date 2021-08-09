package com.pastamania.controller;

import com.pastamania.dto.request.StoreCreateRequest;
import com.pastamania.dto.response.StoreCreateResponse;
import com.pastamania.dto.wrapper.SingleResponseWrapper;
import com.pastamania.modelmapper.ModelMapper;
import com.pastamania.service.CategoryService;
import com.pastamania.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CategoryController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("${app.endpoint.categoriesCreate}")
    public ResponseEntity<SingleResponseWrapper<StoreCreateResponse>> create() {
        log.info("categoriesCreate start ");

        categoryService.initialCategoryPersist();

        StoreCreateResponse response = new StoreCreateResponse();

        response.setStatus(0);

        return new ResponseEntity<SingleResponseWrapper<StoreCreateResponse>>(
                new SingleResponseWrapper<StoreCreateResponse>(response), HttpStatus.CREATED);
    }
}
