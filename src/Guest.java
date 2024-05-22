import java.util.ArrayList;
import java.util.List;

class Guest {
    private String name;
    private String address;
    private String contactDetails;
    private List<String> stayHistory;
    private String feedback;

    public Guest(String name, String address, String contactDetails) {
        this.name = name;
        this.address = address;
        this.contactDetails = contactDetails;
        this.stayHistory = new ArrayList<>();
        this.feedback = "";
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public List<String> getStayHistory() {
        return stayHistory;
    }

    public void addStay(String stayDetails) {
        stayHistory.add(stayDetails);
    }

    public String getFeedback() {
        return feedback;
    }

    public void addFeedback(String feedback) {
        this.feedback = feedback;
    }
}
