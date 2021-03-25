package com.timeController.timeController.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="todo_model")
public class TodoModel {
    @Id
    @GeneratedValue()
    private long id;
    private String nameFile;
    private String description;
    @ManyToOne
    private User user;
    private Date date;
    private boolean done;

    public TodoModel() {
    }

    public TodoModel(long id, String nameFile, String description,User user, Date date, boolean done) {
        this.id = id;
        this.nameFile = nameFile;
        this.description = description;
        this.user = user;
        this.date = date;
        this.done = done;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNameFile() {
        return this.nameFile;
    }

    public void setNameFile(String nameFile) {
        this.nameFile = nameFile;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDone() {
        return this.done;
    }

    public boolean getDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }


    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
   

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof TodoModel)) {
            return false;
        }
        TodoModel todoModel = (TodoModel) o;
        return id == todoModel.id && Objects.equals(nameFile, todoModel.nameFile) && Objects.equals(description, todoModel.description) && Objects.equals(user, todoModel.user) && Objects.equals(date, todoModel.date) && done == todoModel.done;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nameFile, description, user, date, done);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", nameFile='" + getNameFile() + "'" +
            ", description='" + getDescription() + "'" +
            ", user='" + getUser() + "'" +
            ", date='" + getDate() + "'" +
            ", done='" + isDone() + "'" +
            "}";
    }
    
}