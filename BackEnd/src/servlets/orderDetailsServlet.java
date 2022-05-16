package servlets;

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

@WebServlet(urlPatterns = "/orderDetails")
public class orderDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        try {
            resp.setContentType("application/json");
            Connection connection = bds.getConnection();
            String oID = req.getParameter("oID");
            PreparedStatement stm = connection.prepareStatement("select * from orderDetails where orderID=?");
            stm.setObject(1,oID);
            ResultSet set = stm.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (set.next()) {
                String itmCode = set.getString(2);
                String itmName = set.getString(3);
                double itmPrice = set.getDouble(4);
                int itmQty = set.getInt(5);
                double total = set.getDouble(6);

                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("itemCode", itmCode);
                objectBuilder.add("itemName", itmName);
                objectBuilder.add("itemPrice", itmPrice);
                objectBuilder.add("itemQty", itmQty);
                objectBuilder.add("total", total);

                arrayBuilder.add(objectBuilder.build());
            }
            PrintWriter writer = resp.getWriter();
            writer.print(arrayBuilder.build());
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
