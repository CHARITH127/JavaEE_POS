package dao;

import dto.ItemDTO;
import entity.Customer;
import entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl {
    public boolean addItem(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = connection.prepareStatement("Insert into Item values(?,?,?,?)");
        stm.setObject(1, item.getItemCode());
        stm.setObject(2, item.getItemName());
        stm.setObject(3, item.getQtyOnHand());
        stm.setObject(4, item.getUnitPrice());

        if (stm.executeUpdate() > 0) {
            connection.close();
            return true;
        }
        return false;
    }

    public ArrayList<Item> getAllItems(Connection connection)throws SQLException, ClassNotFoundException{
        ResultSet rst = connection.prepareStatement("select * from Item").executeQuery();
        ArrayList<Item> items = new ArrayList<>();
        while (rst.next()) {
            Item item = new Item();
            item.setItemCode(rst.getString(1));
            item.setItemName(rst.getString(2));
            item.setQtyOnHand(rst.getInt(3));
            item.setUnitPrice(rst.getDouble(4));

            items.add(item);

        }
        return items;
    }

    public Item getItem(String Icode, Connection connection) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = connection.prepareStatement("select * from Item where itemCode=?");
        stm.setObject(1, Icode);
        ResultSet rest = stm.executeQuery();
        Item item = new Item();
        while (rest.next()) {
            item.setItemCode(rest.getString(1));
            item.setItemName(rest.getString(2));
            item.setQtyOnHand(rest.getInt(3));
            item.setUnitPrice(rest.getDouble(4));
        }
        return item;
    }

    public boolean updateItem(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = connection.prepareStatement("Update Item set itemrName=?,itemQuantity=?,itemPrice=? where itemCode=?");
        stm.setObject(1, item.getItemName());
        stm.setObject(2, item.getQtyOnHand());
        stm.setObject(3, item.getUnitPrice());
        stm.setObject(4, item.getItemCode());

        if (stm.executeUpdate() > 0) {
            return true;
        }

        return false;
    }

    public boolean deleteItem(String id, Connection connection) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = connection.prepareStatement("Delete from Item where itemCode=?");
        stm.setObject(1, id);
        if (stm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }
}
