package DAO;


import Model.DbConnector;
import Model.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDAOOracleImpl implements DAO<Location>{

    @Override
    public Location getById(int id) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Location location = null;

        try{

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("SELECT * FROM locations WHERE location_id = ?");
            rs = pstmt.executeQuery();

            location = new Location(
                    rs.getInt("location_id"),
                    rs.getString("name"),
                    rs.getString("city"),
                    rs.getString("category")
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

        return location;

    }

    @Override
    public ObservableList<Location> getAll() throws SQLException {

        ObservableList<Location> locationList = FXCollections.observableArrayList();

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            connection = new DbConnector().getConnection();
            pstmt = connection.prepareStatement("SELECT * FROM locations");
            rs = pstmt.executeQuery();

            while (rs.next()) {

                locationList.add(
                        new Location(
                                rs.getInt("location_id"),
                                rs.getString("name"),
                                rs.getString("city"),
                                rs.getString("category")
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

        return locationList;

    }

    @Override
    public void create(Location location) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            //  Establish connection to database
            connection = new DbConnector().getConnection();
            //  Set the query
            pstmt = connection.prepareStatement("INSERT INTO locations (location_id, name, city, category) " +
                    "VALUES (0, ?, ?, ?)");
            //  Set parameters
            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getCity());
            pstmt.setString(3, location.getCategory());
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

    @Override
    public void update(Location location) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("UPDATE locations SET name = ?, city = ?, category = ? WHERE location_id = ?");

            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getCity());
            pstmt.setString(3, location.getCategory());
            pstmt.setInt(4, location.getId());

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
    public void delete(Location location) throws SQLException {

        Connection connection = null;
        PreparedStatement pstmt = null;

        try {

            connection = new DbConnector().getConnection();

            pstmt = connection.prepareStatement("DELETE FROM locations WHERE location_id = ?");
            pstmt.setInt(1, location.getId());

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
