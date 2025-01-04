package main;

import java.awt.*;
import java.awt.image.BufferedImage;

public class utilityTool {
    public BufferedImage scaleImage(BufferedImage img, int newWidth, int newHeight) {
        BufferedImage newImg = new BufferedImage(newWidth, newHeight, img.getType());
        Graphics2D g = newImg.createGraphics();
        g.drawImage(img, 0, 0, newWidth, newHeight, null);
        g.dispose();
        return newImg;
    }
}
