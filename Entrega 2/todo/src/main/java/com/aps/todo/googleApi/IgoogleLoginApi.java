package com.aps.todo.googleApi;

import com.aps.todo.models.UserModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public interface IgoogleLoginApi {

    ResponseEntity<UserModel> googleLogin(String token);
}
