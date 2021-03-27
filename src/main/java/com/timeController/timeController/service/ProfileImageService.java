package com.timeController.timeController.service;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;

public interface ProfileImageService {
    public void setImageContent(ByteArrayResource content);
    public void setImageName(String imageName);
    public void writeImageToDisk() throws IOException;
}
