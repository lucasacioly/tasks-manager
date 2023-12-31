package com.aps.todo.repository;

import com.aps.todo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface UserRepository extends JpaRepository<UserModel, Long> {

     @Query("SELECT u FROM UserModel u WHERE u.email = :email AND u.password = :password")
     UserModel signIn(@Param("email") String email, @Param("password") String password);

     @Query("SELECT u FROM UserModel u WHERE u.email = :email")
     UserModel checkByEmail(@Param("email") String email);

     @Query("SELECT u.oauthToken FROM UserModel u WHERE u.id = :id")
     String getOauthToken(@Param("id") Long id);

     @Query("SELECT u FROM UserModel u WHERE u.oauthToken = :oauthToken")
     UserModel validateUser(@Param("oauthToken") String oauthToken);
}
