package Model;

import java.time.LocalDate;


public class Contact {

    private int id;
    private User user1;
    private User user2;
    private Location location;
    private LocalDate dateReceived;

    public Contact(){}

    public Contact(int id, User user1, User user2, Location location, LocalDate dateReceived){
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.location = location;
        this.dateReceived = dateReceived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDate getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }

}
