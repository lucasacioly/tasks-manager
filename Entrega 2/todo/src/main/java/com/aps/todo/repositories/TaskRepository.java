package com.aps.todo.repositories;

import com.aps.todo.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Long> {

    // Aqui você pode adicionar métodos personalizados de consulta, se necessário

}