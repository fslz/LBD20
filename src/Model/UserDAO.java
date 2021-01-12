package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;

public class UserDAO {

    /**
     * Called by the UserController, returns an ObservableList of type User containing every user in the db
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

                userList.add(
                        new User(
                                rs.getInt("user_id"),
                                rs.getString("username"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("gender"),
                                convertToEntityAttribute(rs.getDate("date_of_birth")),
                                convertToEntityAttribute(rs.getDate("date_of_death"))
                        )
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return userList;

    }

    /**
     * Called by the UserController
     * Inserts new user into the db
     *
     * @return void
     */
    public void addUser(User user) throws SQLException {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users (user_id, username, first_name, last_name, gender, date_of_birth, date_of_death) " +
                    "VALUES (0, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getGender());
            pstmt.setDate(5, convertToDatabaseColumn(user.getDateOfBirth()));
            pstmt.setDate(6, convertToDatabaseColumn(user.getDateOfDeath()));

            int rows = pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }

    }

    /**
     * Called by the UserController
     * Updates the user in the db
     *
     * @return void
     */
    public void updateUser(User user) throws SQLException {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("UPDATE users SET username = ?, first_name = ?, last_name = ?, gender = ?, date_of_birth = ?, date_of_death = ? WHERE user_id = ?");

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getGender());
            pstmt.setDate(5, convertToDatabaseColumn(user.getDateOfBirth()));
            pstmt.setDate(6, convertToDatabaseColumn(user.getDateOfDeath()));
            pstmt.setInt(7, user.getId());

            int row = pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
    }

    /**
     * Called by the UserController
     * Deletes the user from the db
     *
     * @return void
     */
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

    /**
     * Called internally
     * Converts safely a LocalDate to java.sql.Date
     *
     * @return java.sql.Date
     */
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return (localDate == null ? null : Date.valueOf(localDate));
    }

    /**
     * Called internally
     * Converts safely a java.sql.Date to LocalDate
     *
     * @return LocalDate
     */
    public LocalDate convertToEntityAttribute(Date date) {
        return (date == null ? null : date.toLocalDate());
    }

}
