package Hotel_Management_System;

public class Guest {
    private final String customerName;
    private final String customerEmail;
    private final String customerId;

    public Guest(String customerName, String customerEmail, String customerId) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}