package com.example.auth;

import com.example.auth.GoogleApi.*;
import com.example.auth.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Component
public class UserControlador {

    private final UserCollection userCollection;
    private final IGoogleLoginApi googleLoginApi;

    @Autowired
    public UserControlador(UserCollection userCollection) {
        this.userCollection = userCollection;
        this.googleLoginApi = GoogleLoginApi.getInstance();
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
        UserModel user = googleLoginApi.googleLogin(token);

        var existUser = userCollection.checkByEmail(user.getEmail());
        if (existUser != null) {
            return new ResponseEntity<>(existUser, HttpStatus.OK);
        }

        var userSaved = userCollection.save(user);
        return new ResponseEntity<>(userSaved, HttpStatus.OK);
    }

    public ResponseEntity<String> validateUser(String token){
        UserModel user = userCollection.validateUser(token);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user.getId().toString(), HttpStatus.OK);
    }
}