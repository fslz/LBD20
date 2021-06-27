package DAO;

import Model.DbConnector;
import Model.SerologicalTest;
import Model.User;
import Util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SerologicalTestDAOOracleImpl implements DAO<SerologicalTest> {


    /*
    DAO<User> userDAO;

    public SerologicalTestDAOOracleImpl(DAO<User> userDAO){
        this.userDAO = userDAO;
    }

    */
    @Override
    public SerologicalTest getById(int id) throws SQLException {

        /*
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SerologicalTest serologicalTest = null;

        try{

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM serological_tests WHERE serological_test_id = ?");
            // TODO setString
            rs = pstmt.executeQuery();

            serologicalTest = new SerologicalTest(
                    rs.getInt("serological_test_id"),
                    // calling the UserDAO in order to fetch user data.
                    userDAO.getById(rs.getInt("user_id")),
                    Util.convertToEntityAttribute(rs.getDate("date_result")),
                    rs.getString("igm"),
                    rs.getString("igg")
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

        return serologicalTest;
        */
        return null;

    }

    @Override
    public ObservableList<SerologicalTest> getAll() throws SQLException {
        return null;
    }

    @Override
    public void create(SerologicalTest serologicalTest) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            //  Establish connection to database
            connection = new DbConnector().getConnection();
            //  Set the query
            pstmt = connection.prepareStatement("INSERT INTO serological_tests (serological_test_id, user_id, date_result, igm, igg)" +
                    "VALUES (0, ?, ?, ?, ?)");
            //  Set parameters
            pstmt.setInt(1, serologicalTest.getUser().getId());
            pstmt.setDate(2, Util.convertToDatabaseColumn(serologicalTest.getDateResult()));
            pstmt.setString(3, serologicalTest.getIgg());
            pstmt.setString(4, serologicalTest.getIgm());
            //  Execute update (Insert into serological_tests)
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
    public void update(SerologicalTest serologicalTest) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("UPDATE SEROLOGICAL_TESTS SET USER_ID = ?, DATE_RESULT = ?, IGM = ?, IGG = ? WHERE SEROLOGICAL_TEST_ID = ?");

            pstmt.setInt(1, serologicalTest.getUser().getId());
            pstmt.setDate(2, Util.convertToDatabaseColumn(serologicalTest.getDateResult()));
            pstmt.setString(3, serologicalTest.getIgm());
            pstmt.setString(4, serologicalTest.getIgg());
            pstmt.setInt(5, serologicalTest.getId());

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
    public void delete(SerologicalTest serologicalTest) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("DELETE FROM serological_tests WHERE serological_test_id = ?");
            pstmt.setInt(1, serologicalTest.getId());

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


    public ObservableList<SerologicalTest> getAllByUserId(User user) throws SQLException {

        ObservableList<SerologicalTest> serologicalTestList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM serological_tests WHERE user_id = ?");
            pstmt.setInt(1, user.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                serologicalTestList.add(

                        new SerologicalTest(

                                rs.getInt("serological_test_id"),

                                user,

                                Util.convertToEntityAttribute(rs.getDate("date_result")),

                                rs.getString("igm"),

                                rs.getString("igg")

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

        return serologicalTestList;
    }


}
