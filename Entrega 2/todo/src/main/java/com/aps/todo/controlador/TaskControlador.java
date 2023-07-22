package com.aps.todo.controlador;

import com.aps.todo.models.TaskModel;
import com.aps.todo.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Component
public class TaskControlador {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskControlador(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public ResponseEntity<List<TaskModel>> getAllTasks() {
        List<TaskModel> tasks = taskRepository.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    public ResponseEntity<TaskModel> getTaskById(@PathVariable Long id) {
        TaskModel task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    public ResponseEntity<TaskModel> createTask(@RequestBody TaskModel task) {
        TaskModel createdTask = taskRepository.save(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    public ResponseEntity<TaskModel> updateTask(@PathVariable Long id, @RequestBody TaskModel task) {
        if (!taskRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TaskModel updatedTask = taskRepository.save(task);
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

