package com.aps.todo.collection;

import com.aps.todo.repository.EpicRepository;
import com.aps.todo.models.EpicModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<EpicModel> findById(Long id) {

        EpicModel epic = this.epicRepository.findById(id).orElse(null);

        if (epic == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(epic, HttpStatus.OK);
    }

    public EpicModel save(EpicModel epic) { return this.epicRepository.save(epic); }


    public boolean existsById(Long id) {
        return this.epicRepository.existsById(id);
    }

    public void deleteById(Long id) {
        this.epicRepository.deleteById(id);
    }


}
