package com.aps.todo.repositories;

import com.aps.todo.models.EpicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpicRepository extends JpaRepository<EpicModel, Long> {

    // Adicione métodos personalizados de consulta, se necessário

}
