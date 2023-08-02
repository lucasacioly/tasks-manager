package com.aps.todo.Repository;

import com.aps.todo.daos.EpicDao;
import com.aps.todo.daos.TaskDao;
import com.aps.todo.models.EpicModel;
import com.aps.todo.models.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskRepository implements ITaskRepository{

    private static TaskRepository instance;

    @Autowired
    private TaskDao taskDao;

    public static synchronized TaskRepository getInstance(){
        if (instance == null) {
            instance = new TaskRepository();
        }
        return instance;
    };

    public List<TaskModel> getUserTasks (String userId){
        return this.taskDao.getUserTasks(userId);
    }

    public Optional<TaskModel> findById(Long id) {
        return this.taskDao.findById(id);
    }

    public TaskModel save(TaskModel task) {
        return this.taskDao.save(task);
    }


    public boolean existsById(Long id) {
        return this.taskDao.existsById(id);
    }

    public void deleteById(Long id) {
        this.taskDao.deleteById(id);
    }


}
