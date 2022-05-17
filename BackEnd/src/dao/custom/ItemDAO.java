package dao.custom;

import dao.CrudDAO;
import entity.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item,Connection,String> {
    public void updateItemQty(String itemCode, int qty, Connection connection) throws SQLException, ClassNotFoundException;
}
