package controllers;

import bo.BoFactory;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.OrderBOImpl;
import bo.custom.impl.OrderDetailsBOImpl;
import dto.OrderDTO;
import dto.OrderDetailsDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/orders")
public class orderServlet extends HttpServlet {

    OrderBOImpl orderBO = (OrderBOImpl) BoFactory.getBoFactory().getBo(BoFactory.BoTypes.Order);
    OrderDetailsBOImpl detailsBO = (OrderDetailsBOImpl) BoFactory.getBoFactory().getBo(BoFactory.BoTypes.OrderDetails);
    ItemBOImpl itemBO = (ItemBOImpl) BoFactory.getBoFactory().getBo(BoFactory.BoTypes.Item);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        try {
            String option = req.getParameter("option");
            resp.setContentType("application/json");
            Connection connection = bds.getConnection();

            switch (option) {
                case "SEARCH":
                    String orderID = req.getParameter("orderID");
                    JsonObjectBuilder orderObject = Json.createObjectBuilder();

                    OrderDTO order = orderBO.getOrder(orderID, connection);

                    orderObject.add("orderId", order.getOrderId());
                    orderObject.add("orderDate", String.valueOf(order.getoDate()));
                    orderObject.add("customerId", order.getCustomerId());
                    orderObject.add("totalPrice", order.getTotal());
                    PrintWriter writer1 = resp.getWriter();
                    writer1.print(orderObject.build());
                    connection.close();
                    break;

                case "GETALL":
                    ArrayList<OrderDTO> allOrders = orderBO.getAllOrders(connection);
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    for (int i = 0; i < allOrders.size(); i++) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("orderId", allOrders.get(i).getOrderId());
                        objectBuilder.add("orderDate", String.valueOf(allOrders.get(i).getoDate()));
                        objectBuilder.add("customerId", allOrders.get(i).getCustomerId());
                        objectBuilder.add("totalPrice", allOrders.get(i).getTotal());

                        arrayBuilder.add(objectBuilder.build());
                    }
                    PrintWriter writer = resp.getWriter();
                    writer.print(arrayBuilder.build());
                    connection.close();
                    break;

                case "getOrderID":
                    String genOId = orderBO.genarateOrderID(connection);
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    response.add("id", genOId);
                    PrintWriter reswriter = resp.getWriter();
                    reswriter.print(response.build());
                    connection.close();
                    break;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        /*json object*/
        JsonReader reader = Json.createReader(req.getReader());
        resp.setContentType("application/json");
        JsonObject jsonObject = reader.readObject();
        String orderId = jsonObject.getString("orderId");
        Date orderDate = Date.valueOf(jsonObject.getString("orderDate"));
        String customerId = jsonObject.getString("customerId");
        int totalPrice = jsonObject.getInt("totalPrice");
        JsonArray cart = jsonObject.getJsonArray("cart");

        PrintWriter writer = resp.getWriter();

        Connection connection = null;
        try {
            connection = bds.getConnection();
            connection.setAutoCommit(false);

            /*save order details*/
            if (orderBO.addOrder(new OrderDTO(orderId,orderDate,customerId,totalPrice),connection)) {

                ArrayList<OrderDetailsDTO> detailsDTOS = new ArrayList<>();

                for (int i = 0; i < cart.size(); i++) {
                    JsonObject obj = cart.getJsonObject(i);
                    String itemCode = obj.getString("itemCode");
                    String itemName = obj.getString("itemName");
                    double unitPrice = obj.getInt("unitPrice");
                    int qty = obj.getInt("qty");
                    double total = obj.getInt("total");

                    OrderDetailsDTO detailsDTO = new OrderDetailsDTO(orderId,itemCode,itemName,unitPrice,qty,total);
                    detailsDTOS.add(detailsDTO);

                }

                if (detailsBO.addOrderDetails(detailsDTOS,connection)) {

                    for (int i = 0; i < detailsDTOS.size(); i++) {
                        itemBO.updateItemQty(detailsDTOS.get(i).getItemCode(),detailsDTOS.get(i).getQty(),connection);
                    }
                    connection.commit();
                    JsonObjectBuilder response = Json.createObjectBuilder();
                    resp.setStatus(HttpServletResponse.SC_CREATED);//201
                    response.add("status", 200);
                    response.add("message", "Successfully Added");
                    response.add("data", "");
                    writer.print(response.build());


                }

            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        String orderId = req.getParameter("orderId");
        PrintWriter writer = resp.getWriter();
        try {
            Connection connection = bds.getConnection();
            if (orderBO.deleteOrderId(orderId,connection)) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Deleted");
                writer.print(objectBuilder.build());
            }
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
