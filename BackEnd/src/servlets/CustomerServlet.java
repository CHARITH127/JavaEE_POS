package servlets;

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

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
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
                    String custID = req.getParameter("custID");

                    PreparedStatement stm = connection.prepareStatement("select * from Customer where customerID=?");
                    stm.setObject(1, custID);
                    ResultSet rest = stm.executeQuery();
                    JsonObjectBuilder cutomerObject = Json.createObjectBuilder();
                    while (rest.next()) {
                        String id = rest.getString(1);
                        String name = rest.getString(2);
                        String address = rest.getString(3);
                        double salary = rest.getDouble(4);


                        cutomerObject.add("id", id);
                        cutomerObject.add("name", name);
                        cutomerObject.add("address", address);
                        cutomerObject.add("salary", salary);
                    }
                    PrintWriter writer1 = resp.getWriter();
                    writer1.print(cutomerObject.build());
                    connection.close();
                    break;

                case "GETALL":
                    ResultSet rst = connection.prepareStatement("select * from Customer").executeQuery();

                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    while (rst.next()) {
                        String id = rst.getString(1);
                        String name = rst.getString(2);
                        String address = rst.getString(3);
                        double salary = rst.getDouble(4);

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("id", id);
                        objectBuilder.add("name", name);
                        objectBuilder.add("address", address);
                        objectBuilder.add("salary", salary);

                        arrayBuilder.add(objectBuilder.build());

                    }
                    PrintWriter writer = resp.getWriter();
                    writer.print(arrayBuilder.build());
                    connection.close();
                    break;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String custId = jsonObject.getString("id");
        String custName = jsonObject.getString("name");
        String custAddress = jsonObject.getString("address");
        String custsalary = jsonObject.getString("salary");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Connection connection = bds.getConnection();
            PreparedStatement stm = connection.prepareStatement("Insert into Customer values(?,?,?,?)");
            stm.setObject(1, custId);
            stm.setObject(2, custName);
            stm.setObject(3, custAddress);
            stm.setObject(4, custsalary);

            if (stm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }

            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        String id = req.getParameter("cutID");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = bds.getConnection();
            PreparedStatement stm = connection.prepareStatement("Delete from Customer where customerID=?");
            stm.setObject(1, id);

            if (stm.executeUpdate() > 0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Deleted");
                writer.print(objectBuilder.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String custId = jsonObject.getString("id");
        String custName = jsonObject.getString("name");
        String custAddress = jsonObject.getString("address");
        double custSalary = Double.parseDouble(jsonObject.getString("salary"));

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = bds.getConnection();
            PreparedStatement stm = connection.prepareStatement("Update Customer set customerName=?,customerAddress=?,salary=? where customerID=?");
            stm.setObject(1,custName);
            stm.setObject(2,custAddress);
            stm.setObject(3,custSalary);
            stm.setObject(4,custId);

            if (stm.executeUpdate()>0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Updated");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Update Failed");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            }
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
