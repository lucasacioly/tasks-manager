package com.aps.todo.controlador;

import com.aps.todo.models.EpicModel;
import com.aps.todo.repositories.EpicRepository;
import com.aps.todo.repositories.EpicRepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Component
public class EpicControlador {

    private final EpicRepository epicRepository;

    @Autowired
    public EpicControlador(EpicRepositoryFactory repositoryFactory) {
        this.epicRepository = repositoryFactory.createEpicRepository();
    }

    public ResponseEntity<List<EpicModel>> getAllEpics() {
        List<EpicModel> epics = epicRepository.findAll();
        return new ResponseEntity<>(epics, HttpStatus.OK);
    }

    public ResponseEntity<EpicModel> getEpicById(@PathVariable Long id) {
        EpicModel epic = epicRepository.findById(id).orElse(null);
        if (epic == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(epic, HttpStatus.OK);
    }


    public ResponseEntity<EpicModel> createEpic(@RequestBody EpicModel epic) {
        EpicModel createdEpic = epicRepository.save(epic);
        return new ResponseEntity<>(createdEpic, HttpStatus.CREATED);
    }

    public ResponseEntity<EpicModel> updateEpic(@PathVariable Long id, @RequestBody EpicModel epic) {
        if (!epicRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EpicModel updatedEpic = epicRepository.save(epic);
        return new ResponseEntity<>(updatedEpic, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteEpic(@PathVariable Long id) {
        if (!epicRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        epicRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
