package Model;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.util.Date;

public class User { // Is this a bean?


    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dateOfBirth;
    private Date dateOfDeath;


    public User(int id, String firstName, String lastName, String gender, Date dateOfBirth, Date dateOfDeath) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;

    }


    // Getters
    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName.toString();
    }

    public String getLastName() { return this.lastName.toString(); }

    public String getGender() {
        return this.gender;
    }

    public Date getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Date getDateOfDeath() {
        return this.dateOfDeath;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfDeath(Date dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }


}