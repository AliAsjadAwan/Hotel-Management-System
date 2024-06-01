public class Guest {
    private String customerName;
    private  String customerEmail;
    private String customerId;

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