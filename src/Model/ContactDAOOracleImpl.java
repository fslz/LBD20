package Model;

import javafx.collections.ObservableList;
import java.sql.SQLException;

public class ContactDAOOracleImpl implements ContactDAO{

    @Override
    public Contact getById(int id) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Contact> getAll() throws SQLException {
        return null;
    }

    @Override
    public void create(Contact contact) throws SQLException {

    }

    @Override
    public void update(Contact contact) throws SQLException {

    }

    @Override
    public void delete(Contact contact) throws SQLException {

    }
}
