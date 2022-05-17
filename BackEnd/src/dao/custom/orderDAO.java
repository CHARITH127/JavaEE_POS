package dao.custom;

import dao.CrudDAO;
import entity.Order;

import java.sql.Connection;
import java.sql.SQLException;

public interface orderDAO extends CrudDAO<Order, Connection,String> {
    public String genarateOrderID(Connection connection) throws SQLException, ClassNotFoundException;
}
