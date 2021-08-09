package com.pastamania.controller;

import com.pastamania.dto.request.StoreCreateRequest;
import com.pastamania.dto.response.StoreCreateResponse;
import com.pastamania.dto.wrapper.SingleResponseWrapper;
import com.pastamania.entity.Store;
import com.pastamania.modelmapper.ModelMapper;
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
public class StoreController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private StoreService storeService;

    @PostMapping("${app.endpoint.StoreesCreate}")
    public ResponseEntity<SingleResponseWrapper<StoreCreateResponse>> create(
            @Validated @RequestBody StoreCreateRequest request) {

        log.info("Store creation start {}",request.getToken());

        Integer status = storeService.getAllStoreByToken(request.getToken());

        log.info("storeCreateRequests status {}",status);

        StoreCreateResponse response = new StoreCreateResponse();

        response.setStatus(status);

        return new ResponseEntity<SingleResponseWrapper<StoreCreateResponse>>(
                new SingleResponseWrapper<StoreCreateResponse>(response), HttpStatus.CREATED);
    }
}
