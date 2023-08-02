package com.aps.todo.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TaskDaoFactory {
    private final ApplicationContext applicationContext;

    @Autowired
    public TaskDaoFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public TaskDao createTaskRepository() {
        String activeProfile = applicationContext.getEnvironment().getProperty("spring.profiles.active");

        if ("h2".equals(activeProfile)) {
            return  applicationContext.getBean(TaskDaoH2.class);
        } else if ("postgres".equals(activeProfile)) {
            return  applicationContext.getBean(TaskDaoPostgreSQL.class);
        } else {
            throw new IllegalArgumentException("Invalid database profile specified.");
        }
    }
}
