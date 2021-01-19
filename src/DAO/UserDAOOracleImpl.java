package DAO;


import Model.DbConnector;
import Model.User;
import Util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class UserDAOOracleImpl implements UserDAO {


    public User getById(int id) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        User user = null;

        try{

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            rs = pstmt.executeQuery();

            user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("gender"),
                    Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth")),
                    Util.convertToEntityAttribute(rs.getTimestamp("date_of_death"))
            );

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }
        finally{

            try{
                if(pstmt != null)   pstmt.close();
                if(connection != null)  connection.close();
            }
            catch(SQLException e){
                throw e;
            }

        }

        return user;

    }


    /**
     * Called by the UserViewController, returns an ObservableList of type User containing every user in the db
     *
     * @return ObservableList<User>
     */
    public ObservableList<User> getAll() throws SQLException {

        ObservableList<User> userList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM users");
            rs = pstmt.executeQuery();

            while (rs.next()) {

                userList.add(
                        new User(
                                rs.getInt("user_id"),
                                rs.getString("username"),
                                rs.getString("first_name"),
                                rs.getString("last_name"),
                                rs.getString("gender"),
                                Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth")),
                                Util.convertToEntityAttribute(rs.getTimestamp("date_of_death"))
                        )
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally{

            try{
                if(pstmt != null)   pstmt.close();
                if(connection != null)  connection.close();
            }
            catch(SQLException e){
                throw e;
            }

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

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            //  Establish connection to database
            connection = new DbConnector().getConnection();
            //  Set the query
            pstmt = connection.prepareStatement("INSERT INTO users (user_id, username, first_name, last_name, gender, date_of_birth, date_of_death) " +
                    "VALUES (0, ?, ?, ?, ?, ?, ?)");
            //  Set parameters
            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getGender());
            pstmt.setTimestamp(5, Util.convertToDatabaseColumn(user.getDateOfBirth()));
            pstmt.setTimestamp(6, Util.convertToDatabaseColumn(user.getDateOfDeath()));
            //  Execute update
            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
        finally{

            try{
                if(pstmt != null)   pstmt.close();
                if(connection != null)  connection.close();
            }
            catch(SQLException e){
                throw e;
            }

        }

    }


    /**
     * Called by the UserViewController
     * Updates the user in the db
     *
     * @return void
     */
    public void update(User user) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("UPDATE users SET username = ?, first_name = ?, last_name = ?, gender = ?, date_of_birth = ?, date_of_death = ? WHERE user_id = ?");

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getGender());
            pstmt.setTimestamp(5, Util.convertToDatabaseColumn(user.getDateOfBirth()));
            pstmt.setTimestamp(6, Util.convertToDatabaseColumn(user.getDateOfDeath()));
            pstmt.setInt(7, user.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
        finally{

            try{
                if(pstmt != null)   pstmt.close();
                if(connection != null)  connection.close();
            }
            catch(SQLException e){
                throw e;
            }

        }

    }


    /**
     * Called by the UserViewController
     * Deletes the user from the db
     *
     * @return void
     */
    public void delete(User user) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("DELETE FROM users WHERE user_id = ?");
            pstmt.setInt(1, user.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
        finally{

            try{
                if(pstmt != null)   pstmt.close();
                if(connection != null)  connection.close();
            }
            catch(SQLException e){
                throw e;
            }

        }

    }

}