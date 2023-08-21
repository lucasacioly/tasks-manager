package com.example.auth.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("h2")
public class H2factory implements RepositoryFactory{
    private final ApplicationContext applicationContext;

    @Autowired
    public H2factory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public UserRepository createUserRepository() {
        return applicationContext.getBean(UserRepository.class);
    }
}