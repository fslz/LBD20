package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    /**
     * Called by the UserController
     * Returns an ObservableList of type User containing every user in the db
     *
     * @return
     */
    public ObservableList<User> getUsersAll() {

        ObservableList<User> userList = FXCollections.observableArrayList();

        try {

            Connection connection = new DbConnector().getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users;");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                userList.add(new User(rs.getInt("user_id"),
                                      rs.getString("first_name"),
                                      rs.getString("last_name"),
                                      rs.getString("gender"),
                                      rs.getDate("date_of_birth"),
                                      rs.getDate("date_of_death"))
                );

            }

        } catch (Exception e) {
            return null;
        }

        return userList;
    }

    public void addUser(User user){

    }

    public void deleteUser(){

    }


}
