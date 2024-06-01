import javax.swing.*;
//import java.awt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class Order extends JFrame {
    private List<String> complimentaryItems;
    private List<String> mainCourse;
    private List<String> desserts;
    private Date orderTime;
    private JComboBox<String> complimentaryItemsCombo;
    private JComboBox<String> mainCourseCombo;
    private JComboBox<String> dessertsCombo;
    private JButton orderButton;
    private JLabel totalLabel;
    private Map<String, Double> priceMap;

    public Order(List<String> complimentaryItems, List<String> mainCourse, List<String> desserts) {
        this.complimentaryItems = complimentaryItems;
        this.mainCourse = mainCourse;
        this.desserts = desserts;
    }

    public Order() {
        setTitle("Order System");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel image = new BackgroundPanel("C:\\Users\\SHAHBAZ TRADERS\\IdeaProjects\\Project1\\src\\fast-food-restaurant-cartoon-3d-rendered-for-a_9829583.jpg.jpg!sw800");
        setContentPane(image);
        image.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Sample data for the comboboxes and prices
        String[] complimentaryItems = {"Salad", "Soup", "Bread"};
        String[] mainCourse = {"Steak", "Pasta", "Pizza"};
        String[] desserts = {"Ice Cream", "Cake", "Fruit Salad"};

        priceMap = new HashMap<>();
        priceMap.put("Salad", 5.0);
        priceMap.put("Soup", 4.0);
        priceMap.put("Bread", 3.0);
        priceMap.put("Steak", 20.0);
        priceMap.put("Pasta", 15.0);
        priceMap.put("Pizza", 12.0);
        priceMap.put("Ice Cream", 6.0);
        priceMap.put("Cake", 7.0);
        priceMap.put("Fruit Salad", 5.0);

        // Initialize combo boxes
        complimentaryItemsCombo = new JComboBox<>(complimentaryItems);
        mainCourseCombo = new JComboBox<>(mainCourse);
        dessertsCombo = new JComboBox<>(desserts);

        // Initialize button and label
        orderButton = new JButton("Place Order");
        totalLabel = new JLabel("Total: $0.00");
        totalLabel.setForeground(Color.WHITE);

        // Menu and prices display using GridBagLayout
        JLabel menuLabel = new JLabel("<html><font color='black' size='5'><b>Menu and Prices:</b><br>" +
                "Salad: $5.00 | Soup: $4.00 | Bread: $3.00<br>" +
                "Steak: $20.00 | Pasta: $15.00 | Pizza: $12.00<br>" +
                "Ice Cream: $6.00 | Cake: $7.00 | Fruit Salad: $5.00</font></html>");
        menuLabel.setForeground(Color.WHITE);

        // Add components to frame with GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        image.add(menuLabel, gbc);

        addComponentWithPanel(image, "Complimentary Items:", complimentaryItemsCombo, 1);
        addComponentWithPanel(image, "Main Course:", mainCourseCombo, 2);
        addComponentWithPanel(image, "Desserts:", dessertsCombo, 3);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        image.add(orderButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        image.add(totalLabel, gbc);

        // Button action listener
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> complimentaryItems = new ArrayList<>();
                complimentaryItems.add((String) complimentaryItemsCombo.getSelectedItem());

                List<String> mainCourse = new ArrayList<>();
                mainCourse.add((String) mainCourseCombo.getSelectedItem());

                List<String> desserts = new ArrayList<>();
                desserts.add((String) dessertsCombo.getSelectedItem());

                Order order = new Order(complimentaryItems, mainCourse, desserts);
                displayOrder(order);
                calculateTotal(order);
            }
        });
    }

    private void displayOrder(Order order) {
        StringBuilder summary = new StringBuilder();
        summary.append("Order Summary:\n");
        summary.append("Complimentary Items: ").append(order.getComplimentaryItems()).append("\n");
        summary.append("Main Course: ").append(order.getMainCourse()).append("\n");
        summary.append("Desserts: ").append(order.getDesserts()).append("\n");

        JOptionPane.showMessageDialog(this, summary.toString(), "Order Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    private void calculateTotal(Order order) {
        double total = 0.0;
        for (String item : order.getComplimentaryItems()) {
            total += priceMap.get(item);
        }
        for (String item : order.getMainCourse()) {
            total += priceMap.get(item);
        }
        for (String item : order.getDesserts()) {
            total += priceMap.get(item);
        }
        totalLabel.setText("Total: $" + String.format("%.2f", total));
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public List<String> getComplimentaryItems() {
        return complimentaryItems;
    }

    public List<String> getMainCourse() {
        return mainCourse;
    }

    public List<String> getDesserts() {
        return desserts;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Order().setVisible(true);
            }
        });
    }

    private void addComponentWithPanel(JPanel panel, String labelText, Component component, int gridy) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(5, 5, 5, 5);
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(component, gbc);
    }
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String imagePath) {
        backgroundImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}