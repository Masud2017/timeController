package com.timeController.timeController.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import org.springframework.core.io.ByteArrayResource;

public class TestFileDown {
    public static void main(String[] args) throws IOException {
        String url = "https://cdn.ndtv.com/tech/images/gadgets/pikachu_hi_pokemon.jpg?output-quality=80&output-format=webp";
        URL uri = new URL(url);

        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        InputStream inStream = conn.getInputStream();
        BufferedInputStream bin = new BufferedInputStream(inStream);
        byte[] btArray2 = new byte[1024];

        File file = new File("image.jpg");
        FileOutputStream foutSteam = new FileOutputStream(file);
        BufferedOutputStream bout = new BufferedOutputStream(foutSteam);

        
        int read = bin.read(btArray2);

        
        while (read != -1) {
            // Writning the downlaoded image to file
            
            bout.write(btArray2,0,read);

            
            read = bin.read(btArray2);


        }

        bout.close();


        


    }
}
