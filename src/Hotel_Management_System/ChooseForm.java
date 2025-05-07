package Hotel_Management_System;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ChooseForm extends JFrame {
    private boolean isManagerSelected = false;
    private final JButton  MANAGER;
    private final JButton RECEPTIONIST;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final String[] ManagerName = {"Mubeen", "Asjad"};
    private final String[] ManagerPassword = {"Mubeen123", "Asjad123"};
    private final String[] ReceptionistName = {"Umar", "Hassaan"};
    private final String[] ReceptionistPassword = {"Umar123", "Hassaan123"};

    public ChooseForm() {
        // Set frame properties
        setTitle("Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.BLACK);

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);

        // Create a layered pane
        JLayeredPane layeredPane = new JLayeredPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawText(g);
            }
        };
        layeredPane.setBounds(0, 0, screenSize.width, screenSize.height);

        // Create and set the background GIF
        ImageIcon backgroundIcon = ImageUtils.loadImageIcon("Dashboard.gif");
        if (backgroundIcon != null) {
            JLabel backgroundLabel = new JLabel(backgroundIcon);
            backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);
            layeredPane.add(backgroundLabel, Integer.valueOf(0));
        }

        // Create labels for images
        ImageIcon managerIcon = ImageUtils.loadScaledImageIcon("Manager1.png", 200, 150);
        JLabel managerLabel = new JLabel(managerIcon);
        managerLabel.setOpaque(false);

        ImageIcon receptionistIcon = ImageUtils.loadScaledImageIcon("Reception.png", 220, 150);
        JLabel receptionistLabel = new JLabel(receptionistIcon);
        receptionistLabel.setOpaque(false);

        // Create buttons
        MANAGER = new JButton("Manager");
        MANAGER.setFocusable(false);
        MANAGER.setFont(new Font("Tahoma", Font.BOLD,15));
        MANAGER.setBackground(new Color(255,98,0));
        MANAGER.setForeground(Color.WHITE);
        MANAGER.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isManagerSelected = true;
                form();
                RECEPTIONIST.setSelected(false);
            }
        });

        RECEPTIONIST = new JButton("Receptionist");
        RECEPTIONIST.setFocusable(false);
        RECEPTIONIST.setFont(new Font("Tahoma", Font.BOLD,15));
        RECEPTIONIST.setBackground(new Color(255,98,0));
        RECEPTIONIST.setForeground(Color.WHITE);
        RECEPTIONIST.addActionListener(_ -> {
            isManagerSelected = false;
            form();
            MANAGER.setSelected(false);
        });

        // Add buttons and labels to layered pane
        layeredPane.add(managerLabel, Integer.valueOf(1));
        layeredPane.add(receptionistLabel, Integer.valueOf(1));
        layeredPane.add(MANAGER, Integer.valueOf(1));
        layeredPane.add(RECEPTIONIST, Integer.valueOf(1));

        // Create an exit button
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.black);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBounds(screenSize.width - 165, screenSize.height - 75, 100, 30);
        exitButton.setFocusable(false);
        exitButton.addActionListener(_ -> System.exit(0));
        layeredPane.add(exitButton, Integer.valueOf(1));

        // Position manager label and button
        managerLabel.setBounds(screenSize.width / 2 - 180, screenSize.height / 2 - 100, 150, 150);
        MANAGER.setBounds(screenSize.width / 2 - 180, screenSize.height / 2 + 90, 150, 40);

        // Position receptionist label and button
        receptionistLabel.setBounds(screenSize.width / 2 + 30, screenSize.height / 2 - 100, 150, 150);
        RECEPTIONIST.setBounds(screenSize.width / 2 + 30, screenSize.height / 2 + 90, 150, 40);

        // Add the layered pane to the frame
        add(layeredPane);

        // Set frame to full screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            gd.setFullScreenWindow(this);
        } else {
            System.err.println("Full screen not supported");
            setSize(500, 500);
            setLocationRelativeTo(null);
        }

        setVisible(true);
        setAlwaysOnTop(true);
    }

    public Boolean ManagerLogin(String username, char[] Password) {
        return (username.equals(ManagerName[0]) && Arrays.equals(Password, ManagerPassword[0].toCharArray())) ||
                (username.equals(ManagerName[1]) && Arrays.equals(Password, ManagerPassword[1].toCharArray()));
    }

    private boolean isValidUsername(String username) {
        return username.equals(ManagerName[0]) || username.equals(ManagerName[1]);
    }

    public Boolean RecipLogin(String username, char[] Password) {
        return (username.equals(ReceptionistName[0]) && Arrays.equals(Password, ReceptionistPassword[0].toCharArray())) ||
                (username.equals(ReceptionistName[1]) && Arrays.equals(Password, ReceptionistPassword[1].toCharArray()));
    }

    private boolean isValidUsername2(String username) {
        return username.equals(ReceptionistName[0]) || username.equals(ReceptionistName[1]);
    }

    public static void main(String[] args) {
        new ChooseForm();
    }

    private void drawText(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 30));
        String text = "LISBON CITY HOTEL";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int startX = (getWidth() - textWidth) / 2;
        g2d.drawString(text, startX, 100);
    }

    public void form() {
        setVisible(false);

        JFrame loginForm = new JFrame("Login Form");
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginForm.setUndecorated(true);

        // Set the background image
        ImageIcon backgroundIcon = ImageUtils.loadImageIcon("Login1.jpg");
        if (backgroundIcon != null) {
            int bgWidth = backgroundIcon.getIconWidth();
            int bgHeight = backgroundIcon.getIconHeight();
            loginForm.setSize(bgWidth, bgHeight);

            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setBounds(0, 0, bgWidth, bgHeight);

            JLabel backgroundLabel = new JLabel(backgroundIcon);
            backgroundLabel.setBounds(0, 0, bgWidth, bgHeight);
            layeredPane.add(backgroundLabel, Integer.valueOf(0));

            // Set the logo image

            JLabel usernameLabel = new JLabel("Username");
            usernameLabel.setForeground(Color.WHITE);
            usernameLabel.setBounds(100, 200, 200, 30);
            layeredPane.add(usernameLabel, Integer.valueOf(1));

            usernameField = new JTextField();
            usernameField.setBounds(220, 200, 200, 30);
            layeredPane.add(usernameField, Integer.valueOf(1));

            JLabel passwordLabel = new JLabel("Password");
            passwordLabel.setForeground(Color.WHITE);
            passwordLabel.setBounds(100, 250, 200, 30);
            layeredPane.add(passwordLabel, Integer.valueOf(1));

            passwordField = new JPasswordField();
            passwordField.setBounds(220, 250, 200, 30);
            layeredPane.add(passwordField, Integer.valueOf(1));

            JButton loginButton = new JButton("Login");
            loginButton.setBounds(100, 300, 150, 30);
            layeredPane.add(loginButton, Integer.valueOf(1));

            JButton backButton = new JButton("Back");
            backButton.setBounds(270, 300, 150, 30);
            layeredPane.add(backButton, Integer.valueOf(1));

            backButton.addActionListener(_ -> {
                ChooseForm chooseForm = new ChooseForm();
                chooseForm.setVisible(true);
                loginForm.dispose();
            });

            loginButton.addActionListener(_ -> {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                if (isManagerSelected) {
                    if (ManagerLogin(username, password)) {
                        JOptionPane.showMessageDialog(null, "Login successful");
                        new Admin();
                        loginForm.dispose();
                    } else {
                        if (isValidUsername(username)) {
                            JOptionPane.showMessageDialog(null, "Password incorrect");
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid username or password");
                        }
                    }
                } else {
                    if (RecipLogin(username, password)) {
                        JOptionPane.showMessageDialog(null, "Login successful");
                        new Recipcinoist();
                        loginForm.dispose();
                    } else {
                        if (isValidUsername2(username)) {
                            JOptionPane.showMessageDialog(null, "Password incorrect");
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid username or password");
                        }
                    }
                }
            });

            loginForm.add(layeredPane);
            loginForm.setLocationRelativeTo(null); // Center the window on the screen
            loginForm.setVisible(true);
        } else {
            // Fallback if image loading fails
            loginForm.setSize(500, 400);
            loginForm.getContentPane().setBackground(Color.BLACK);
        }
    }
}
