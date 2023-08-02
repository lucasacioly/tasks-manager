package com.aps.todo.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class EpicDaoFactory {

    private final ApplicationContext applicationContext;

    @Autowired
    public EpicDaoFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public EpicDao createEpicRepository() {
        String activeProfile = applicationContext.getEnvironment().getProperty("spring.profiles.active");

        if ("h2".equals(activeProfile)) {
            System.out.println("H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2H2");
            return  applicationContext.getBean(EpicDaoH2.class);
        } else if ("postgres".equals(activeProfile)) {
            System.out.println("postgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgrespostgres");
            return  applicationContext.getBean(EpicDaoPostgreSQL.class);
        } else {
            throw new IllegalArgumentException("Invalid database profile specified.");
        }
    }
}
