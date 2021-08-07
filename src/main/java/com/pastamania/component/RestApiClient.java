package com.pastamania.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class RestApiClient {

    private RestTemplate restTemplateWithAuth;

    private RestTemplate restTemplate;


    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    /*Spring calls methods annotated with @PostConstruct only once, just after the initialization of bean properties.
    Keep in mind that these methods will run even if there is nothing to initialize.*/
    @PostConstruct
    private void postConstruct() {
        this.restTemplate = new RestTemplate();

    }

    public RestTemplate getRestTemplate() {
        return this.restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplateWithAuth() {
        return restTemplateWithAuth;
    }

    public void setRestTemplateWithAuth(RestTemplate restTemplateWithAuth) {
        this.restTemplateWithAuth = restTemplateWithAuth;
    }
}
