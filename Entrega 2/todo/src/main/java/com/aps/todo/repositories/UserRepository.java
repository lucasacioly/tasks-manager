package com.aps.todo.repositories;

import com.aps.todo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

     @Query("SELECT u FROM UserModel u WHERE u.email = :email AND u.password = :password")
     UserModel signIn(@Param("email") String email, @Param("password") String password);
}
