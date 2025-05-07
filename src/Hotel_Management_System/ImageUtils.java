package Hotel_Management_System;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageUtils {
    private static final String VISUALS_DIR = "src/Visuals/";
    
    public static Image loadImage(String imageName) {
        try {
            // First try loading from file system
            File imageFile = new File(VISUALS_DIR + imageName);
            if (imageFile.exists()) {
                return ImageIO.read(imageFile);
            }
            
            // If not found, try loading from classpath
            URL imageUrl = ImageUtils.class.getClassLoader().getResource("Visuals/" + imageName);
            if (imageUrl != null) {
                return ImageIO.read(imageUrl);
            }
            
            throw new IOException("Image not found: " + imageName);
        } catch (IOException e) {
            System.err.println("Error loading image: " + imageName);
            e.printStackTrace();
            return null;
        }
    }
    
    public static ImageIcon loadImageIcon(String imageName) {
        Image image = loadImage(imageName);
        return image != null ? new ImageIcon(image) : null;
    }
    
    public static ImageIcon loadScaledImageIcon(String imageName, int width, int height) {
        Image image = loadImage(imageName);
        if (image != null) {
            Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        }
        return null;
    }
    
    public static void setBackgroundImage(JPanel panel, String imageName) {
        Image image = loadImage(imageName);
        if (image != null) {
            panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
                }
            };
        }
    }
} 