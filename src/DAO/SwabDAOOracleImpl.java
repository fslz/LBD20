package DAO;

import Model.*;
import Util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SwabDAOOracleImpl implements DAO<Swab>{

    @Override
    public Swab getById(int id) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Swab> getAll() throws SQLException {
        return null;
    }

    @Override
    public void create(Swab swab) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            //  Establish connection to database
            connection = new DbConnector().getConnection();
            //  Set the query
            pstmt = connection.prepareStatement("INSERT INTO swabs (swab_id, user_id, date_result, positivity)" +
                    "VALUES (0, ?, ?, ?)");
            //  Set parameters
            pstmt.setInt(1, swab.getUser().getId());
            pstmt.setDate(2, Util.convertToDatabaseColumn(swab.getDateResult()));
            pstmt.setString(3, swab.getPositivity());
            //  Execute update (Insert into swabs)
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
    public void update(Swab swab) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("UPDATE swabs SET date_result = ?, positivity = ? WHERE swab_id = ?");

            pstmt.setDate(1, Util.convertToDatabaseColumn(swab.getDateResult()));
            pstmt.setString(2, swab.getPositivity());
            pstmt.setInt(3, swab.getId());

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


    @Override
    public void delete(Swab swab) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("DELETE FROM swabs WHERE swab_id = ?");
            pstmt.setInt(1, swab.getId());

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

    public ObservableList<Swab> getAllByUserId(User user) throws SQLException {

        ObservableList<Swab> swabList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM swabs WHERE user_id = ?");
            pstmt.setInt(1, user.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                swabList.add(

                        new Swab(

                                rs.getInt("swab_id"),

                                user,

                                Util.convertToEntityAttribute(rs.getDate("date_result")),

                                rs.getString("positivity")
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

        return swabList;
    }


}
