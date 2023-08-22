package com.example.auth;

import com.example.auth.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserControlador userControlador ;

    @Autowired
    public UserController(UserControlador userControlador) {
        this.userControlador = userControlador;
    }

    @GetMapping
    @CrossOrigin
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return userControlador.getAllUsers();
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return userControlador.getUserById(id);
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        return userControlador.createUser(user);
    }

    @PutMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel user){
        return userControlador.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userControlador.deleteUser(id);
    }

    @PostMapping("/signin")
    @CrossOrigin
    public ResponseEntity<UserModel> signIn(@RequestBody SignInParameters input) {

        return userControlador.signIn(input.email, input.password);
    }

    @PostMapping("googleLogin")
    @CrossOrigin
    public ResponseEntity<UserModel> googleLogin(@RequestBody String token){
        return userControlador.googleLogin(token);
    }

    @GetMapping("/validateUser")
    @CrossOrigin
    public ResponseEntity<String> validateUser(@RequestHeader("token") String token){
        return userControlador.validateUser(token);
    }
}