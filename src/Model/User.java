package Model;

public class User { // Is this a bean?


    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private String dateOfDeath;


    public User(int id, String firstName, String lastName, String gender, String dateOfBirth, String dateOfDeath) {
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
        return this.firstName;
    }

    public String getLastName() {

        return this.lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getDateOfDeath() {
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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfDeath(String dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }


}