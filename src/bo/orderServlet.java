package bo;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class orderServlet extends HttpServlet {
    public String genarateOrderID() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "ijse");
            ResultSet rst = connection.prepareStatement("select orderID from `Order` order by orderID desc limit 1").executeQuery();
            if (rst.next()) {
                int tempID = Integer.parseInt(rst.getString(1).split("-")[1]);
                tempID = tempID + 1;
                if (tempID < 9) {
                    return "O-00" + tempID;
                } else if (tempID < 99) {
                    return "O-0" + tempID;
                } else {
                    return "O-" + tempID;
                }
            } else {
                return "O-001";
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

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

                    JsonObjectBuilder orderObject = Json.createObjectBuilder();
                    while (rest.next()) {
                        String orderId = rest.getString(1);
                        Date orderDate = rest.getDate(2);
                        String customerId = rest.getString(3);
                        double totalPrice = rest.getDouble(4);

                        orderObject.add("orderId", orderId);
                        orderObject.add("orderDate", String.valueOf(orderDate));
                        orderObject.add("customerId", customerId);
                        orderObject.add("totalPrice", totalPrice);
                    }
                    PrintWriter writer1 = resp.getWriter();
                    writer1.print(orderObject.build());
                    break;

                case "GETALL":
                    ResultSet rst = connection.prepareStatement("select * from `Order`").executeQuery();

                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    while (rst.next()) {
                        String orderId = rst.getString(1);
                        Date orderDate = rst.getDate(2);
                        String customerId = rst.getString(3);
                        double totalPrice = rst.getDouble(4);

                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("orderId", orderId);
                        objectBuilder.add("orderDate", String.valueOf(orderDate));
                        objectBuilder.add("customerId", customerId);
                        objectBuilder.add("totalPrice", totalPrice);

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
        resp.setContentType("application/json");
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String orderId = jsonObject.getString("orderId");
        Date orderDate = Date.valueOf(jsonObject.getString("orderDate"));
        String customerId = jsonObject.getString("customerId");
        double totalPrice = Double.parseDouble(jsonObject.getString("totalPrice"));
        PrintWriter writer = resp.getWriter();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "ijse");
            PreparedStatement stm = connection.prepareStatement("insert into `Order` values (?,?,?,?)");
            stm.setObject(1, orderId);
            stm.setObject(2, orderDate);
            stm.setObject(3, customerId);
            stm.setObject(4, totalPrice);

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
        String orderId = req.getParameter("orderId");
        PrintWriter writer = resp.getWriter();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "ijse");
            PreparedStatement stm = connection.prepareStatement("Delete from `Order` where orderID=?");
            stm.setObject(1, orderId);

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
}
