package com.aps.todo.collection;

import com.aps.todo.repository.EpicRepository;
import com.aps.todo.models.EpicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EpicCollection {


    private EpicRepository epicRepository;

    @Autowired
    public EpicCollection(EpicRepository epicRepository){
        this.epicRepository = epicRepository;
    }

    public List<EpicModel> getUserEpics (String userId){
        return this.epicRepository.getUserEpics(userId);
    }

    public Optional<EpicModel> findById(Long id) {
        return this.epicRepository.findById(id);
    }

    public EpicModel save(EpicModel epic) {
        return this.epicRepository.save(epic);
    }


    public boolean existsById(Long id) {
        return this.epicRepository.existsById(id);
    }

    public void deleteById(Long id) {
        this.epicRepository.deleteById(id);
    }


}
