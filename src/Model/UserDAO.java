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

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = pstmt.executeQuery();


            while (rs.next()) {

                /*
                User user = new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("gender"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        null // TODO FIX THIS (if rs.getDate() == null then cannot convert to LocalDate by using .toLocalDate()
                        //rs.getDate("date_of_death").toLocalDate()
                );
                */
                int id = rs.getInt("user_id");
                String userName = rs.getString("username");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String gender = rs.getString("gender");
                LocalDate dateOfBirth = rs.getDate("date_of_birth").toLocalDate();
                LocalDate dateOfDeath;
                if (rs.getDate("date_of_death") == null)
                    dateOfDeath = null;
                else
                    dateOfDeath = rs.getDate("date_of_death").toLocalDate();


                User user = new User(id, userName, firstName, lastName, gender, dateOfBirth, dateOfDeath); // Instantiate User

                /*
                user.setId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username")); // PROBLEMA QUI
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setGender(rs.getString("gender"));
                user.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                if (rs.getDate("date_of_death").equals(null))
                    user.setDateOfDeath(null);
                else
                    user.setDateOfDeath(rs.getDate("date_of_death").toLocalDate());
                */

                userList.add(user); // Add user to the list

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return userList;

    }


    public void addUser(User user) throws SQLException {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users (user_id, username, first_name, last_name, gender, date_of_birth, date_of_death) " +
                    "VALUES (0, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getGender());
            pstmt.setDate(5, java.sql.Date.valueOf(user.getDateOfBirth()));
            if (user.getDateOfDeath() != null)
                pstmt.setDate(6, java.sql.Date.valueOf(user.getDateOfDeath()));
            else
                pstmt.setDate(6, null);


            int rows = pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getErrorCode() + " " + e.getSQLState() + " " + e.getNextException());
            throw e;

        }

    }


    public void deleteUser(User user) {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
            pstmt.setInt(1, user.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void updateUser(User user) {

        Connection connection = new DbConnector().getConnection();
        try {

            PreparedStatement pstmt = connection.prepareStatement("UPDATE users SET first_name = ?, last_name = ?, gender = ?, date_of_birth = ?, date_of_death = ? WHERE user_id = ?");

            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getGender());
            pstmt.setDate(4, java.sql.Date.valueOf(user.getDateOfBirth()));
            pstmt.setDate(5, java.sql.Date.valueOf(user.getDateOfDeath()));
            pstmt.setInt(6, user.getId());

            int row = pstmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }
    }


}
