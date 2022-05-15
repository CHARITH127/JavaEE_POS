package bo;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/orders")
public class orderServlet extends HttpServlet {
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

                case "getOrderID":
                    ResultSet rset = connection.prepareStatement("select orderID from `Order` order by orderID desc limit 1").executeQuery();
                    if (rset.next()) {
                        int tempID = Integer.parseInt(rset.getString(1).split("-")[1]);
                        tempID = tempID + 1;
                        if (tempID < 9) {
                            String id="O-00" + tempID;
                            JsonObjectBuilder response = Json.createObjectBuilder();
                            response.add("id", id);
                            System.out.println(id);
                            PrintWriter reswriter = resp.getWriter();
                            reswriter.print(response.build());
                        } else if (tempID < 99) {
                            String id= "O-0" + tempID;
                            JsonObjectBuilder response = Json.createObjectBuilder();
                            response.add("id", id);
                            PrintWriter reswriter = resp.getWriter();
                            reswriter.print(response.build());
                        } else {
                            String id="O-" + tempID;
                            JsonObjectBuilder response = Json.createObjectBuilder();
                            response.add("id", id);
                            PrintWriter reswriter = resp.getWriter();
                            reswriter.print(response.build());
                        }
                    } else {
                        String id ="O-001";
                        JsonObjectBuilder response = Json.createObjectBuilder();
                        response.add("id", id);
                        PrintWriter reswriter = resp.getWriter();
                        reswriter.print(response.build());
                    }

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
        resp.setContentType("application/json");
        JsonObject jsonObject = reader.readObject();
        String orderId = jsonObject.getString("orderId");
        String orderDate = jsonObject.getString("orderDate");
        String customerId = jsonObject.getString("customerId");
        int totalPrice = jsonObject.getInt("totalPrice");
        JsonArray cart = jsonObject.getJsonArray("cart");

        PrintWriter writer = resp.getWriter();

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "ijse");
            connection.setAutoCommit(false);
            PreparedStatement stm = connection.prepareStatement("insert into `Order` values (?,?,?,?)");
            stm.setObject(1, orderId);
            stm.setObject(2, orderDate);
            stm.setObject(3, customerId);
            stm.setObject(4, totalPrice);

            /*save order details*/
            if (stm.executeUpdate() > 0) {
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO orderDetails VALUES(?,?,?,?,?,?)");

                for (int i = 0; i < cart.size(); i++) {
                    JsonObject obj = cart.getJsonObject(i);
                    String itemCode = obj.getString("itemCode");
                    String itemName = obj.getString("itemName");
                    double unitPrice = obj.getInt("unitPrice");
                    int qty = obj.getInt("qty");
                    double total = obj.getInt("total");

                    pstm.setObject(1, orderId);
                    pstm.setObject(2, itemCode);
                    pstm.setObject(3, itemName);
                    pstm.setObject(4, unitPrice);
                    pstm.setObject(5, qty);
                    pstm.setObject(6, total);


                    if (pstm.executeUpdate() > 0) {
                        /*update item qty*/
                        PreparedStatement statement = connection.prepareStatement("UPDATE Item SET itemQuantity=(itemQuantity-" + qty + ") WHERE itemCode='" + itemCode + "'");
                        statement.executeUpdate();
                        connection.commit();
                        JsonObjectBuilder response = Json.createObjectBuilder();
                        resp.setStatus(HttpServletResponse.SC_CREATED);//201
                        response.add("status", 200);
                        response.add("message", "Successfully Added");
                        response.add("data", "");
                        writer.print(response.build());
                    }

                }

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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
