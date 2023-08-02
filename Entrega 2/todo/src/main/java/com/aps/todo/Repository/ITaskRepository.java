package com.aps.todo.Repository;

import com.aps.todo.models.TaskModel;

import java.util.List;
import java.util.Optional;

public interface ITaskRepository {

    public List<TaskModel> getUserTasks (String userId);
    public Optional<TaskModel> findById(Long id);
    public TaskModel save(TaskModel task);
    public boolean existsById(Long id);
    public void deleteById(Long id);

}
