package com.aps.todo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class PostgreSQLFactory implements repositoryFactory {
    private final ApplicationContext applicationContext;

    @Autowired
    public PostgreSQLFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public EpicRepository createEpicRepository() {
        return applicationContext.getBean(EpicRepositoryPostgreSQL.class);
    }

    @Override
    public TaskRepository createTaskRepository() {
        return applicationContext.getBean(TaskRepositoryPostgreSQL.class);
    }

    @Override
    public UserRepository createUserRepository() {
        return applicationContext.getBean(UserRepositoryPostgreSQL.class);
    }
}
