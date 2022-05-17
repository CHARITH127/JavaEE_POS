package controllers;

import bo.BoFactory;
import bo.custom.impl.ItemBOImpl;
import dto.ItemDTO;
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

@WebServlet(urlPatterns = "/item")
public class ItemServlet extends HttpServlet {

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
                    String itemCode = req.getParameter("itemCode");
                    JsonObjectBuilder itemObject = Json.createObjectBuilder();
                    ItemDTO item = itemBO.getItem(itemCode, connection);
                    itemObject.add("itemCode", item.getItemCode());
                    itemObject.add("itemName", item.getItemName());
                    itemObject.add("itemQty", item.getQtyOnHand());
                    itemObject.add("itemPrice", item.getUnitPrice());
                    PrintWriter writer1 = resp.getWriter();
                    writer1.print(itemObject.build());
                    connection.close();
                    break;

                case "GETALL":
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    ArrayList<ItemDTO> allItems = itemBO.getAllItems(connection);
                    for (int i = 0; i < allItems.size(); i++) {
                        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                        objectBuilder.add("itemCode", allItems.get(i).getItemCode());
                        objectBuilder.add("itemName", allItems.get(i).getItemName());
                        objectBuilder.add("itemQty", allItems.get(i).getQtyOnHand());
                        objectBuilder.add("itemPrice", allItems.get(i).getUnitPrice());

                        arrayBuilder.add(objectBuilder.build());
                    }
                    PrintWriter writer = resp.getWriter();
                    writer.print(arrayBuilder.build());
                    connection.close();
                    break;
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*connection pool*/
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        /*json object*/
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String itemCode = jsonObject.getString("itemCode");
        String itemName = jsonObject.getString("itemName");
        int itemQty = Integer.parseInt(jsonObject.getString("itemQty"));
        double itemPrice = Double.parseDouble(jsonObject.getString("itemPrice"));

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");
        try {
            Connection connection = bds.getConnection();
            if (itemBO.addItem(new ItemDTO(itemCode,itemName,itemQty,itemPrice),connection)) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                resp.setStatus(HttpServletResponse.SC_CREATED);//201
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            }
            connection.close();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*connection pool*/
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");


        String itemCode = req.getParameter("itemCode");
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = bds.getConnection();
            if (itemBO.deleteItem(itemCode,connection)) {
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

        /*connection pool*/
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = bds.getConnection();

            JsonReader reader = Json.createReader(req.getReader());
            JsonObject jsonObject = reader.readObject();
            String itemCode = jsonObject.getString("itemCode");
            String itemName = jsonObject.getString("itemName");
            int itemQty = Integer.parseInt(jsonObject.getString("itemQty"));
            double itemPrice = Double.parseDouble(jsonObject.getString("itemPrice"));
            if (itemBO.updateItem(new ItemDTO(itemCode,itemName,itemQty,itemPrice),connection)) {
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
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
