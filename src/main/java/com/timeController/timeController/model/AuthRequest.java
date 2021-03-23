package com.timeController.timeController.model;

import java.io.Serializable;

public class AuthRequest implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 6017923934414008634L;
    private String email;
    private String password;

    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {return this.email;}
    public void getEmail(String email) {this.email = email;}

    public String getPassword() {return this.password;}
    public void getPassword(String password) {this.password = password;}
    
}
