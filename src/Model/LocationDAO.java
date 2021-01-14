package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationDAO implements DAO<Location>{

    @Override
    public Location getById(int id) throws SQLException {

        Connection connection = new DbConnector().getConnection();
        Location Location;

        try{

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM locations WHERE location_id = ?");
            ResultSet rs = pstmt.executeQuery();

            Location = new Location(
                    rs.getInt("location_id"),
                    rs.getString("name"),
                    rs.getString("city"),
                    rs.getString("category")
            );

            return Location;

        } catch (SQLException e) {

            e.printStackTrace();
            return null;

        }
        finally{

            connection.close();

        }

    }

    @Override
    public ObservableList<Location> getAll() throws SQLException {

        ObservableList<Location> locationList = FXCollections.observableArrayList();

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM locations");
            ResultSet rs = pstmt.executeQuery();

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

            connection.close();

        }

        return locationList;

    }

    @Override
    public void create(Location location) throws SQLException {

        //  Establish connection to database
        Connection connection = new DbConnector().getConnection();

        try {
            //  Set the query
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO locations (location_id, name, city, category) " +
                    "VALUES (0, ?, ?, ?)");
            //  Set parameters
            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getCity());
            pstmt.setString(3, location.getCategory());
            //  Execute query
            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
        finally{

            connection.close();

        }

    }

    @Override
    public void update(Location location) throws SQLException {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("UPDATE locations SET name = ?, city = ?, category = ? WHERE location_id = ?");

            pstmt.setString(1, location.getName());
            pstmt.setString(2, location.getCity());
            pstmt.setString(3, location.getCategory());
            pstmt.setInt(4, location.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
        finally{

            connection.close();

        }
        
    }

    @Override
    public void delete(Location location) throws SQLException {

        Connection connection = new DbConnector().getConnection();

        try {

            PreparedStatement pstmt = connection.prepareStatement("DELETE FROM locations WHERE location_id = ?");
            pstmt.setInt(1, location.getId());

            pstmt.executeUpdate();

        } catch (SQLException e) {

            throw e;

        }
        finally{

            connection.close();

        }
        
    }
}
