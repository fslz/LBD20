package DAO;

import Model.User;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface UserDAO extends DAO<User>{

    User getById(int id) throws SQLException;

    ObservableList<User> getAll() throws SQLException;

    void create(User user) throws SQLException;

    void update(User user) throws SQLException;

    void delete(User user) throws SQLException;

}
