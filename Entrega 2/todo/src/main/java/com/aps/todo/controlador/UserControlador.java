package com.aps.todo.controlador;

import com.aps.todo.models.UserModel;
import com.aps.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Component
public class UserControlador {

    private static UserControlador userControlador;

    @Autowired
    public UserControlador(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public static synchronized UserControlador getInstance(UserRepository userRepository){
        if (userControlador == null)
            userControlador = new UserControlador(userRepository);

        return userControlador;
    }

    private final UserRepository userRepository;


    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        UserModel user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        String token = UUID.randomUUID().toString();
        user.setOauthToken(token);
        UserModel createdUser = userRepository.save(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel user) {
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setOauthToken(userRepository.getOauthToken(id));
        UserModel updatedUser = userRepository.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<UserModel> signIn(@RequestBody String email, @RequestBody String password){
        var user = userRepository.signIn(email, password);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    public ResponseEntity<UserModel> googleSignUp(String email, String name){
        var user = new UserModel();
        user.setUsername(name);
        user.setEmail(email);

        String token = UUID.randomUUID().toString();
        user.setOauthToken(token);
        user.setPassword(token);

        UserModel createdUser = userRepository.save(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    public ResponseEntity<UserModel> googleSignIn(String email){

        UserModel googleUser = userRepository.GooglesignIn(email);
        if(googleUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(googleUser, HttpStatus.CREATED);
    }

    public UserModel validateUser(String token){
        var user = userRepository.validateUser(token);
        return user;
    }
}

