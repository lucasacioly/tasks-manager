package com.aps.todo.controllers;

import com.aps.todo.Facade;
import com.aps.todo.models.EpicModel;
import org.springframework.beans.factory.annotation.Autowired;
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
    @CrossOrigin
    public ResponseEntity<List<EpicModel>> getAllEpics(@RequestHeader("token") String token) {
        return fachada.getAllEpics(token);
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<EpicModel> getEpicById(@RequestHeader("token")String token, @PathVariable Long id) {
        return fachada.getEpicById(token, id);
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<EpicModel> createEpic(@RequestHeader("token") String token, @RequestBody EpicModel epic) {
        return fachada.postNewEpic(token, epic);
    }

    @PutMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<EpicModel> updateEpic(@RequestHeader("token") String token, @PathVariable Long id, @RequestBody EpicModel epic){
        return fachada.putEpic(token, id, epic);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<Void> deleteEpic(@RequestHeader("token") String token, @PathVariable Long id) {
        return fachada.deleteEpic(token, id);
    }

}
