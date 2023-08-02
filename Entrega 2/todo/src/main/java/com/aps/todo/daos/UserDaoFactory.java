package com.aps.todo.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserDaoFactory {
    private final ApplicationContext applicationContext;

    @Autowired
    public UserDaoFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public UserDao createUserRepository() {
        String activeProfile = applicationContext.getEnvironment().getProperty("spring.profiles.active");

        if ("h2".equals(activeProfile)) {
            return  applicationContext.getBean(UserDaoH2.class);
        } else if ("postgres".equals(activeProfile)) {
            return  applicationContext.getBean(UserDaoPostgreSQL.class);
        } else {
            throw new IllegalArgumentException("Invalid database profile specified.");
        }
    }
}
