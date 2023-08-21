package com.example.auth.GoogleApi;

import com.example.auth.Models.*;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.UUID;

public class GoogleLoginApi implements IGoogleLoginApi {

    private static GoogleLoginApi instance;

    private GoogleLoginApi(){}

    public static synchronized GoogleLoginApi getInstance(){
        if (instance == null)
            instance = new GoogleLoginApi();

        return instance;
    }

    @Override
    public UserModel googleLogin(String token) {
        if (token.endsWith("=")) {
            token = token.substring(0, token.length() - 1);
        }
        String GOOGLE_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RequestEntity<?> requestEntity = RequestEntity.get(URI.create(GOOGLE_ENDPOINT))
                .headers(headers)
                .build();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GoogleUserModel> response = restTemplate.exchange(requestEntity, GoogleUserModel.class);

        GoogleUserModel googleResponse = response.getBody();


        String oauthtoken = UUID.randomUUID().toString();
        UserModel user = new UserModel();
        user.setUsername(googleResponse.getName());
        user.setEmail(googleResponse.getEmail());
        user.setPassword(oauthtoken);
        user.setOauthToken(oauthtoken);

        return user;
    }
}