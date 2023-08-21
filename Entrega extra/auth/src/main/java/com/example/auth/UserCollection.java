package com.example.auth;

import com.example.auth.Repository.UserRepository;
import com.example.auth.Models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserCollection {

    private final UserRepository userRepository;

    @Autowired
    public UserCollection(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> findAll (){
        return userRepository.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        return userRepository.findById(id);
    }

    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }


    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public String getOauthToken(long id){
        return userRepository.getOauthToken(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public UserModel signIn(String email, String password){
        return userRepository.signIn(email, password);
    }

    public UserModel checkByEmail(String email){
        return userRepository.checkByEmail(email);
    }

    public UserModel validateUser(String token){
        return userRepository.validateUser(token);
    }
}