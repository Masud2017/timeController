package com.timeController.timeController.service;

import java.io.IOException;

import com.timeController.timeController.model.User;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageService {
    public void setImageContent(MultipartFile content);
    public void setImageName(String imageName);
    public void writeImageToDisk() throws IOException;
}
