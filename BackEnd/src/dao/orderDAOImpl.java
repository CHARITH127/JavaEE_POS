package dao;

import entity.Order;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

public class orderDAOImpl {

    public boolean addOrder(Order order,Connection connection)throws SQLException, ClassNotFoundException{
        PreparedStatement stm = connection.prepareStatement("insert into `Order` values (?,?,?,?)");
        stm.setObject(1, order.getOrderId());
        stm.setObject(2, order.getoDate());
        stm.setObject(3, order.getCustomerId());
        stm.setObject(4, order.getTotal());

        if (stm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

    public Order getOrder(String oID, Connection connection) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = connection.prepareStatement("select * from `Order` where orderID=?");
        stm.setObject(1, oID);
        ResultSet rest = stm.executeQuery();

        Order order = new Order();
        while (rest.next()) {
            order.setOrderId(rest.getString(1));
            order.setoDate(rest.getDate(2));
            order.setCustomerId(rest.getString(3));
            order.setTotal(rest.getDouble(4));
        }
        return order;
    }

    public ArrayList<Order> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = connection.prepareStatement("select * from `Order`").executeQuery();

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

    public String genarateOrderID(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rset = connection.prepareStatement("select orderID from `Order` order by orderID desc limit 1").executeQuery();
        if (rset.next()) {
            int tempID = Integer.parseInt(rset.getString(1).split("-")[1]);
            tempID = tempID + 1;
            if (tempID < 9) {
                String id = "O-00" + tempID;
                return id;
            } else if (tempID < 99) {
                String id = "O-0" + tempID;
                return id;
            } else {
                String id = "O-" + tempID;
                return id;
            }
        } else {
            String id = "O-001";
            return id;
        }

    }

    public boolean deleteOrderId(String id,Connection connection)throws SQLException, ClassNotFoundException{
        PreparedStatement stm = connection.prepareStatement("Delete from `Order` where orderID=?");
        stm.setObject(1, id);
        if (stm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

}
