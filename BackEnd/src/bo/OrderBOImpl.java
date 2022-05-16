package bo;

import dao.orderDAOImpl;
import dto.OrderDTO;
import entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl {
    orderDAOImpl orderDAO = new orderDAOImpl();

    public boolean addOrder(OrderDTO orderDTO, Connection connection)throws SQLException, ClassNotFoundException{
        return orderDAO.addOrder(new Order(orderDTO.getOrderId(),orderDTO.getoDate(),orderDTO.getCustomerId(),orderDTO.getTotal()),connection);
    }

    public OrderDTO getOrder(String oID, Connection connection) throws SQLException, ClassNotFoundException{
        Order order = orderDAO.getOrder(oID, connection);
        return new OrderDTO(order.getOrderId(),order.getoDate(),order.getCustomerId(),order.getTotal());
    }

    public String genarateOrderID(Connection connection) throws SQLException, ClassNotFoundException{
        return orderDAO.genarateOrderID(connection);
    }

    public ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException{
        ArrayList<Order> allOrders = orderDAO.getAllOrders(connection);
        ArrayList<OrderDTO> orderDTOS = new ArrayList<>();
        for (int i = 0; i < allOrders.size(); i++) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(allOrders.get(i).getOrderId());
            orderDTO.setoDate(allOrders.get(i).getoDate());
            orderDTO.setCustomerId(allOrders.get(i).getCustomerId());
            orderDTO.setTotal(allOrders.get(i).getTotal());
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    public boolean deleteOrderId(String id,Connection connection)throws SQLException, ClassNotFoundException{
        if (orderDAO.deleteOrderId(id, connection)) {
            return true;
        }else {
            return false;
        }
    }

}
