package com.aps.todo.googleApi;

import com.aps.todo.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public interface IGoogleLoginApi {

    UserModel googleLogin(String token);
}
