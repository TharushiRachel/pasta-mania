package com.pastamania.component;


import com.pastamania.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenAuthorizationInterceptor implements ClientHttpRequestInterceptor {

    @Autowired
    private AuthService authService;

    public TokenAuthorizationInterceptor() {
    }

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add("Content-Type", "application/json ");
        request.getHeaders().add("Authorization", "Bearer " + "d22e68278c144eb8b22c50a2623bccc9");

        return execution.execute(request, body);
    }
}
