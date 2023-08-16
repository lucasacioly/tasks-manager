package com.aps.todo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class H2factory implements repositoryFactory{
    private final ApplicationContext applicationContext;

    @Autowired
    public H2factory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    @Override
    public EpicRepository createEpicRepository() {
        return applicationContext.getBean(EpicRepositoryH2.class);
    }

    @Override
    public TaskRepository createTaskRepository() {
        return applicationContext.getBean(TaskRepositoryH2.class);
    }

    @Override
    public UserRepository createUserRepository() {
        return applicationContext.getBean(UserRepositoryH2.class);
    }
}
