package com.pastamania.service.impl;

import com.pastamania.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
/*import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;*/
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${shangrilaSync.appCode}")
    private String appCode;

    @Value("${shangrilaSync.propertyCode}")
    private String propertyCode;

    @Value("${shangrilaSync.clientID}")
    private String clientID;

    @Value("${shangrilaSync.clientSecret}")
    private String clientSecret;

    @Value("${shangrilaSync.pOSInterfaceCode}")
    private String pOSInterfaceCode;

    @Value("${shangrilaSync.token.url}")
    private String tokenUrl;


    /*@Override
    public String retrieveOauth2Token() {

        final ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setClientId(clientID);
        resourceDetails.setClientSecret(clientSecret);
        resourceDetails.setGrantType("client_credentials");
        resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.query);

        resourceDetails.setAccessTokenUri(tokenUrl);

        final OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails);
        final OAuth2AccessToken accessToken = oAuth2RestTemplate.getAccessToken();

        final String accessTokenAsString = accessToken.getValue();

        System.out.println("accessTokenAsString : {}"+ accessTokenAsString);


        return accessToken.getValue();
    }*/
}
