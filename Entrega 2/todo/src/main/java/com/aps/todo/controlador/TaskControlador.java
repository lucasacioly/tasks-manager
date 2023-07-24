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
import java.util.Optional;

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

                epic.setTotalTasks(epic.getTotalTasks() + 1);

                if (!task.inProgress()){
                    epic.setTasksDone(epic.getTotalTasks() + 1);
                }
                epicRepository.save(epic);

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

