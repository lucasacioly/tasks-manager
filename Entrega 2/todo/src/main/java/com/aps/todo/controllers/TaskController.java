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
    public ResponseEntity<List<TaskModel>> getAllTasks() {
        return fachada.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskModel> getTaskById(@PathVariable Long id) {
        return fachada.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity<TaskModel> createTask(@RequestBody TaskModel task) {
        return fachada.postNewTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> updateTask(@PathVariable Long id, @RequestBody TaskModel task){
        return fachada.putTask(id, task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return fachada.deleteTask(id);
    }

}