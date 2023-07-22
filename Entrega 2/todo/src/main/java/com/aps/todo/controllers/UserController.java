package com.aps.todo.controllers;

import com.aps.todo.Facade;
import com.aps.todo.models.UserModel;
import com.aps.todo.models.signInParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private Facade fachada;

    @Autowired
    public UserController(Facade fachada) {
        this.fachada = fachada;
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return fachada.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return fachada.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        return fachada.postNewUser(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserModel> signIn(@RequestBody signInParameters input) {

        return fachada.signIn(input.email, input.password);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel user){
        return fachada.putUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return fachada.deleteUser(id);
    }

}