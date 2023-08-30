package com.example.crud.UserSystemCom;

import org.springframework.stereotype.Component;

@Component
public interface IUserService {

    public String validateUser(String token);
}
