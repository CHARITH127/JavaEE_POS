package dao;

import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T,Connection,ID> extends SuperDAO {
    public boolean add(T t, Connection connection) throws SQLException, ClassNotFoundException;

    public ArrayList<T> getAll(Connection connection) throws SQLException, ClassNotFoundException;

    public T search(ID id, Connection connection) throws SQLException, ClassNotFoundException;

    public boolean update(T t, Connection connection) throws SQLException, ClassNotFoundException;

    public boolean delete(ID id, Connection connection) throws SQLException, ClassNotFoundException;

}
