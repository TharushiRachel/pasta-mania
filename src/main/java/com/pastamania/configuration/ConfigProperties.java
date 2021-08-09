package com.pastamania.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties("app")
@Configuration
public class ConfigProperties {

    private Loyvers loyvers;

    @Data
    public static class Loyvers {

        private String baseUrl;

    }


}
