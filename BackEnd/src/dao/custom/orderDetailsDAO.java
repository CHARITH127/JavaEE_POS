package dao.custom;

import dao.CrudDAO;
import entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface orderDetailsDAO extends CrudDAO<OrderDetails, Connection,String> {
    public ArrayList<OrderDetails> getOrderDetails(String oid, Connection connection)throws SQLException, ClassNotFoundException;
    public boolean add(ArrayList<OrderDetails> cart, Connection connection)throws SQLException, ClassNotFoundException;
}
