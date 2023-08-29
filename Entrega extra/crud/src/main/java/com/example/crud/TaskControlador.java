package com.example.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskControlador {

    private final TaskCollection taskCollection;


    public TaskControlador(TaskCollection taskCollection) {
        this.taskCollection = taskCollection;
    }

    public ResponseEntity<List<TaskRecordDTO>> getAllTasks(String token) {
        var userId = validateUser(token);

        if (userId != null){
            List<TaskModel> tasks = taskCollection.getUserTasks(userId);

            List<TaskRecordDTO> taskDtos = tasks.stream()
                    .map(task -> new TaskRecordDTO(
                            task.getId(),
                            task.getName(),
                            task.getDescription(),
                            task.getDueDate(),
                            task.getInProgress(),
                            task.getUserId()
                    ))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(taskDtos, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<TaskRecordDTO> getTaskById(String token, Long id) {
        var userId = validateUser(token);

        if (userId == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TaskModel task = taskCollection.findById(id).orElse(null);

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TaskRecordDTO taskDto = new TaskRecordDTO(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getDueDate(),
                task.getInProgress(),
                task.getUserId()
        );

        return new ResponseEntity<>(taskDto, HttpStatus.OK);

    }

    public ResponseEntity<TaskRecordDTO> createTask(String token,  TaskRecordDTO task) {
        var userId = validateUser(token);
        if (userId == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        TaskModel taskCreated = new TaskModel();
        taskCreated.setName(task.name());
        taskCreated.setDescription(task.description());
        taskCreated.setDueDate(task.dueDate());
        taskCreated.setInProgress(task.inProgress());
        taskCreated.setUserId(userId);

        taskCollection.save(taskCreated);

        TaskRecordDTO taskDto = new TaskRecordDTO(
                taskCreated.getId(),
                taskCreated.getName(),
                taskCreated.getDescription(),
                taskCreated.getDueDate(),
                taskCreated.getInProgress(),
                taskCreated.getUserId()
        );

        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    public ResponseEntity<TaskModel> updateTask(String token, Long id, TaskRecordDTO task) {
        var userId = validateUser(token);
        if (userId != null){

            if (!taskCollection.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            TaskModel taskCreated = new TaskModel();

            taskCreated.setId(task.id());
            taskCreated.setName(task.name());
            taskCreated.setDescription(task.description());
            taskCreated.setInProgress(task.inProgress());
            taskCreated.setDueDate(task.dueDate());
            taskCreated.setUserId(userId);
            TaskModel updatedTask = taskCollection.save(taskCreated);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> deleteTask(String token, Long id) {
        var userId = validateUser(token);
        if (userId != null){

            if (!taskCollection.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            taskCollection.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    private String validateUser(String token){
        String userAppUrl = "http://gateway:80/auth/users/validateUser";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("token", token);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(userAppUrl, HttpMethod.GET, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }

    }

}