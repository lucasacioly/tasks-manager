package com.aps.todo.repository;

public interface repositoryFactory {
    EpicRepository createEpicRepository();
    TaskRepository createTaskRepository();
    UserRepository createUserRepository();
}
