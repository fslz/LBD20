package Model;

import java.time.LocalDate;


public class Contact {

    User user1;
    User user2;
    Location location;
    LocalDate dateReceived;

    public Contact(User user1, User user2, Location location, LocalDate dateReceived){
        this.user1 = user1;
        this.user2 = user2;
        this.location = location;
        this.dateReceived = dateReceived;
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


    /*
    public Contact(User user1, User user2, Location location, LocalDate dateReceived){
        this.user1 = user1;
        this.user2 = user2;
        this.location = location;
        this.dateReceived = dateReceived;
    }
    */

    /*
    public Contact(ArrayList<User> userList, Location location, LocalDate dateReceived){
        this.userList = userList;
        this.location = location;
        this.dateReceived = dateReceived;
    }

}
*/