package com.timeController.timeController.model;

import java.util.*;

public class UserInfo {
    private String name;
    private String email;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserInfo)) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(name, userInfo.name) && Objects.equals(email, userInfo.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
    
}
