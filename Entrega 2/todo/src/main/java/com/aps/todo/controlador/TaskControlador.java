package com.aps.todo.controlador;

import com.aps.todo.models.TaskModel;
import com.aps.todo.repositories.TaskRepository;
import com.aps.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskControlador {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskControlador(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public ResponseEntity<List<TaskModel>> getAllTasks(String token) {
        var user = userRepository.validateUser(token);
        if (user != null){

            List<TaskModel> tasks = taskRepository.getUserTasks(user.getId().toString());
            return new ResponseEntity<>(tasks, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<TaskModel> getTaskById(String token, Long id) {
        var user = userRepository.validateUser(token);
        if (user != null){

            TaskModel task = taskRepository.findById(id).orElse(null);
            if (task == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(task, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<TaskModel> createTask(String token,  TaskModel task) {
        var user = userRepository.validateUser(token);
        if (user != null){

            task.setUserId(user.getId().toString());
            TaskModel createdTask = taskRepository.save(task);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<TaskModel> updateTask(String token, Long id, TaskModel task) {
        var user = userRepository.validateUser(token);
        if (user != null){

            if (!taskRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            task.setUserId(user.getId().toString());
            TaskModel updatedTask = taskRepository.save(task);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> deleteTask(String token, Long id) {
        var user = userRepository.validateUser(token);
        if (user != null){

            if (!taskRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            taskRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
}

