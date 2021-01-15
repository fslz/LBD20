package Model;

import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface ContactDAO extends DAO<Contact>{

    Contact getById(int id) throws SQLException;

    ObservableList<Contact> getAll() throws SQLException;

    void create(Contact contact) throws SQLException;

    void update(Contact contact) throws SQLException;

    void delete(Contact contact) throws SQLException;

}
