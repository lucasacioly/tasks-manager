package com.aps.todo.controlador;

import com.aps.todo.dtos.TaskRecordDto;
import com.aps.todo.models.EpicModel;
import com.aps.todo.models.TaskModel;
import com.aps.todo.repositories.EpicRepository;
import com.aps.todo.repositories.TaskRepository;
import com.aps.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskControlador {

    private final UserControlador userControlador;
    private  final EpicRepository epicRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TaskControlador(TaskRepository taskRepository, UserRepository userRepository, EpicRepository epicRepository) {
        this.epicRepository = epicRepository;
        this.userControlador = UserControlador.getInstance(userRepository);
        this.taskRepository = taskRepository;
    }

    public ResponseEntity<List<TaskModel>> getAllTasks(String token) {
        var user = userControlador.validateUser(token);
        if (user != null){

            List<TaskModel> tasks = taskRepository.getUserTasks(user.getId().toString());
            return new ResponseEntity<>(tasks, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public ResponseEntity<TaskModel> getTaskById(String token, Long id) {
        var user = userControlador.validateUser(token);
        if (user != null){

            TaskModel task = taskRepository.findById(id).orElse(null);
            if (task == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(task, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<TaskModel> createTask(String token,  TaskRecordDto task) {
        var user = userControlador.validateUser(token);
        if (user != null){

            TaskModel taskCreated = new TaskModel();

            if(task.epicId() != null){
                EpicModel epic = epicRepository.findById(task.epicId()).orElse(null);
                taskCreated.setEpic(epic);
            }

            taskCreated.setName(task.name());
            taskCreated.setDescription(task.description());
            taskCreated.setInProgress(task.inProgress());
            taskCreated.setDueDate(task.dueDate());
            taskCreated.setUserId(task.userId());

            taskCreated.setUserId(user.getId().toString());
            TaskModel createdTask = taskRepository.save(taskCreated);
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<TaskModel> updateTask(String token, Long id, TaskRecordDto task) {
        var user = userControlador.validateUser(token);
        if (user != null){

            if (!taskRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            EpicModel epic = epicRepository.findById(task.epicId()).orElse(null);
            TaskModel taskCreated = new TaskModel();

            taskCreated.setEpic(epic);
            taskCreated.setName(task.name());
            taskCreated.setDescription(task.description());
            taskCreated.setInProgress(task.inProgress());
            taskCreated.setDueDate(task.dueDate());
            taskCreated.setUserId(task.userId());

            taskCreated.setUserId(user.getId().toString());
            TaskModel updatedTask = taskRepository.save(taskCreated);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> deleteTask(String token, Long id) {
        var user = userControlador.validateUser(token);
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

