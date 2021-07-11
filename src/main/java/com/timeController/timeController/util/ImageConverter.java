package com.timeController.timeController.util;

import java.awt.image.BufferedImage;


public interface ImageConverter {
    public BufferedImage getJpgToGif(BufferedImage jpgImage);
    public BufferedImage getGifToJpg( BufferedImage gifImage);
    public BufferedImage getJpgToPng(BufferedImage jpgImage);
    public BufferedImage getPngToJpg(BufferedImage gifImage);
}
