package DAO;

import Model.SerologicalTest;
import javafx.collections.ObservableList;
import java.sql.SQLException;

public interface SerologicalTestDAO extends DAO<SerologicalTest>{

    SerologicalTest getById(int id) throws SQLException;

    ObservableList<SerologicalTest> getAll() throws SQLException;

    void create(SerologicalTest serologicalTest) throws SQLException;

    void update(SerologicalTest serologicalTest) throws SQLException;

    void delete(SerologicalTest serologicalTest) throws SQLException;

}
