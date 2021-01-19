package Model;

import java.time.LocalDateTime;

public class Swab {

    private int id;
    private User user;
    private LocalDateTime dateResult;
    private String positivity;

    public Swab(int id, User user, LocalDateTime dateResult, String positivity) {
        this.id = id;
        this.user = user;
        this.dateResult = dateResult;
        this.positivity = positivity;
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

    public String getPositivity() {
        return positivity;
    }

    public void setPositivity(String positivity) {
        this.positivity = positivity;
    }

}
