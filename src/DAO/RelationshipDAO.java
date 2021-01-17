package DAO;

import Model.Relationship;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface RelationshipDAO extends DAO<Relationship>{

    Relationship getById(int id) throws SQLException;

    ObservableList<Relationship> getAll() throws SQLException;

    void create(Relationship relationship) throws SQLException;

    void update(Relationship relationship) throws SQLException;

    void delete(Relationship relationship) throws SQLException;

}
