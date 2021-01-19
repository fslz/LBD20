package Model;

import java.time.LocalDateTime;

public class SerologicalTest {

    private int id;
    private User user;
    private LocalDateTime dateResult;
    private String igm;
    private String igg;

    public SerologicalTest(int id, User user, LocalDateTime dateResult, String igm, String igg) {
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

    public LocalDateTime getDateResult() {
        return dateResult;
    }

    public void setDateResult(LocalDateTime dateResult) {
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
