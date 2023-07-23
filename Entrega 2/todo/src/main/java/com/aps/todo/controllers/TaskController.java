package com.aps.todo.controllers;

import com.aps.todo.Facade;
import com.aps.todo.models.TaskModel;
import com.aps.todo.repositories.TaskRepository;
import com.aps.todo.repositories.TaskRepositoryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private Facade fachada;

    @Autowired
    public TaskController(Facade fachada) {
        this.fachada = fachada;
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity<List<TaskModel>> getAllTasks(@RequestHeader("token") String token) {
        return fachada.getAllTasks(token);
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> getTaskById(@RequestHeader("token") String token, @PathVariable Long id) {
        return fachada.getTaskById(token, id);
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<TaskModel> createTask(@RequestHeader("token") String token, @RequestBody TaskModel task) {
        return fachada.postNewTask(token, task);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> updateTask(@RequestHeader("token") String token, @PathVariable Long id, @RequestBody TaskModel task){
        return fachada.putTask(token, id, task);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@RequestHeader("token") String token, @PathVariable Long id) {
        return fachada.deleteTask(token, id);
    }

}