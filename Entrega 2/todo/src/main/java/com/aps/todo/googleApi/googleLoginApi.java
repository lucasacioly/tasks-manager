package com.aps.todo.googleApi;

import com.aps.todo.collection.UserCollection;
import com.aps.todo.models.GoogleUserModel;
import com.aps.todo.models.UserModel;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.util.UUID;

@Component
public class googleLoginApi implements IgoogleLoginApi{

    private final UserCollection userCollection;

    public googleLoginApi(UserCollection userCollection){
        this.userCollection = userCollection;
    }

    @Override
    public ResponseEntity<UserModel> googleLogin(String token) {
        String GOOGLE_ENDPOINT = "https://www.googleapis.com/oauth2/v3/userinfo";

        GoogleUserModel googleResponse = null;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(GOOGLE_ENDPOINT);

            httpGet.setHeader("Authorization", "Bearer " + token);

            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<GoogleUserModel> resp = restTemplate.exchange(
                    GOOGLE_ENDPOINT,
                    HttpMethod.GET,
                    null,
                    GoogleUserModel.class
            );

            googleResponse = resp.getBody();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String oauthtoken = UUID.randomUUID().toString();
        UserModel user = new UserModel();
        user.setUsername(googleResponse.getName());
        user.setEmail(googleResponse.getEmail());
        user.setPassword(googleResponse.getEmail());
        user.setOauthToken(oauthtoken);

        var existUser = userCollection.checkByEmail(user.getEmail());
        if (existUser != null) {
            return new ResponseEntity<>(existUser, HttpStatus.OK);
        }

        var userSaved = userCollection.save(user);
        return new ResponseEntity<>(userSaved, HttpStatus.OK);
    }
}
