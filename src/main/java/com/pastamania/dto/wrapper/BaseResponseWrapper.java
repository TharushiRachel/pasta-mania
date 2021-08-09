package com.pastamania.dto.wrapper;

import com.pastamania.enums.RestApiResponseStatus;
import lombok.Data;

@Data
public class BaseResponseWrapper {

    public static BaseResponseWrapper OK = new BaseResponseWrapper(RestApiResponseStatus.OK);

    public BaseResponseWrapper(){}

    public BaseResponseWrapper(RestApiResponseStatus restApiResponseStatus) {
        this.status = restApiResponseStatus.getStatus();
        this.statusCode = restApiResponseStatus.getCode();
    }

    public String status;

    public Integer statusCode;
}
