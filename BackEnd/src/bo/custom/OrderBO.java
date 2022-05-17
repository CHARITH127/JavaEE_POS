package bo.custom;

import bo.SuperBO;
import dto.OrderDTO;
import entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderBO extends SuperBO {
    public boolean addOrder(OrderDTO orderDTO, Connection connection)throws SQLException, ClassNotFoundException;

    public OrderDTO getOrder(String oID, Connection connection) throws SQLException, ClassNotFoundException;

    public String genarateOrderID(Connection connection) throws SQLException, ClassNotFoundException;

    public ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException;

    public boolean deleteOrderId(String id,Connection connection)throws SQLException, ClassNotFoundException;
}
