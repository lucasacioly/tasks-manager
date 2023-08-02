package com.aps.todo.Repository;

import com.aps.todo.daos.UserDao;
import com.aps.todo.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepository implements IUserRepository{

    private static UserRepository instance;

    @Autowired
    private UserDao userDao;

    public static synchronized UserRepository getInstance(){
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    };

    public List<UserModel> findAll (){
        return this.userDao.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        return this.userDao.findById(id);
    }

    public UserModel save(UserModel user) {
        return this.userDao.save(user);
    }


    public boolean existsById(Long id) {
        return this.userDao.existsById(id);
    }

    public String getOauthToken(long id){
        return this.userDao.getOauthToken(id);
    }

    public void deleteById(Long id) {
        this.userDao.deleteById(id);
    }

    public UserModel signIn(String email, String password){
        return this.userDao.signIn(email, password);
    }

    public UserModel checkByEmail(String email){
        return this.userDao.checkByEmail(email);
    }

    public UserModel validateUser(String token){
        return this.userDao.validateUser(token);
    }
}
