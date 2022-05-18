package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.orderDAO;
import entity.Order;

import java.sql.*;
import java.util.ArrayList;

public class orderDAOImpl implements orderDAO {
    @Override
    public boolean add(Order order, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("insert into `Order` values (?,?,?,?)", connection, order.getOrderId(), order.getoDate(), order.getCustomerId(), order.getTotal());
    }

    @Override
    public Order search(String oID, Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rest = CrudUtil.executeQuery("select * from `Order` where orderID=?", connection, oID);
        Order order = new Order();
        while (rest.next()) {
            order.setOrderId(rest.getString(1));
            order.setoDate(rest.getDate(2));
            order.setCustomerId(rest.getString(3));
            order.setTotal(rest.getDouble(4));
        }
        return order;
    }

    @Override
    public boolean update(Order order, Connection connection) {
        throw new UnsupportedOperationException("Not Supported Yet");
    }

    @Override
    public ArrayList<Order> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("select * from `Order`", connection);
        ArrayList<Order> orders = new ArrayList<>();
        while (rst.next()) {
            Order order = new Order();
            order.setOrderId(rst.getString(1));
            order.setoDate(rst.getDate(2));
            order.setCustomerId(rst.getString(3));
            order.setTotal(rst.getDouble(4));

            orders.add(order);

        }
        return orders;
    }

    @Override
    public String genarateOrderID(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rset = CrudUtil.executeQuery("select orderID from `Order` order by orderID desc limit 1", connection);
        String id;
        if (rset.next()) {
            int tempID = Integer.parseInt(rset.getString(1).split("-")[1]);
            tempID = tempID + 1;
            if (tempID < 9) {
                id = "O-00" + tempID;
                return id;
            } else if (tempID < 99) {
                id = "O-0" + tempID;
                return id;
            } else {
                id = "O-" + tempID;
                return id;
            }
        } else {
            id = "O-001";
            return id;
        }

    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("Delete from `Order` where orderID=?", connection, id);
    }

}
