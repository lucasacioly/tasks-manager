package com.aps.todo.collection;

import com.aps.todo.repository.TaskRepository;
import com.aps.todo.models.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskCollection {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskCollection(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public List<TaskModel> getUserTasks (String userId){
        return this.taskRepository.getUserTasks(userId);
    }

    public Optional<TaskModel> findById(Long id) {
        return this.taskRepository.findById(id);
    }

    public TaskModel save(TaskModel task) {
        return this.taskRepository.save(task);
    }


    public boolean existsById(Long id) {
        return this.taskRepository.existsById(id);
    }

    public void deleteById(Long id) {
        this.taskRepository.deleteById(id);
    }


}
