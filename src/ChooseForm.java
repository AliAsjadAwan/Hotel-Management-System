import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ChooseForm extends JFrame {
    private boolean isManagerSelected = false;
    private JButton MANAGER;
    private JButton RECIPIONIST;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private final String[] Managername = {"Mubeen", "Asjad"};
    private final String[] Managerpassword = {"Mubeen123", "Asjad123"};
    private final String[] Recipname = {"Umar", "Hassaan"};
    private final String[] Recippassword = {"Umar123", "Hassaan123"};

    public ChooseForm() {
        // Set frame properties
        setTitle("Admin");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setBackground(new Color(0, 0, 0, 0));
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
        layeredPane.setBounds(100, 200, screenSize.width, screenSize.height);

        // Create and set the background GIF
        ImageIcon imageIcon = new ImageIcon(ClassLoader.getSystemResource("Dashboard.gif"));
        JLabel backgroundLabel = new JLabel(imageIcon);
        backgroundLabel.setBounds(0, 0, screenSize.width, screenSize.height);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Create labels for images
        ImageIcon managerIcon = new ImageIcon(ClassLoader.getSystemResource("Manager1.png"));
        Image manager=managerIcon.getImage().getScaledInstance(200,150, Image.SCALE_DEFAULT);
        ImageIcon icon=new ImageIcon(manager);
        JLabel managerLabel = new JLabel(icon);
        managerLabel.setOpaque(false);

        ImageIcon receptionistIcon = new ImageIcon(ClassLoader.getSystemResource("Reception.png"));
        Image reception=receptionistIcon.getImage().getScaledInstance(220,150, Image.SCALE_DEFAULT);
        ImageIcon ico=new ImageIcon(reception);
        JLabel receptionistLabel = new JLabel(ico);
        receptionistLabel.setOpaque(false);

        // Create buttons
        MANAGER = new JButton("Manager");
        //MANAGER.setBackground(Color.ORANGE);
        MANAGER.setFocusable(false);
        MANAGER.setFont(new Font("Tahoma", Font.BOLD,15));
        MANAGER.setBackground(new Color(255,98,0));
        MANAGER.setForeground(Color.WHITE);
        MANAGER.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isManagerSelected = true;
                form();
                RECIPIONIST.setSelected(false);
            }
        });
        RECIPIONIST = new JButton("Receptionist");
        RECIPIONIST.setFocusable(false);
        //RECIPIONIST.setBackground(Color.orange);
        RECIPIONIST.setFont(new Font("Tahoma", Font.BOLD,15));
        RECIPIONIST.setBackground(new Color(255,98,0));
        RECIPIONIST.setForeground(Color.WHITE);
        RECIPIONIST.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isManagerSelected = false;
                //setVisible(false);
                form(); // Call the form method when receptionist button is clicked

                MANAGER.setSelected(false);
            }
        });
        // Make button backgrounds transparent
//        MANAGER.setContentAreaFilled(false);
//        MANAGER.setBorderPainted(false);
//        RECIPIONIST.setContentAreaFilled(false);
//        RECIPIONIST.setBorderPainted(false);

        // Add buttons and labels to layered pane
        layeredPane.add(managerLabel, Integer.valueOf(1));
        layeredPane.add(receptionistLabel, Integer.valueOf(1));
        layeredPane.add(MANAGER, Integer.valueOf(1));
        layeredPane.add(RECIPIONIST, Integer.valueOf(1));

        // Create an exit button
        // ImageIcon exitIcon = new ImageIcon(ClassLoader.getSystemResource("exit_icon.png"));
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(Color.black);
        exitButton.setForeground(Color.WHITE);
        exitButton.setBounds(screenSize.width - 165, screenSize.height - 75, 100, 30);
        //exitButton.setContentAreaFilled(false);
        //exitButton.setBorderPainted(false);
        exitButton.setFocusable(false);
        exitButton.addActionListener(e -> System.exit(0));
        layeredPane.add(exitButton, Integer.valueOf(1));

//        Graphics2D g2d = (Graphics2D) layeredPane.getGraphics();
//        g2d.setColor(Color.WHITE);
//        g2d.setFont(new Font("Arial", Font.BOLD, 20));
//        String text = "LISBON CITY HOTEL";
//        FontMetrics fm = g2d.getFontMetrics();
//        int textWidth = fm.stringWidth(text);
//        int textHeight = fm.getHeight();
//        int arcWidth = screenSize.width / 2;
//        int arcHeight = 150;
//        int startX = (screenSize.width - textWidth) / 2;
//        int startY = screenSize.height / 2 - arcHeight - textHeight / 2;
//        g2d.drawArc(startX, startY, textWidth, arcHeight, 0, -180);
//        g2d.drawString(text, startX + 5, startY + arcHeight + textHeight / 2);

        // Position manager label and button
        managerLabel.setBounds(screenSize.width / 2 - 180, screenSize.height / 2 - 100, 150, 150);
        MANAGER.setBounds(screenSize.width / 2 - 180, screenSize.height / 2 + 90, 150, 40);

        // Position receptionist label and button
        receptionistLabel.setBounds(screenSize.width / 2 + 30, screenSize.height / 2 - 100, 150, 150);
        RECIPIONIST.setBounds(screenSize.width / 2 + 30, screenSize.height / 2 + 90, 150, 40);

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
        return (username.equals(Managername[0]) && Arrays.equals(Password, Managerpassword[0].toCharArray())) ||
                (username.equals(Managername[1]) && Arrays.equals(Password, Managerpassword[1].toCharArray()));
    }

    private boolean isValidUsername(String username) {
        return username.equals(Managername[0]) || username.equals(Managername[1]);
    }

    public Boolean RecipLogin(String username, char[] Password) {
        return (username.equals(Recipname[0]) && Arrays.equals(Password, Recippassword[0].toCharArray())) ||
                (username.equals(Recipname[1]) && Arrays.equals(Password, Recippassword[1].toCharArray()));
    }

    private boolean isValidUsername2(String username) {
        return username.equals(Recipname[0]) || username.equals(Recipname[1]);
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

    public void form(){
        setVisible(false);

        JFrame loginForm = new JFrame("Login Form");
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginForm.setUndecorated(true);

        // Set the background image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("Login1.jpg"));
        int bgWidth = backgroundIcon.getIconWidth();
        int bgHeight = backgroundIcon.getIconHeight();
        loginForm.setSize(bgWidth, bgHeight);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, bgWidth, bgHeight);

        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, bgWidth, bgHeight);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // Set the logo image
//        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("Logo.jpg"));
//        JLabel logoLabel = new JLabel(logoIcon);
//        int logoWidth = logoIcon.getIconWidth();
//        int logoHeight = logoIcon.getIconHeight();
//        logoLabel.setBounds(bgWidth - logoWidth - 50, (bgHeight - logoHeight) / 2, logoWidth, logoHeight);
//        layeredPane.add(logoLabel, Integer.valueOf(1));

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

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 300, 150, 30);
        layeredPane.add(loginButton, Integer.valueOf(1));

        backButton = new JButton("Back");
        backButton.setBounds(270, 300, 150, 30);
        layeredPane.add(backButton, Integer.valueOf(1));

        backButton.addActionListener(e -> {
            ChooseForm chooseForm = new ChooseForm();
            chooseForm.setVisible(true);
            loginForm.dispose();
        });

        loginButton.addActionListener(e -> {
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
    }
}
