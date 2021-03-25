package com.timeController.timeController.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import java.util.Objects;

@Entity
@Table(name="profile_image")
public class profileImageModel {
    @Id
    @GeneratedValue
    private long id;

    @Lob
    private byte[] image;
    @OneToOne
    private User user;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
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
        return id == profileImageModel.id && Objects.equals(image, profileImageModel.image) && Objects.equals(user, profileImageModel.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, user);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", image='" + getImage() + "'" +
            ", user='" + getUser() + "'" +
            "}";
    }
    
}
