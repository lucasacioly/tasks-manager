package com.aps.todo.repositories;

import com.aps.todo.models.EpicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpicRepository extends JpaRepository<EpicModel, Long> {

    @Query("SELECT e FROM EpicModel e WHERE e.userId = :userId")
    List<EpicModel> getUserEpics(@Param("userId") String userId);

}
