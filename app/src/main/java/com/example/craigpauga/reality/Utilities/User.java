package com.example.craigpauga.reality.Utilities;

/**
 * Created by CraigPauga on 10/8/16.
 */

public class User {
    private String name;
    private String email;
    private String password;

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }

    public String getPassword(){
        return this.password;
    }
}
