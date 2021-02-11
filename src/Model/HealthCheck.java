package Model;

import java.time.LocalDate;

public class HealthCheck {

    private int id;
    private User user;
    private LocalDate dateOfCheck;
    private String fever;
    private String respiratoryDisorder;
    private String smellTasteDisorder;

    public HealthCheck(){

    }

    public HealthCheck(int id, User user, LocalDate dateOfCheck, String fever, String respiratoryDisorder, String smellTasteDisorder) {
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

    public LocalDate getDateOfCheck() {
        return dateOfCheck;
    }

    public void setDateOfCheck(LocalDate dateOfCheck) {
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
