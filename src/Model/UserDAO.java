package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class UserDAO implements DAO<User> {


    public User getById(int id) throws SQLException {

        Connection connection = new DbConnector().getConnection();
        User user;

        try{

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            ResultSet rs = pstmt.executeQuery();

            user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("gender"),
                    Util.convertToEntityAttribute(rs.getDate("date_of_birth")),
                    Util.convertToEntityAttribute(rs.getDate("date_of_death"))
            );

            return user;

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }
        finally{

            connection.close();

        }

    }


    /**
     * Called by the UserViewController, returns an ObservableList of type User containing every user in the db
     *
     * @return ObservableList<User>
     */
    public ObservableList<User> getAll() throws SQLException {

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
                                Util.convertToEntityAttribute(rs.getDate("date_of_birth")),
                                Util.convertToEntityAttribute(rs.getDate("date_of_death"))
                        )
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally{

            connection.close();

        }

        return userList;

    }


    /**
     * Called by the UserViewController
     * Inserts new user into the db
     *
     * @return void
     */
    public void create(User user) throws SQLException {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users (user_id, username, first_name, last_name, gender, date_of_birth, date_of_death) " +
                    "VALUES (0, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getGender());
            pstmt.setDate(5, Util.convertToDatabaseColumn(user.getDateOfBirth()));
            pstmt.setDate(6, Util.convertToDatabaseColumn(user.getDateOfDeath()));

            int rows = pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
        finally{

            connection.close();

        }

    }


    /**
     * Called by the UserViewController
     * Updates the user in the db
     *
     * @return void
     */
    public void update(User user) throws SQLException {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("UPDATE users SET username = ?, first_name = ?, last_name = ?, gender = ?, date_of_birth = ?, date_of_death = ? WHERE user_id = ?");

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getGender());
            pstmt.setDate(5, Util.convertToDatabaseColumn(user.getDateOfBirth()));
            pstmt.setDate(6, Util.convertToDatabaseColumn(user.getDateOfDeath()));
            pstmt.setInt(7, user.getId());

            int row = pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
        finally{

            connection.close();

        }

    }

    /**
     * Called by the UserViewController
     * Deletes the user from the db
     *
     * @return void
     */
    public void delete(User user) throws SQLException {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
            pstmt.setInt(1, user.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
        finally{

            connection.close();

        }

    }

}