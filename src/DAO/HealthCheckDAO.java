package DAO;

import Model.HealthCheck;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface HealthCheckDAO extends DAO<HealthCheck>{

    HealthCheck getById(int id) throws SQLException;

    ObservableList<HealthCheck> getAll() throws SQLException;

    void create(HealthCheck healthCheck) throws SQLException;

    void update(HealthCheck healthCheck) throws SQLException;

    void delete(HealthCheck healthCheck) throws SQLException;

}
