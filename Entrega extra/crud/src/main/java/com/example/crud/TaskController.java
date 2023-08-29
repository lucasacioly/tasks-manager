package com.example.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private TaskControlador taskControlador;

    @Autowired
    public TaskController(TaskControlador taskControlador) {
        this.taskControlador = taskControlador;
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity<List<TaskRecordDTO>> getAllTasks(@RequestHeader("token") String token) {
        return taskControlador.getAllTasks(token);
    }

    @CrossOrigin
    @GetMapping("/api/{id}")
    public ResponseEntity<TaskRecordDTO> getTaskById(@RequestHeader("token") String token, @PathVariable Long id) {
        return taskControlador.getTaskById(token, id);
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<TaskRecordDTO> createTask(@RequestHeader("token") String token, @RequestBody TaskRecordDTO task) {
        return taskControlador.createTask(token, task);
    }

    @CrossOrigin
    @PutMapping("/api/{id}")
    public ResponseEntity<TaskModel> updateTask(@RequestHeader("token") String token, @PathVariable Long id, @RequestBody TaskRecordDTO task){
        return taskControlador.updateTask(token, id, task);
    }

    @CrossOrigin
    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deleteTask(@RequestHeader("token") String token, @PathVariable Long id) {
        return taskControlador.deleteTask(token, id);
    }

}