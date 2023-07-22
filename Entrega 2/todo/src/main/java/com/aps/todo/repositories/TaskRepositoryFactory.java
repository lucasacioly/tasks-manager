package com.aps.todo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskRepositoryFactory {
    private final ApplicationContext applicationContext;

    @Autowired
    public TaskRepositoryFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public TaskRepository createTaskRepository() {
        String activeProfile = applicationContext.getEnvironment().getProperty("spring.profiles.active");

        if ("h2".equals(activeProfile)) {
            return  applicationContext.getBean(TaskRepositoryH2.class);
        } else if ("postgres".equals(activeProfile)) {
            return  applicationContext.getBean(TaskRepositoryPostgreSQL.class);
        } else {
            throw new IllegalArgumentException("Invalid database profile specified.");
        }
    }
}
