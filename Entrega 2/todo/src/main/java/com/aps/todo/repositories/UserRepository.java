package com.aps.todo.repositories;

import com.aps.todo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    // Adicione métodos personalizados de consulta, se necessário

}
