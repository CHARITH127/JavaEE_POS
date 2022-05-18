package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.ItemDAO;
import entity.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public boolean add(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("Insert into Item values(?,?,?,?)", connection, item.getItemCode(), item.getItemName(), item.getQtyOnHand(), item.getUnitPrice());
    }

    @Override
    public ArrayList<Item> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("select * from Item", connection);
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

    @Override
    public Item search(String Icode, Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rest = CrudUtil.executeQuery("select * from Item where itemCode=?", connection, Icode);
        Item item = new Item();
        while (rest.next()) {
            item.setItemCode(rest.getString(1));
            item.setItemName(rest.getString(2));
            item.setQtyOnHand(rest.getInt(3));
            item.setUnitPrice(rest.getDouble(4));
        }
        return item;
    }

    @Override
    public boolean update(Item item, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("Update Item set itemrName=?,itemQuantity=?,itemPrice=? where itemCode=?", connection, item.getItemName(), item.getQtyOnHand(), item.getUnitPrice(), item.getItemCode());
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("Delete from Item where itemCode=?", connection, id);
    }

    public void updateItemQty(String itemCode, int qty, Connection connection) throws SQLException, ClassNotFoundException {
        CrudUtil.executeUpdate("UPDATE Item SET itemQuantity=(itemQuantity-" + qty + ") WHERE itemCode=?", connection, itemCode);
    }
}
