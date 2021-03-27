package com.timeController.timeController.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.timeController.timeController.dao.ImageRepository;
import com.timeController.timeController.dao.UserRepository;
import com.timeController.timeController.model.User;
import com.timeController.timeController.model.profileImageModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfileImageServiceImpl implements ProfileImageService{
    @Autowired
    ImageRepository imgRepo;
    @Autowired
    UserRepository userRepo;

    private String imageName;


    @Value("${profileImageSize.Limit}")
    private String profileImageSizeLimit;

    private MultipartFile content;

    @Override
    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageName() {
        return this.imageName;
    }

    @Override
    public void setImageContent(MultipartFile content) {
        this.content = content;
    }
    
    @Override
    public void writeImageToDisk() throws IOException {
        String path = "/home/unroot/imageStorageDb/";
        
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(userDetails.getUsername());
        profileImageModel imgModelChecker = imgRepo.findByUser(user);

        if (imgModelChecker == null) {
        
            File fp = new File(path+this.content.getOriginalFilename());

            profileImageModel imgModel = new profileImageModel();
            imgModel.setImageName(path + this.content.getOriginalFilename());
            imgModel.setUser(user);
            final byte[] imageContent;
            InputStream in;
            try {
                in = content.getInputStream();
                BufferedInputStream input = new BufferedInputStream(in);

                imageContent = input.readAllBytes();
                
                try {
                    FileOutputStream out = new FileOutputStream(fp);
                    BufferedOutputStream output = new BufferedOutputStream(out);
                    output.write(imageContent);
                    imgRepo.save(imgModel);
                    try {
                        out.write(imageContent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
        } else {
            System.out.println("Sorry your profile image is already exist!!");
        }
    }
    
}
