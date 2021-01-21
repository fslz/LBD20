package DAO;

import Model.*;
import Util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactDAOOracleImpl implements ContactDAO {

    @Override
    public Contact getById(int id) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Contact contact = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM contacts_all_v2 WHERE contact_id = ?");
            rs = pstmt.executeQuery();

            contact = new Contact(rs.getInt("contact_id"),
                    new User(rs.getInt("user_id1"),
                    rs.getString("username1"),
                    rs.getString("first_name1"),
                    rs.getString("last_name1"),
                    rs.getString("gender1"),
                    Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth1")),
                    Util.convertToEntityAttribute(rs.getTimestamp("date_of_death1"))
                    ),

                    new User(rs.getInt("user_id2"),
                            rs.getString("username2"),
                            rs.getString("firstname2"),
                            rs.getString("last_name2"),
                            rs.getString("gender2"),
                            Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth2")),
                            Util.convertToEntityAttribute(rs.getTimestamp("date_of_death2"))
                    ),

                    new Location(rs.getInt("location_id"),
                            rs.getString("location_name"),
                            rs.getString("location_city"),
                            rs.getString("location_category")
                    ),

                    Util.convertToEntityAttribute(rs.getTimestamp("date_received"))
            );


        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        } finally {

            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw e;
            }

        }

        return contact;
    }

    @Override
    public ObservableList<Contact> getAll() throws SQLException {

        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM contacts_all_v2");
            rs = pstmt.executeQuery();

            while (rs.next()) {

                contactList.add(
                        new Contact(rs.getInt("contact_id"),
                                new User(rs.getInt("user_id1"),
                                rs.getString("username1"),
                                rs.getString("first_name1"),
                                rs.getString("last_name1"),
                                rs.getString("gender1"),
                                Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth1")),
                                Util.convertToEntityAttribute(rs.getTimestamp("date_of_death1"))
                        ),

                                new User(rs.getInt("user_id2"),
                                        rs.getString("username2"),
                                        rs.getString("firstname2"),
                                        rs.getString("last_name2"),
                                        rs.getString("gender2"),
                                        Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth2")),
                                        Util.convertToEntityAttribute(rs.getTimestamp("date_of_death2"))
                                ),

                                new Location(rs.getInt("location_id"),
                                        rs.getString("location_name"),
                                        rs.getString("location_city"),
                                        rs.getString("location_category")
                                ),

                                Util.convertToEntityAttribute(rs.getTimestamp("date_received"))
                        )
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw e;
            }

        }

        return contactList;
    }

    @Override
    public void create(Contact contact) throws SQLException {


        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            //  Establish connection to database
            connection = new DbConnector().getConnection();
            //  Set the query
            pstmt = connection.prepareStatement("INSERT INTO contact_all_v (user_id1, user_id2, location_id, date_received)" +
                    "VALUES (0, ?, ?, ?, ?)");
            //  Set parameters
            pstmt.setInt(1, contact.getUser1().getId());
            pstmt.setInt(2, contact.getUser1().getId());
            pstmt.setInt(3, contact.getLocation().getId());
            pstmt.setTimestamp(4, Util.convertToDatabaseColumn(contact.getDateReceived()));

            //  Execute update
            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        } finally {

            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw e;
            }

        }


    }

    @Override
    public void update(Contact contact) throws SQLException {

        /*
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("UPDATE contacts_all_v SET username = ?, first_name = ?, last_name = ?, gender = ?, date_of_birth = ?, date_of_death = ? WHERE user_id = ?");

            pstmt.setString(1, user.getUserName());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getGender());
            pstmt.setDate(5, Util.convertToDatabaseColumn(user.getDateOfBirth()));
            pstmt.setDate(6, Util.convertToDatabaseColumn(user.getDateOfDeath()));
            pstmt.setInt(7, user.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        } finally {

            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw e;
            }

        }
        */

    }

    @Override
    public void delete(Contact contact) throws SQLException {


        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("DELETE FROM contact_all_v WHERE user_id1 = ? OR user_id2 = ?");
            pstmt.setInt(1, contact.getUser1().getId());
            pstmt.setInt(2, contact.getUser2().getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        } finally {

            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw e;
            }

        }

    }


    public ObservableList<Contact> getAllByUserId(User user) throws SQLException{

        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM contacts_all_v2 WHERE user_id1 = ?");
            pstmt.setInt(1, user.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                contactList.add(
                        new Contact(rs.getInt("contact_id"),
                                new User(rs.getInt("user_id1"),
                                rs.getString("username1"),
                                rs.getString("first_name1"),
                                rs.getString("last_name1"),
                                rs.getString("gender1"),
                                Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth1")),
                                Util.convertToEntityAttribute(rs.getTimestamp("date_of_death1"))
                        ),

                                new User(rs.getInt("user_id2"),
                                        rs.getString("username2"),
                                        rs.getString("firstname2"),
                                        rs.getString("last_name2"),
                                        rs.getString("gender2"),
                                        Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth2")),
                                        Util.convertToEntityAttribute(rs.getTimestamp("date_of_death2"))
                                ),

                                new Location(rs.getInt("location_id"),
                                        rs.getString("location_name"),
                                        rs.getString("location_city"),
                                        rs.getString("location_category")
                                ),

                                Util.convertToEntityAttribute(rs.getTimestamp("date_received"))
                        )
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw e;
            }

        }

        return contactList;
    }


    public ObservableList<Contact> getAllBylocationId(Location location) throws SQLException{

        ObservableList<Contact> contactList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM contacts_all_v2 WHERE location_id = ?");
            pstmt.setInt(1, location.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                contactList.add(
                        new Contact(rs.getInt("contact_id"),
                                new User(rs.getInt("user_id1"),
                                rs.getString("username1"),
                                rs.getString("first_name1"),
                                rs.getString("last_name1"),
                                rs.getString("gender1"),
                                Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth1")),
                                Util.convertToEntityAttribute(rs.getTimestamp("date_of_death1"))
                        ),

                                new User(rs.getInt("user_id2"),
                                        rs.getString("username2"),
                                        rs.getString("firstname2"),
                                        rs.getString("last_name2"),
                                        rs.getString("gender2"),
                                        Util.convertToEntityAttribute(rs.getTimestamp("date_of_birth2")),
                                        Util.convertToEntityAttribute(rs.getTimestamp("date_of_death2"))
                                ),

                                new Location(rs.getInt("location_id"),
                                        rs.getString("location_name"),
                                        rs.getString("location_city"),
                                        rs.getString("location_category")
                                ),

                                Util.convertToEntityAttribute(rs.getTimestamp("date_received"))
                        )
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw e;
            }

        }

        return contactList;
    }

}

