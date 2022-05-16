package bo;

import dao.orderDetailsDAO;
import dto.OrderDetailsDTO;
import entity.OrderDetails;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailsBOImpl {
    orderDetailsDAO detailsDAO = new orderDetailsDAO();
    public boolean addOrderDetails(ArrayList<OrderDetailsDTO> cart, Connection connection)throws SQLException, ClassNotFoundException{
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();

        for (int i = 0; i < cart.size(); i++) {
            OrderDetails orderDe = new OrderDetails();
            orderDe.setOrderId(cart.get(i).getOrderId());
            orderDe.setItemCode(cart.get(i).getItemCode());
            orderDe.setItemName(cart.get(i).getItemName());
            orderDe.setPrice(cart.get(i).getPrice());
            orderDe.setQty(cart.get(i).getQty());
            orderDe.setTotal(cart.get(i).getTotal());
            orderDetails.add(orderDe);
        }
        return detailsDAO.addOrderDetails(orderDetails,connection);
    }

    public ArrayList<OrderDetailsDTO> getOrderDetails(String oid,Connection connection)throws SQLException, ClassNotFoundException{
        ArrayList<OrderDetails> details = detailsDAO.getOrderDetails(oid, connection);
        ArrayList<OrderDetailsDTO> detailsDTOS = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            OrderDetailsDTO orderDto = new OrderDetailsDTO();
            orderDto.setOrderId(details.get(i).getOrderId());
            orderDto.setItemCode(details.get(i).getItemCode());
            orderDto.setItemName(details.get(i).getItemName());
            orderDto.setPrice(details.get(i).getPrice());
            orderDto.setQty(details.get(i).getQty());
            orderDto.setTotal(details.get(i).getTotal());
            detailsDTOS.add(orderDto);
        }
        return detailsDTOS;
    }
}
