package Model;

import java.time.LocalDate;

public class SerologicalTest {

    private int id;
    private User user;
    private LocalDate dateResult;
    private String igm;
    private String igg;

    public SerologicalTest(int id, User user, LocalDate dateResult, String igm, String igg) {
        this.id = id;
        this.user = user;
        this.dateResult = dateResult;
        this.igm = igm;
        this.igg = igg;
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

    public String getIgm() {
        return igm;
    }

    public void setIgm(String igm) {
        this.igm = igm;
    }

    public String getIgg() {
        return igg;
    }

    public void setIgg(String igg) {
        this.igg = igg;
    }

}
