package com.example.mad;

public class Admin {

    String username;
    String password;

    public Admin(String uname, String pwd)
    {
        this.username = uname;
        this.password = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
