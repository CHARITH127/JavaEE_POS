package servlets;

import bo.CustomerBOImpl;
import dto.CustomerDTO;
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

@WebServlet(urlPatterns = "/customer")
public class CustomerServlet extends HttpServlet {
    CustomerBOImpl customerBO = new CustomerBOImpl();
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
                    JsonObjectBuilder cutomerObject = Json.createObjectBuilder();
                    

                    CustomerDTO customer = customerBO.getCustomer(custID, connection);
                    cutomerObject.add("id", customer.getCustomerID());
                    cutomerObject.add("name", customer.getCustomerName());
                    cutomerObject.add("address", customer.getCustomerAddress());
                    cutomerObject.add("salary", customer.getCustomerSalary());

                    PrintWriter writer1 = resp.getWriter();
                    writer1.print(cutomerObject.build());
                    connection.close();
                    break;

                case "GETALL":
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
                    for (int i = 0; i < allCustomers.size(); i++) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("id", allCustomers.get(i).getCustomerID());
                        objectBuilder.add("name", allCustomers.get(i).getCustomerName());
                        objectBuilder.add("address", allCustomers.get(i).getCustomerAddress());
                        objectBuilder.add("salary", allCustomers.get(i).getCustomerSalary());

                        arrayBuilder.add(objectBuilder.build());
                    }

                    PrintWriter writer = resp.getWriter();
                    writer.print(arrayBuilder.build());
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

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String custId = jsonObject.getString("id");
        String custName = jsonObject.getString("name");
        String custAddress = jsonObject.getString("address");
        double custsalary = Double.parseDouble(jsonObject.getString("salary"));
        CustomerDTO customerDTO = new CustomerDTO(custId,custName,custAddress,custsalary);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Connection connection = bds.getConnection();

            if (customerBO.addCustomer(customerDTO,connection)) {
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            if (customerBO.deleteCustomer(id,connection)) {
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
            if (customerBO.updateCustomer(new CustomerDTO(custId,custName,custAddress,custSalary),connection)) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("message", "Successfully Updated");
                objectBuilder.add("data", "");
                writer.print(objectBuilder.build());
            } else {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 400);
                objectBuilder.add("message", "Update Failed");
                objectBuilder.add("data", "");
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
