package com.timeController.timeController.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="profile_image")
public class profileImageModel {
    @Id
    @GeneratedValue
    private long id;

    // @Lob
    // private byte[] image;
    private String imageName;
    @OneToOne
    private User user;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageName() {
        return this.imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
        if (!(o instanceof profileImageModel)) {
            return false;
        }
        profileImageModel profileImageModel = (profileImageModel) o;
        return id == profileImageModel.id && Objects.equals(imageName, profileImageModel.imageName) && Objects.equals(user, profileImageModel.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageName, user);
    }
    

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", imageName='" + getImageName() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
    
}
