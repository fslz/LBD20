package Model;

import java.time.LocalDate;


public class Swab {

    private int id;
    private User user;
    private LocalDate dateResult;
    private String positivity;

    public Swab(){

    }

    public Swab(int id, User user, LocalDate dateResult, String positivity) {
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

    public LocalDate getDateResult() {
        return dateResult;
    }

    public void setDateResult(LocalDate dateResult) {
        this.dateResult = dateResult;
    }

    public String getPositivity() {
        return positivity;
    }

    public void setPositivity(String positivity) {
        this.positivity = positivity;
    }

}
