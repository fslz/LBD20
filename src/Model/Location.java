package Model;

public class Location {

    private int id;
    private String name;
    private String city;
    private String category;

    public Location(int id, String name, String city, String category) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
