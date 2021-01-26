package Model;

import java.time.LocalDateTime;

public class HealthCheck {

    private int id;
    private User user;
    private LocalDateTime dateOfCheck;
    private String fever;
    private String respiratoryDisorder;
    private String smellTasteDisorder;

    public HealthCheck(){

    }

    public HealthCheck(int id, User user, LocalDateTime dateOfCheck, String fever, String respiratoryDisorder, String smellTasteDisorder) {
        this.id = id;
        this.user = user;
        this.dateOfCheck = dateOfCheck;
        this.fever = fever;
        this.respiratoryDisorder = respiratoryDisorder;
        this.smellTasteDisorder = smellTasteDisorder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getDateOfCheck() {
        return dateOfCheck;
    }

    public void setDateOfCheck(LocalDateTime dateOfCheck) {
        this.dateOfCheck = dateOfCheck;
    }

    public String getFever() {
        return fever;
    }

    public void setFever(String fever) {
        this.fever = fever;
    }

    public String getRespiratoryDisorder() {
        return respiratoryDisorder;
    }

    public void setRespiratoryDisorder(String respiratoryDisorder) {
        this.respiratoryDisorder = respiratoryDisorder;
    }

    public String getSmellTasteDisorder() {
        return smellTasteDisorder;
    }

    public void setSmellTasteDisorder(String smellTasteDisorder) {
        this.smellTasteDisorder = smellTasteDisorder;
    }

}
