package Model;

import java.time.LocalDate;

public class HealthCheck {

    private int id;
    private User user;
    private LocalDate dateResult;
    private String fever;
    private String respiratoryDisorder;
    private String smellTasteDisorder;

    public HealthCheck(){

    }

    public HealthCheck(int id, User user, LocalDate dateResult, String fever, String respiratoryDisorder, String smellTasteDisorder) {
        this.id = id;
        this.user = user;
        this.dateResult = dateResult;
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

    public LocalDate getDateResult() {
        return dateResult;
    }

    public void setDateResult(LocalDate dateResult) {
        this.dateResult = dateResult;
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
