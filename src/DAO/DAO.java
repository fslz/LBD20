package DAO;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    T getById(int id) throws SQLException;

    ObservableList<T> getAll() throws SQLException;

    void create(T t) throws SQLException;

    void update(T t) throws SQLException;

    void delete(T t) throws SQLException;

}