package bo.custom.impl;

import bo.custom.OrderBO;
import dao.DAOFactory;
import dao.custom.impl.orderDAOImpl;
import dto.OrderDTO;
import entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderBOImpl implements OrderBO {
    orderDAOImpl orderDAO = (orderDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public boolean addOrder(OrderDTO orderDTO, Connection connection)throws SQLException, ClassNotFoundException{
        return orderDAO.add(new Order(orderDTO.getOrderId(),orderDTO.getoDate(),orderDTO.getCustomerId(),orderDTO.getTotal()),connection);
    }

    @Override
    public OrderDTO getOrder(String oID, Connection connection) throws SQLException, ClassNotFoundException{
        Order order = orderDAO.search(oID, connection);
        return new OrderDTO(order.getOrderId(),order.getoDate(),order.getCustomerId(),order.getTotal());
    }

    @Override
    public String genarateOrderID(Connection connection) throws SQLException, ClassNotFoundException{
        return orderDAO.genarateOrderID(connection);
    }

    @Override
    public ArrayList<OrderDTO> getAllOrders(Connection connection) throws SQLException, ClassNotFoundException{
        ArrayList<Order> allOrders = orderDAO.getAll(connection);
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

    @Override
    public boolean deleteOrderId(String id,Connection connection)throws SQLException, ClassNotFoundException{
        if (orderDAO.delete(id, connection)) {
            return true;
        }else {
            return false;
        }
    }

}
