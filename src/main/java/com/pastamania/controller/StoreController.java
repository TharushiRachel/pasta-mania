package com.pastamania.controller;

import com.pastamania.dto.request.StoreCreateRequest;
import com.pastamania.dto.response.StoreCreateResponse;
import com.pastamania.dto.wrapper.SingleResponseWrapper;
import com.pastamania.modelmapper.ModelMapper;
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

    /*@Autowired
    private StoreService storeService;

    @PostMapping("${app.endpoint.StoreesCreate}")
    public ResponseEntity<SingleResponseWrapper<StoreCreateResponse>> create(
            @Validated @RequestBody StoreCreateRequest request) {

        log.info("Store creation start {}",request.getToken());

        Integer status = storeService.getAllStoreByToken(request.getToken());

        log.info("storeCreateRequests status {}",status);

        *//*Store Store = modelMapper.map(request,Store.class);

        Store persistedStore = StoreService.save(Store);

        StoreCreateResponse response = modelMapper.map(persistedStore, StoreCreateResponse.class);

        log.info("Store creation start {}",response.getId());*//*

        StoreCreateResponse response = new StoreCreateResponse();
        response.setStatus(status);

        return new ResponseEntity<SingleResponseWrapper<StoreCreateResponse>>(
                new SingleResponseWrapper<StoreCreateResponse>(response), HttpStatus.CREATED);
    }*/
}
