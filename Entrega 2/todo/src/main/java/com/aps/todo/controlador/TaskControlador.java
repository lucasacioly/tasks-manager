package com.aps.todo.controlador;

import com.aps.todo.collection.*;
import com.aps.todo.dtos.TaskRecordDto;
import com.aps.todo.models.EpicModel;
import com.aps.todo.models.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskControlador {

    private final UserCollection userCollection;
    private final EpicCollection epicCollection;
    private final TaskCollection taskCollection;

    @Autowired
    public TaskControlador(TaskCollection taskCollection, EpicCollection epicCollection, UserCollection userCollection) {
        this.epicCollection = epicCollection;
        this.userCollection = userCollection;
        this.taskCollection = taskCollection;
    }

    public ResponseEntity<List<TaskRecordDto>> getAllTasks(String token) {
        var user = userCollection.validateUser(token);
        if (user != null){

            List<TaskModel> tasks = taskCollection.getUserTasks(user.getId().toString());

            List<TaskRecordDto> taskDtos = tasks.stream()
                    .map(task -> new TaskRecordDto(
                            task.getId(),
                            task.getEpicId(),
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

    public ResponseEntity<TaskRecordDto> getTaskById(String token, Long id) {
        var user = userCollection.validateUser(token);

        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TaskModel task = taskCollection.findById(id).orElse(null);

        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TaskRecordDto taskDto = new TaskRecordDto(
                task.getId(),
                task.getEpicId(),
                task.getName(),
                task.getDescription(),
                task.getDueDate(),
                task.getInProgress(),
                task.getUserId()
        );

        return new ResponseEntity<>(taskDto, HttpStatus.OK);

    }

    public ResponseEntity<TaskRecordDto> createTask(String token,  TaskRecordDto task) {
        var user = userCollection.validateUser(token);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        TaskModel taskCreated = new TaskModel();
        taskCreated.setName(task.name());
        taskCreated.setDescription(task.description());
        taskCreated.setDueDate(task.dueDate());
        taskCreated.setEpic(null);
        taskCreated.setInProgress(task.inProgress());
        taskCreated.setUserId(user.getId().toString());

        if(task.epicId() != null){
            EpicModel epic = epicCollection.findById(task.epicId()).orElse(null);

            epic.setTotalTasks(epic.getTotalTasks() + 1);

            if (!task.inProgress()){
                epic.setTasksDone(epic.getTotalTasks() + 1);
            }
            epicCollection.save(epic);

            taskCreated.setEpic(epic);
        }

        taskCollection.save(taskCreated);

        TaskRecordDto taskDto = new TaskRecordDto(
                taskCreated.getId(),
                taskCreated.getEpicId(),
                taskCreated.getName(),
                taskCreated.getDescription(),
                taskCreated.getDueDate(),
                taskCreated.getInProgress(),
                taskCreated.getUserId()
        );

        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    public ResponseEntity<TaskModel> updateTask(String token, Long id, TaskRecordDto task) {
        var user = userCollection.validateUser(token);
        if (user != null){

            if (!taskCollection.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            TaskModel taskBd = taskCollection.findById(id).orElse(null);
            TaskModel taskCreated = new TaskModel();

            if(task.epicId() != null ) {
                EpicModel epicNew = epicCollection.findById(task.epicId()).orElse(null);
                if (epicNew != null && taskBd != null && taskBd.getEpic()!= null) {

                    // nao troca de epico
                    if (taskBd.getEpic().getId() == task.epicId()) {
                        if (taskBd.getInProgress() && !task.inProgress()) {
                            epicNew.setTasksDone(epicNew.getTasksDone() + 1);
                        }
                        if (!taskBd.getInProgress() && task.inProgress()) {
                            epicNew.setTasksDone(epicNew.getTasksDone() - 1);
                        }
                    } else { // troca de epico
                        EpicModel epicBD = epicCollection.findById(taskBd.getEpic().getId()).orElse(null);

                        if (epicBD != null) {
                            epicNew.setTotalTasks(epicNew.getTotalTasks() - 1);

                            if (!taskBd.getInProgress()) {
                                epicNew.setTasksDone(epicNew.getTasksDone() - 1);
                            }

                            epicCollection.save(epicBD);
                        }

                        epicNew.setTotalTasks(epicNew.getTotalTasks() + 1);

                        if (!task.inProgress()) {
                            epicNew.setTasksDone(epicNew.getTasksDone() + 1);
                        }
                    }
                } else if (epicNew != null && taskBd != null && taskBd.getEpic() == null) { // nao tinha epico e ganhou epico
                    epicNew.setTotalTasks(epicNew.getTotalTasks() + 1);

                    if (!task.inProgress()) {
                        epicNew.setTasksDone(epicNew.getTasksDone() + 1);
                    }
                }

                epicCollection.save(epicNew);
                taskCreated.setEpic(epicNew);

            }
            else{
                taskCreated.setEpic(null);
            }

            taskCreated.setId(task.id());
            taskCreated.setName(task.name());
            taskCreated.setDescription(task.description());
            taskCreated.setInProgress(task.inProgress());
            taskCreated.setDueDate(task.dueDate());
            taskCreated.setUserId(task.userId());

            taskCreated.setUserId(user.getId().toString());
            TaskModel updatedTask = taskCollection.save(taskCreated);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Void> deleteTask(String token, Long id) {
        var user = userCollection.validateUser(token);
        if (user != null){

            if (!taskCollection.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            taskCollection.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
}

