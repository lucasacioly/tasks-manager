package com.aps.todo.repositories;


import com.aps.todo.models.UserModel;
import com.aps.todo.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositoryH2 extends UserRepository {

    // Adicione métodos personalizados de consulta, se necessário

}
