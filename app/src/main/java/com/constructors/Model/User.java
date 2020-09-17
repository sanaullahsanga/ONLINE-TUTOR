package com.constructors.Model;

public class User {
    public String name, email,uid;

    public User(){

    }

    public User(String email, String name, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;

    }
}
