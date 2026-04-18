package edu.farmingdale.csc325capstone;

import java.util.HashMap;

public class User {
    private String name;
    private String email;
    private String password;
    private HashMap<String, Object> builds;

    public User(String name, String email, String password, HashMap<String, Object> builds){
        this.name=name;
        this.email=email;
        this.password=password;
        this.builds=builds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
