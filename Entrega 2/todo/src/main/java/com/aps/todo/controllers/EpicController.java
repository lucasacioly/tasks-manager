package com.aps.todo.controllers;

import com.aps.todo.models.EpicModel;
import com.aps.todo.repositories.EpicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/epics")
public class EpicController {

    private final EpicRepository epicRepository;

    @Autowired
    public EpicController(EpicRepository epicRepository) {
        this.epicRepository = epicRepository;
    }

    @GetMapping
    public ResponseEntity<List<EpicModel>> getAllEpics() {
        List<EpicModel> epics = epicRepository.findAll();
        return new ResponseEntity<>(epics, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpicModel> getEpicById(@PathVariable Long id) {
        EpicModel epic = epicRepository.findById(id).orElse(null);
        if (epic == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(epic, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EpicModel> createEpic(@RequestBody EpicModel epic) {
        EpicModel createdEpic = epicRepository.save(epic);
        return new ResponseEntity<>(createdEpic, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpicModel> updateEpic(@PathVariable Long id, @RequestBody EpicModel epic) {
        if (!epicRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        EpicModel updatedEpic = epicRepository.save(epic);
        return new ResponseEntity<>(updatedEpic, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpic(@PathVariable Long id) {
        if (!epicRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        epicRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
