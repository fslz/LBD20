package DAO;

import Model.Location;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface LocationDAO extends DAO<Location>{

    Location getById(int id) throws SQLException;

    ObservableList<Location> getAll() throws SQLException;

    void create(Location location) throws SQLException;

    void update(Location location) throws SQLException;

    void delete(Location location) throws SQLException;

}
