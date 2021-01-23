package Model;


import java.time.LocalDateTime;
import java.util.List;


public class User {


    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDateTime dateOfBirth;
    private LocalDateTime dateOfDeath;

    // Mapped Associations
    private List<Contact> contactList;
    private List<Relationship> relationshipList;
    private List<Swab> swabList;
    private List<SerologicalTest> serologicalTestList;
    private List<HealthCheck> healthCheckList;


    public User(int id, String userName, String firstName, String lastName, String gender, LocalDateTime dateOfBirth, LocalDateTime dateOfDeath) {

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

    public List<Relationship> getRelationshipList() {
        return relationshipList;
    }

    public void setRelationshipList(List<Relationship> relationshipList) {
        this.relationshipList = relationshipList;
    }

    public List<Swab> getSwabList() {
        return swabList;
    }

    public void setSwabList(List<Swab> swabList) {
        this.swabList = swabList;
    }

    public List<SerologicalTest> getSerologicalTestList() {
        return serologicalTestList;
    }

    public void setSerologicalTestList(List<SerologicalTest> serologicalTestList) {
        this.serologicalTestList = serologicalTestList;
    }

    public List<HealthCheck> getHealthCheckList() {
        return healthCheckList;
    }

    public void setHealthCheckList(List<HealthCheck> healthCheckList) {
        this.healthCheckList = healthCheckList;
    }

    public int getId() {
        return this.id;
    }

    public String getUserName() { return userName; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getGender() { return gender; }

    public LocalDateTime getDateOfBirth() { return this.dateOfBirth; }

    public LocalDateTime getDateOfDeath() { return this.dateOfDeath; }

    public void setId(int id) { this.id = id; }

    public void setUserName(String userName) { this.userName = userName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setGender(String gender) { this.gender = gender; }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfDeath(LocalDateTime dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

}