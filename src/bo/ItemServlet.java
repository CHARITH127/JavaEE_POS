package bo;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String option = req.getParameter("option");
            resp.setContentType("application/json");
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "ijse");


            switch (option) {
                case "SEARCH":
                    String itemCode = req.getParameter("itemCode");

                    PreparedStatement stm = connection.prepareStatement("select * from Item where itemCode=?");
                    stm.setObject(1, itemCode);
                    ResultSet rest = stm.executeQuery();
                    JsonObjectBuilder itemObject = Json.createObjectBuilder();
                    while (rest.next()) {
                        String itmCode = rest.getString(1);
                        String itemName = rest.getString(2);
                        int itemQuantity = Integer.parseInt(rest.getString(3));
                        int itemPrice = Integer.parseInt(rest.getString(4));



                        itemObject.add("itemCode", itmCode);
                        itemObject.add("itemName", itemName);
                        itemObject.add("itemQty", itemQuantity);
                        itemObject.add("itemPrice", itemPrice);
                    }
                    PrintWriter writer1 = resp.getWriter();
                    writer1.print(itemObject.build());
                    break;

                case "GETALL":
                    ResultSet rst = connection.prepareStatement("select * from Item").executeQuery();

                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    while (rst.next()) {
                        String itmCode = rst.getString(1);
                        String itemName = rst.getString(2);
                        int itemQty = Integer.parseInt(rst.getString(3));
                        int itemPrice = Integer.parseInt(rst.getString(4));

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("itemCode", itmCode);
                        objectBuilder.add("itemName", itemName);
                        objectBuilder.add("itemQty", itemQty);
                        objectBuilder.add("itemPrice", itemPrice);

                        arrayBuilder.add(objectBuilder.build());

                    }
                    PrintWriter writer = resp.getWriter();
                    writer.print(arrayBuilder.build());
                    break;
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemCode = jsonObject.getString("itemCode");
        String itemName = jsonObject.getString("itemName");
        int itemQty = Integer.parseInt(jsonObject.getString("itemQty"));
        int itemPrice = Integer.parseInt(jsonObject.getString("itemPrice"));

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "ijse");
            PreparedStatement stm = connection.prepareStatement("Insert into Item values(?,?,?,?)");
            stm.setObject(1, itemCode);
            stm.setObject(2, itemName);
            stm.setObject(3, itemQty);
            stm.setObject(4, itemPrice);

            if (stm.executeUpdate() > 0) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemCode = req.getParameter("itemCode");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "ijse");
            PreparedStatement stm = connection.prepareStatement("Delete from Item where itemCode=?");
            stm.setObject(1, itemCode);

            if (stm.executeUpdate() > 0) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("status", 200);
                objectBuilder.add("data", "");
                objectBuilder.add("message", "Successfully Deleted");
                writer.print(objectBuilder.build());
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemCode = jsonObject.getString("itemCode");
        String itemName = jsonObject.getString("itemName");
        int itemQty = Integer.parseInt(jsonObject.getString("itemQty"));
        int itemPrice = Integer.parseInt(jsonObject.getString("itemPrice"));

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "ijse");
            PreparedStatement stm = connection.prepareStatement("Update Item set itemrName=?,itemQuantity=?,itemPrice=? where itemCode=?");
            stm.setObject(1,itemName);
            stm.setObject(2,itemQty);
            stm.setObject(3,itemPrice);
            stm.setObject(4,itemCode);

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

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
