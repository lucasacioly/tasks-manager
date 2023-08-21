package com.example.auth.GoogleApi;

import com.example.auth.Models.UserModel;
import org.springframework.stereotype.Component;

@Component
public interface IGoogleLoginApi {

    UserModel googleLogin(String token);
}
