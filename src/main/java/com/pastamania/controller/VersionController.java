package com.pastamania.controller;


import io.swagger.annotations.ApiModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiModel(value="VersionController", description="Check the version of the application")
public class VersionController {

    @GetMapping("${app.endpoint.versionsSearch}")
    public String retrieveVersion() {
        return "1.0";
    }

    /*@GetMapping("${app.endpoint.versionsSearch}")
    public ResponseEntity<SingleResponseWrapper<NewVersionResponse>> retrieveVersion() {

        Version version = versionService.retrieve();

        NewVersionResponse response = modelMapper.map(version, NewVersionResponse.class);

        return new ResponseEntity<SingleResponseWrapper<NewVersionResponse>>(
                new SingleResponseWrapper<NewVersionResponse>(response), HttpStatus.OK);
    }*/
}
