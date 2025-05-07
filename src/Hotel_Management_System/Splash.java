package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;

public class Splash extends JFrame {
    Splash() {
        // Load the image icon
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("Visuals/lv_0_20240530170505.gif"));
        JLabel label = new JLabel(imageIcon);

        // Get the screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;


        label.setBounds(0, 0, screenWidth, screenHeight);
        add(label);

        setLayout(null);


        setUndecorated(true);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        gd.setFullScreenWindow(this);
        getContentPane().setBackground(Color.BLACK);

        setVisible(true);

        try {
            Thread.sleep(5000);
            new ChooseForm();
            setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            gd.setFullScreenWindow(null);
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
