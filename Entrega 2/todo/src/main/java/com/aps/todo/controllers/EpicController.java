package com.aps.todo.controllers;

import com.aps.todo.Facade;
import com.aps.todo.models.EpicModel;
import com.aps.todo.repositories.EpicRepository;
import com.aps.todo.repositories.EpicRepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/epics")
public class EpicController {

    private Facade fachada;

    @Autowired
    public EpicController(Facade fachada) {
        this.fachada = fachada;
    }

    @GetMapping
    public ResponseEntity<List<EpicModel>> getAllEpics() {
        return fachada.getAllEpics();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpicModel> getEpicById(@PathVariable Long id) {
        return fachada.getEpicById(id);
    }

    @PostMapping
    public ResponseEntity<EpicModel> createEpic(@RequestBody EpicModel epic) {
        return fachada.postNewEpic(epic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EpicModel> updateEpic(@PathVariable Long id, @RequestBody EpicModel epic){
        return fachada.putEpic(id, epic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEpic(@PathVariable Long id) {
        return fachada.deleteEpic(id);
    }

}
