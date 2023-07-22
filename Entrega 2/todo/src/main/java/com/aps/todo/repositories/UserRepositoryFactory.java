package com.aps.todo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryFactory {
    private final ApplicationContext applicationContext;

    @Autowired
    public UserRepositoryFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public UserRepository createUserRepository() {
        String activeProfile = applicationContext.getEnvironment().getProperty("spring.profiles.active");

        if ("h2".equals(activeProfile)) {
            return  applicationContext.getBean(UserRepositoryH2.class);
        } else if ("postgres".equals(activeProfile)) {
            return  applicationContext.getBean(UserRepositoryPostgreSQL.class);
        } else {
            throw new IllegalArgumentException("Invalid database profile specified.");
        }
    }
}
