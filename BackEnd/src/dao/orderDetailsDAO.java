package dao;

import dto.OrderDTO;
import dto.OrderDetailsDTO;
import entity.OrderDetails;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class orderDetailsDAO {

    public boolean addOrderDetails(ArrayList<OrderDetails> cart, Connection connection)throws SQLException, ClassNotFoundException{
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderDetails VALUES(?,?,?,?,?,?)");

        for (int i = 0; i < cart.size(); i++) {
            pstm.setObject(1, cart.get(i).getOrderId());
            pstm.setObject(2, cart.get(i).getItemCode());
            pstm.setObject(3, cart.get(i).getItemName());
            pstm.setObject(4, cart.get(i).getPrice());
            pstm.setObject(5, cart.get(i).getQty());
            pstm.setObject(6, cart.get(i).getTotal());

            if (pstm.executeUpdate() > 0) {

            }else {
                return false;
            }

        }

        return true;

    }

    public ArrayList<OrderDetails> getOrderDetails(String oid,Connection connection)throws SQLException, ClassNotFoundException{
        PreparedStatement stm = connection.prepareStatement("select * from orderDetails where orderID=?");
        stm.setObject(1,oid);
        ResultSet set = stm.executeQuery();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        while (set.next()) {
            OrderDetails orderDe = new OrderDetails();

            orderDe.setOrderId(set.getString(1));
            orderDe.setItemCode(set.getString(2));
            orderDe.setItemName(set.getString(3));
            orderDe.setPrice(set.getDouble(4));
            orderDe.setQty(set.getInt(5));
            orderDe.setTotal(set.getDouble(6));

            orderDetails.add(orderDe);
        }

        return orderDetails;
    }
}
