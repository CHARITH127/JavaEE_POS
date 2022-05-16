package servlets;

import bo.OrderDetailsBOImpl;
import dto.OrderDetailsDTO;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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

@WebServlet(urlPatterns = "/orderDetails")
public class orderDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OrderDetailsBOImpl detailsBO = new OrderDetailsBOImpl();
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        try {
            resp.setContentType("application/json");
            Connection connection = bds.getConnection();
            String oID = req.getParameter("oID");
            ArrayList<OrderDetailsDTO> orderDet = detailsBO.getOrderDetails(oID, connection);
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (int i = 0; i < orderDet.size(); i++) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("itemCode", orderDet.get(i).getItemCode());
                objectBuilder.add("itemName", orderDet.get(i).getItemName());
                objectBuilder.add("itemPrice", orderDet.get(i).getPrice());
                objectBuilder.add("itemQty", orderDet.get(i).getQty());
                objectBuilder.add("total", orderDet.get(i).getTotal());

                arrayBuilder.add(objectBuilder.build());
            }
            PrintWriter writer = resp.getWriter();
            writer.print(arrayBuilder.build());
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
