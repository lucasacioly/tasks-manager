package com.aps.todo.collection;

import com.aps.todo.repository.UserRepository;
import com.aps.todo.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserCollection {

    private UserRepository userRepository;

    @Autowired
    public UserCollection(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserModel> findAll (){
        return this.userRepository.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        return this.userRepository.findById(id);
    }

    public UserModel save(UserModel user) {
        return this.userRepository.save(user);
    }


    public boolean existsById(Long id) {
        return this.userRepository.existsById(id);
    }

    public String getOauthToken(long id){
        return this.userRepository.getOauthToken(id);
    }

    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    public UserModel signIn(String email, String password){
        return this.userRepository.signIn(email, password);
    }

    public UserModel checkByEmail(String email){
        return this.userRepository.checkByEmail(email);
    }

    public UserModel validateUser(String token){
        return this.userRepository.validateUser(token);
    }
}
