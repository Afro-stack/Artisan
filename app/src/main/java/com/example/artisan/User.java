package com.example.artisan;

public class User {
    public String name, email, password, cnfpassword;

    public User(String name, String email, String password){

    }
    public User(String name, String email, String password, String cnfpassword){
        this.name = name;
        this.email = email;
        this.password = password;
        this.cnfpassword = password;
    }
}
