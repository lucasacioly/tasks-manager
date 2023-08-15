package com.aps.todo.models;

public class GoogleUserModel {
    private String sub;
    private String name;
    private String given_name;
    private String family_name;
    private String picture;
    private String email;
    private boolean email_verified;
    private String locale;

    public GoogleUserModel(){}

    public String getEmail(){
        return this.email;
    }

    public String getName(){
        return this.name;
    }

}

