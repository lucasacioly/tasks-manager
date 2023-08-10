package com.aps.todo.controlador;

import com.aps.todo.collection.UserCollection;
import com.aps.todo.models.GoogleUserModel;
import com.aps.todo.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.List;
import java.util.UUID;

@Component
public class UserControlador {

    private static RestTemplate httpClient;
    private final UserCollection userCollection;

    @Autowired
    public UserControlador(UserCollection userCollection) {
        this.userCollection = userCollection;
        this.httpClient = new RestTemplate();
    }


    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userCollection.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        UserModel user = userCollection.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        String token = UUID.randomUUID().toString();
        user.setOauthToken(token);
        UserModel createdUser = userCollection.save(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        if (!userCollection.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setOauthToken(userCollection.getOauthToken(id));
        UserModel updatedUser = userCollection.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userCollection.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userCollection.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<UserModel> signIn(@RequestBody String email, @RequestBody String password){
        var user = userCollection.signIn(email, password);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

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

    public UserModel validateUser(String token){
        var user = userCollection.validateUser(token);
        return user;
    }
}

