package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class UserDAO {

    /**
     * Called by the UserController
     * Returns an ObservableList of type User containing every user in the db
     *
     * @return ObservableList<User>
     */
    public ObservableList<User> getUsersAll() {

        ObservableList<User> userList = FXCollections.observableArrayList();
        int count = 0;

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {

                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        null // TODO FIX THIS (if rs.getDate() == null then cannot convert to LocalDate by using .toLocalDate()
                        //rs.getDate("date_of_death").toLocalDate()
                );


                userList.add(user);

                /*
                userList.add(
                        new User(
                                rs.getInt("user_id"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("gender"),
                                rs.getDate("date_of_birth").toLocalDate(),
                                rs.getDate("date_of_death").toLocalDate()
                        )
                );
                */


            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return userList;

    }


    public void addUser(User user) {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users VALUES (0, ?, ?, ?, ?, ?)");

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getGender());
            pstmt.setDate(4, java.sql.Date.valueOf( user.getDateOfBirth() ));
            pstmt.setDate(5, java.sql.Date.valueOf( user.getDateOfDeath() ));

            int rows = pstmt.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void deleteUser(User user) {

        Connection connection = new DbConnector().getConnection();

        try{

            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
            pstmt.setInt(1, user.getId());

            pstmt.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }


    public void updateUser(User user){

        Connection connection = new DbConnector().getConnection();
        try {

            PreparedStatement pstmt = connection.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, gender = ?, date_of_birth = ?, date_of_death = ? WHERE user_id = ?");

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getGender());
            pstmt.setDate(4, java.sql.Date.valueOf( user.getDateOfBirth() ));
            pstmt.setDate(5, java.sql.Date.valueOf( user.getDateOfDeath() ));
            pstmt.setInt(6, user.getId());

            int row = pstmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }


}
