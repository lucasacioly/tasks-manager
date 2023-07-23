package com.aps.todo.controllers;

import com.aps.todo.Facade;
import com.aps.todo.models.UserModel;
import com.aps.todo.models.signInParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
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
    @CrossOrigin
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return fachada.getAllUsers();
    }

    @GetMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return fachada.getUserById(id);
    }

    @PostMapping
    @CrossOrigin
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel user) {
        return fachada.postNewUser(user);
    }

    @PutMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<UserModel> updateUser(@PathVariable Long id, @RequestBody UserModel user){
        return fachada.putUser(id, user);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return fachada.deleteUser(id);
    }

    @PostMapping("/signin")
    @CrossOrigin
    public ResponseEntity<UserModel> signIn(@RequestBody signInParameters input) {

        return fachada.signIn(input.email, input.password);
    }

    /*@GetMapping("/googleSignUp")
    public ResponseEntity<UserModel> googleSignUp(@AuthenticationPrincipal OAuth2User principal) {
        String name = principal.getAttribute("name");
        String email = principal.getAttribute("email");

        return fachada.googleSignUp(email, name);
    }


    @GetMapping("/googleSignIn")
    public ResponseEntity<UserModel> googleSignIn(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");

        return fachada.googleSignIn(email);
    }*/
}