package bo.custom;

import bo.SuperBO;
import dto.OrderDetailsDTO;
import entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailsBO extends SuperBO {
    public boolean addOrderDetails(ArrayList<OrderDetailsDTO> cart, Connection connection)throws SQLException, ClassNotFoundException;

    public ArrayList<OrderDetailsDTO> getOrderDetails(String oid,Connection connection)throws SQLException, ClassNotFoundException;
}
