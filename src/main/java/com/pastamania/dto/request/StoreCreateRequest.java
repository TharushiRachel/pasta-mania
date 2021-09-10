package com.pastamania.dto.request;

import lombok.Data;

//import javax.validation.constraints.NotEmpty;

@Data
public class StoreCreateRequest {

    //@NotEmpty(message = "Please enter valid token")
    private String token;
}
