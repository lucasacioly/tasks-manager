package com.aps.todo.controlador;

import com.aps.todo.Repository.IEpicRepository;
import com.aps.todo.Repository.ITaskRepository;
import com.aps.todo.Repository.IUserRepository;
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

    private final IUserRepository userRepository;
    private  final IEpicRepository epicRepository;
    private final ITaskRepository taskRepository;

    @Autowired
    public TaskControlador(ITaskRepository taskRepository, IUserRepository userRepository, IEpicRepository epicRepository) {
        this.epicRepository = epicRepository;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public ResponseEntity<List<TaskRecordDto>> getAllTasks(String token) {
        var user = userRepository.validateUser(token);
        if (user != null){

            List<TaskModel> tasks = taskRepository.getUserTasks(user.getId().toString());

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
        var user = userRepository.validateUser(token);

        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        TaskModel task = taskRepository.findById(id).orElse(null);

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
        var user = userRepository.validateUser(token);
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
            EpicModel epic = epicRepository.findById(task.epicId()).orElse(null);

            epic.setTotalTasks(epic.getTotalTasks() + 1);

            if (!task.inProgress()){
                epic.setTasksDone(epic.getTotalTasks() + 1);
            }
            epicRepository.save(epic);

            taskCreated.setEpic(epic);
        }

        taskRepository.save(taskCreated);

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
        var user = userRepository.validateUser(token);
        if (user != null){

            if (!taskRepository.existsById(id)) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            TaskModel taskBd = taskRepository.findById(id).orElse(null);
            EpicModel epicNew = epicRepository.findById(task.epicId()).orElse(null);

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
                    EpicModel epicBD = epicRepository.findById(taskBd.getEpic().getId()).orElse(null);

                    if (epicBD != null) {
                        epicNew.setTotalTasks(epicNew.getTotalTasks() - 1);

                        if (!taskBd.getInProgress()) {
                            epicNew.setTasksDone(epicNew.getTasksDone() - 1);
                        }

                        epicRepository.save(epicBD);
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

            epicRepository.save(epicNew);


            TaskModel taskCreated = new TaskModel();
            taskCreated.setId(task.id());
            taskCreated.setEpic(epicNew);
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

