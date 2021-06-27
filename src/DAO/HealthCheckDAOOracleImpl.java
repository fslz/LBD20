package DAO;

import Model.DbConnector;
import Model.HealthCheck;
import Model.User;
import Util.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HealthCheckDAOOracleImpl implements DAO<HealthCheck> {

    @Override
    public HealthCheck getById(int id) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<HealthCheck> getAll() throws SQLException {
        return null;
    }

    @Override
    public void create(HealthCheck healthCheck) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            //  Establish connection to database
            connection = new DbConnector().getConnection();
            //  Set the query
            pstmt = connection.prepareStatement("INSERT INTO health_checks (health_check_id, user_id, date_result, fever, respiratory_disorder, smell_taste_disorder)" +
                    "VALUES (0, ?, ?, ?, ?, ?)");
            //  Set parameters
            pstmt.setInt(1, healthCheck.getUser().getId());
            pstmt.setDate(2, Util.convertToDatabaseColumn(healthCheck.getDateResult()));
            pstmt.setString(3, healthCheck.getFever());
            pstmt.setString(4, healthCheck.getRespiratoryDisorder());
            pstmt.setString(5, healthCheck.getSmellTasteDisorder());

            //  Execute update (Insert into health_checks)
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
    public void update(HealthCheck healthCheck) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("UPDATE HEALTH_CHECKS SET USER_ID = ?, DATE_RESULT = ?, fever = ?, respiratory_disorder = ?, smell_taste_disorder = ? WHERE health_check_id = ?");

            pstmt.setInt(1, healthCheck.getUser().getId());
            pstmt.setDate(2, Util.convertToDatabaseColumn(healthCheck.getDateResult()));
            pstmt.setString(3, healthCheck.getFever());
            pstmt.setString(4, healthCheck.getRespiratoryDisorder());
            pstmt.setString(5, healthCheck.getSmellTasteDisorder());
            pstmt.setInt(6, healthCheck.getId());

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
    public void delete(HealthCheck healthCheck) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("DELETE FROM health_checks WHERE health_check_id = ?");
            pstmt.setInt(1, healthCheck.getId());

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


    public ObservableList<HealthCheck> getAllByUserId(User user) throws SQLException {

        ObservableList<HealthCheck> healthChecksTestList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM health_checks WHERE user_id = ?");
            pstmt.setInt(1, user.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                healthChecksTestList.add(

                        new HealthCheck(

                                rs.getInt("health_check_id"),

                                user,

                                Util.convertToEntityAttribute(rs.getDate("date_result")),

                                rs.getString("fever"),

                                rs.getString("respiratory_disorder"),

                                rs.getString("smell_taste_disorder")

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

        return healthChecksTestList;
    }

}
