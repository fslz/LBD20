package Model;

import java.time.LocalDate;
import java.util.List;


public class User {


    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private List<Contact> contactList;
    private List<Relationship> relationshipList;
    private List<Swab> swabList;
    private List<SerologicalTest> serologicalTestList;
    private List<HealthCheck> healthCheckList;


    public User(int id, String userName, String firstName, String lastName, String gender, LocalDate dateOfBirth, LocalDate dateOfDeath) {

        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        contactList = null;
        relationshipList = null;
        swabList = null;
        serologicalTestList = null;
        healthCheckList = null;
    }






    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }







    // Getters
    public int getId() {
        return this.id;
    }

    public String getUserName() { return userName; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getGender() { return gender; }

    public LocalDate getDateOfBirth() { return this.dateOfBirth; }

    public LocalDate getDateOfDeath() { return this.dateOfDeath; }

    // Setters
    public void setId(int id) { this.id = id; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setGender(String gender) { this.gender = gender; }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

}