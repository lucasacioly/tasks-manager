package com.aps.todo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EpicRepositoryFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public EpicRepositoryFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public EpicRepository createEpicRepository() {
        String activeProfile = applicationContext.getEnvironment().getProperty("spring.profiles.active");

        if ("h2".equals(activeProfile)) {
            System.out.println("H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2");
            return  applicationContext.getBean(EpicRepositoryH2.class);
        } else if ("postgres".equals(activeProfile)) {
            System.out.println("postgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgres");
            return  applicationContext.getBean(EpicRepositoryPostgreSQL.class);
        } else {
            throw new IllegalArgumentException("Invalid database profile specified.");
        }
    }
}
