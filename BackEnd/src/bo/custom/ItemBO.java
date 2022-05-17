package bo.custom;

import bo.SuperBO;
import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    public boolean addItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException;


    public ItemDTO getItem(String id, Connection connection) throws SQLException, ClassNotFoundException;


    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException;


    public boolean updateItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException;


    public boolean deleteItem(String id, Connection connection) throws SQLException, ClassNotFoundException;

    public void updateItemQty(String itemCode, int qty, Connection connection) throws SQLException, ClassNotFoundException;
}
