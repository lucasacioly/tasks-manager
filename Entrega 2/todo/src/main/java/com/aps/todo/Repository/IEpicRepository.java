package com.aps.todo.Repository;

import com.aps.todo.models.EpicModel;

import java.util.List;
import java.util.Optional;

public interface IEpicRepository {

    public List<EpicModel> getUserEpics (String userId);
    public Optional<EpicModel> findById(Long id);
    public EpicModel save(EpicModel epic);
    public boolean existsById(Long id);
    public void deleteById(Long id);

}
