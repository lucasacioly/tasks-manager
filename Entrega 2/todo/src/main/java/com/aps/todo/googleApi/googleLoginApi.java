package com.aps.todo.googleApi;

import com.aps.todo.collection.UserCollection;
import com.aps.todo.models.GoogleUserModel;
import com.aps.todo.models.UserModel;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.net.URI;
import java.util.UUID;

@Component
public class googleLoginApi implements IgoogleLoginApi{

    private final UserCollection userCollection;

    public googleLoginApi(UserCollection userCollection){
        this.userCollection = userCollection;
    }

    @Override
    public ResponseEntity<UserModel> googleLogin(String token) {
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

        var existUser = userCollection.checkByEmail(user.getEmail());
        if (existUser != null) {
            return new ResponseEntity<>(existUser, HttpStatus.OK);
        }

        var userSaved = userCollection.save(user);
        return new ResponseEntity<>(userSaved, HttpStatus.OK);
    }
}
