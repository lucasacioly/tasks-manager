package com.aps.todo;

import com.aps.todo.controlador.EpicControlador;
import com.aps.todo.controlador.TaskControlador;
import com.aps.todo.controlador.UserControlador;
import com.aps.todo.models.EpicModel;
import com.aps.todo.models.TaskModel;
import com.aps.todo.models.UserModel;
import com.aps.todo.repositories.*;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Facade {

    private final EpicRepository epicRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    private EpicControlador epicControlador;
    private UserControlador userControlador;
    private TaskControlador taskControlador;

    @Autowired
    public Facade(EpicControlador epicControlador, TaskControlador taskControlador,
                  TaskRepositoryFactory taskRepositoryFactory, EpicRepositoryFactory epicRepositoryFactory,
                  UserRepositoryFactory userRepositoryFactory, UserControlador userControlador) {
        this.epicControlador = epicControlador;
        this.taskControlador = taskControlador;
        this.userControlador = userControlador;
        this.epicRepository = epicRepositoryFactory.createEpicRepository();
        this.taskRepository = taskRepositoryFactory.createTaskRepository();
        this.userRepository = userRepositoryFactory.createUserRepository();
    }

    //EPICS
    public ResponseEntity<List<EpicModel>> getAllEpics(){
        return this.epicControlador.getAllEpics();
    }

    public ResponseEntity<EpicModel> getEpicById(Long id){
        return this.epicControlador.getEpicById(id);
    }

    public ResponseEntity<EpicModel> postNewEpic(EpicModel epic){
        return this.epicControlador.createEpic(epic);
    }

    public ResponseEntity<EpicModel> putEpic(Long id, EpicModel epic){
        return this.epicControlador.updateEpic(id, epic);
    }

    public ResponseEntity<Void> deleteEpic(Long id){
        return this.epicControlador.deleteEpic(id);
    }

    // TASKS

    public ResponseEntity<List<TaskModel>> getAllTasks(){
        return this.taskControlador.getAllTasks();
    }

    public ResponseEntity<TaskModel> getTaskById(Long id){
        return this.taskControlador.getTaskById(id);
    }

    public ResponseEntity<TaskModel> postNewTask(TaskModel task){
        return this.taskControlador.createTask(task);
    }

    public ResponseEntity<TaskModel> putTask(Long id, TaskModel task){
        return this.taskControlador.updateTask(id, task);
    }

    public ResponseEntity<Void> deleteTask(Long id){
        return this.taskControlador.deleteTask(id);
    }

    // USERS

    public ResponseEntity<List<UserModel>> getAllUsers(){
        return this.userControlador.getAllUsers();
    }

    public ResponseEntity<UserModel> getUserById(Long id){
        return this.userControlador.getUserById(id);
    }

    public ResponseEntity<UserModel> postNewUser(UserModel user){
        return this.userControlador.createUser(user);
    }

    public ResponseEntity<UserModel> putUser(Long id, UserModel user){
        return this.userControlador.updateUser(id, user);
    }

    public ResponseEntity<Void> deleteUser(Long id){
        return this.userControlador.deleteUser(id);
    }

    public ResponseEntity<UserModel> signIn(String email, String password){ return this.userControlador.signIn(email, password);}
}
