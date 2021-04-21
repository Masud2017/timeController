package com.timeController.timeController.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="user")
public class User {
    @GeneratedValue
    @Id
    private long id;
    @Column(name="first_name")
    String first_name;
    @Column(name = "second_name")
    String second_name;
    @Column(name = "email")
    String email;
    @Column(name = "password")
    String password;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getSecond_name() {
        return this.second_name;
    }

    public void setSecond_name(String second_name) {
        this.second_name = second_name;
    }
    
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(first_name, user.first_name) && Objects.equals(second_name, user.second_name) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, second_name, email, password);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", first_name='" + getFirst_name() + "'" +
            ", second_name='" + getSecond_name() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
    
}
