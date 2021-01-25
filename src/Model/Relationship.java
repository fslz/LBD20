package Model;

import javax.management.relation.Relation;

public class Relationship {

    private int id;
    private User user1;
    private User user2;
    private String type;

    public Relationship(){

    }

    public Relationship(int id, User user1, User user2, String type) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
