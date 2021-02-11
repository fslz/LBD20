package DAO;

import Model.*;
import Util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RelationshipDAOOracleImpl implements DAO<Relationship> {


    @Override
    public Relationship getById(int id) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Relationship> getAll() throws SQLException {
        return null;
    }

    @Override
    public void create(Relationship relationship) throws SQLException {


        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            //  Establish connection to database
            connection = new DbConnector().getConnection();
            //  Set the query
            pstmt = connection.prepareStatement("INSERT INTO relationships_all_v (relationship_id, user_id1, user_id2, type)" +
                    "VALUES (0, ?, ?, ?)");
            //  Set parameters
            pstmt.setInt(1, relationship.getUser1().getId());
            pstmt.setInt(2, relationship.getUser2().getId());
            pstmt.setString(3, relationship.getType());

            //  Execute update (Insert into contacts)
            pstmt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Error code: " + e.getErrorCode() + " SQL State: " + e.getSQLState());
            throw e;

        }
        finally {

            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw e;
            }

        }

    }


    @Override
    public void update(Relationship relationship) throws SQLException {

    }


    @Override
    public void delete(Relationship relationship) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("DELETE FROM relationship_all_v WHERE relationship_id = ?");
            pstmt.setInt(1, relationship.getId());

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

    public List<Relationship> getAllByUserId(User user) throws SQLException {

        ObservableList<Relationship> relationshipList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM relationships_all_v2 WHERE user_id1 = ?");
            pstmt.setInt(1, user.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                relationshipList.add(

                        new Relationship(

                                // Get relationship id
                                rs.getInt("relationship_id"),

                                // Get user 1
                                user,

                                // Get user 2
                                new User(rs.getInt("user_id2"),
                                        rs.getString("username2"),
                                        rs.getString("firstname2"),
                                        rs.getString("last_name2"),
                                        rs.getString("gender2"),
                                        Util.convertToEntityAttribute(rs.getDate("date_of_birth2")),
                                        Util.convertToEntityAttribute(rs.getDate("date_of_death2"))
                                ),

                                // Get relationship type
                                rs.getString("relationship_type")

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

        return relationshipList;
    }

}
