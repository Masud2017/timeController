package com.timeController.timeController.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 */

public class MultiPartFileImpl implements MultipartFile{
    private ByteArrayResource byteArrayResource;

    public MultiPartFileImpl(ByteArrayResource byteArrayResource) {
        this.byteArrayResource = byteArrayResource;
    }

    @Override
    public String getName() {
        String fileName = this.byteArrayResource.getFilename();

        return fileName;
    }

    @Override
    public String getOriginalFilename() {
        String originalFilename = this.byteArrayResource.getFilename();

        return originalFilename;
    }

    @Override
    public String getContentType() {
        String type = "Binary";
        return type;
    }

    @Override
    public boolean isEmpty() {
        boolean isExist = this.byteArrayResource.exists();

        if (isExist) return true;
        
        return false;
    }

    @Override
    public long getSize() {
        long size = this.byteArrayResource.contentLength();
        return size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        byte[] bytes = this.byteArrayResource.getByteArray();
        return bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream in = this.byteArrayResource.getInputStream();
        return in;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileOutputStream outputStream = new FileOutputStream(dest);
        BufferedOutputStream bfOut = new BufferedOutputStream(outputStream);

        bfOut.write(this.byteArrayResource.getByteArray());

        bfOut.close();
    }
    
}
