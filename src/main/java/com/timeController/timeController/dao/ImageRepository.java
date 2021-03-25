package com.timeController.timeController.dao;

import com.timeController.timeController.model.User;
import com.timeController.timeController.model.profileImageModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<profileImageModel,Long>{
    profileImageModel findByUser(User user);
}
