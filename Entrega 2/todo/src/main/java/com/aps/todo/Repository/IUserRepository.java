package com.aps.todo.Repository;

import com.aps.todo.models.UserModel;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    public List<UserModel> findAll ();
    public Optional<UserModel> findById(Long id);
    public UserModel save(UserModel user);
    public boolean existsById(Long id);
    public String getOauthToken(long id);
    public void deleteById(Long id);
    public UserModel signIn(String email, String password);
    public UserModel checkByEmail(String email);
    public UserModel validateUser(String token);
}
