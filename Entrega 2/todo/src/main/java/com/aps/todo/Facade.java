package com.aps.todo;

import com.aps.todo.controlador.EpicControlador;
import com.aps.todo.controlador.TaskControlador;
import com.aps.todo.controlador.UserControlador;
import com.aps.todo.dtos.TaskRecordDto;
import com.aps.todo.models.EpicModel;
import com.aps.todo.models.TaskModel;
import com.aps.todo.models.UserModel;
import com.aps.todo.daos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Facade {

    private static Facade instance;

    //@Autowired
    private final EpicDao epicDao;

    //@Autowired
    private final TaskDao taskDao;

    //@Autowired
    private final UserDao userDao;

    //@Autowired
    private EpicControlador epicControlador;
    //@Autowired
    private UserControlador userControlador;
    //@Autowired
    private TaskControlador taskControlador;

    @Autowired
    public Facade(EpicControlador epicControlador, TaskControlador taskControlador, UserControlador userControlador,
                  TaskDaoFactory taskDaoFactory, EpicDaoFactory epicDaoFactory,
                  UserDaoFactory userDaoFactory) {
        this.epicControlador = epicControlador;
        this.taskControlador = taskControlador;
        this.userControlador = userControlador;

        this.epicDao = epicDaoFactory.createEpicRepository();
        this.taskDao = taskDaoFactory.createTaskRepository();
        this.userDao = userDaoFactory.createUserRepository();
    };

    /*
    //SINGLETON CLASS RETRIEVE
    public static synchronized Facade getInstance(){
        if (instance == null) {
            instance = new Facade();
        }
        return instance;
    };*/


    //EPICS
    public ResponseEntity<List<EpicModel>> getAllEpics(String token){
        return this.epicControlador.getAllEpics(token);
    }

    public ResponseEntity<EpicModel> getEpicById(String token, Long id){
        return this.epicControlador.getEpicById(token, id);
    }

    public ResponseEntity<EpicModel> postNewEpic(String token, EpicModel epic){
        return this.epicControlador.createEpic(token, epic);
    }

    public ResponseEntity<EpicModel> putEpic(String token, Long id, EpicModel epic){
        return this.epicControlador.updateEpic(token, id, epic);
    }

    public ResponseEntity<Void> deleteEpic(String token, Long id){
        return this.epicControlador.deleteEpic(token, id);
    }

    // TASKS

    public ResponseEntity<List<TaskRecordDto>> getAllTasks(String token){
        return this.taskControlador.getAllTasks(token);
    }

    public ResponseEntity<TaskRecordDto> getTaskById(String token, Long id){
        return this.taskControlador.getTaskById(token, id);
    }

    public ResponseEntity<TaskRecordDto> postNewTask(String token, TaskRecordDto task){
        return this.taskControlador.createTask(token, task);
    }

    public ResponseEntity<TaskModel> putTask(String token, Long id, TaskRecordDto task){
        return this.taskControlador.updateTask(token, id, task);
    }

    public ResponseEntity<Void> deleteTask(String token, Long id){
        return this.taskControlador.deleteTask(token, id);
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

    public ResponseEntity<UserModel> googleLogin(String token){ return this.userControlador.googleLogin(token);}


}
