package com.github.darshan744.TestApplication;

public class UserModel {
    private String name;
    private String userName;
    private String email;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "UserModel [name=" + name + ", userName=" + userName + ", email=" + email + "]";
    }
    
    
}
