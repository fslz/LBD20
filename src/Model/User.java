package Model;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User { // Is this a bean?


    private int id;
    private SimpleStringProperty userName;
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty gender;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;


    public User(int id, String userName, String firstName, String lastName, String gender, LocalDate dateOfBirth, LocalDate dateOfDeath) {

        this.id = id;
        this.userName = new SimpleStringProperty(userName);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.gender = new SimpleStringProperty(gender);
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;

    }


    // Getters
    public int getId() {
        return this.id;
    }

    public String getUserName() { return userName.get(); }

    public String getFirstName() { return firstName.get(); }

    public String getLastName() { return lastName.get(); }

    public String getGender() { return gender.get(); }

    public LocalDate getDateOfBirth() { return this.dateOfBirth; }

    public LocalDate getDateOfDeath() { return this.dateOfDeath; }

    // Setters
    public void setId(int id) { this.id = id; }

    public void setUserName(String userName) { this.userName.set(userName); }

    public void setFirstName(String firstName) { this.firstName.set(firstName); }

    public void setLastName(String lastName) { this.lastName.set(lastName); }

    public void setGender(String gender) {
        this.gender = new SimpleStringProperty(gender);
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

}