package bo;

import dao.ItemDAOImpl;
import dto.CustomerDTO;
import dto.ItemDTO;
import entity.Customer;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl {

    ItemDAOImpl itemDAO = new ItemDAOImpl();
    public boolean addItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.addItem(new Item(itemDTO.getItemCode(),itemDTO.getItemName(),itemDTO.getQtyOnHand(),itemDTO.getUnitPrice()),connection);
    }


    public ItemDTO getItem(String id, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.getItem(id, connection);
        return new ItemDTO(item.getItemCode(),item.getItemName(),item.getQtyOnHand(),item.getUnitPrice());
    }


    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAllItems(connection);
        ArrayList<ItemDTO> itemDTOS = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            ItemDTO itemDTO = new ItemDTO();
            itemDTO.setItemCode(items.get(i).getItemCode());
            itemDTO.setItemName(items.get(i).getItemName());
            itemDTO.setQtyOnHand(items.get(i).getQtyOnHand());
            itemDTO.setUnitPrice(items.get(i).getUnitPrice());

            itemDTOS.add(itemDTO);
        }

        return itemDTOS;
    }


    public boolean updateItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getItemCode(), itemDTO.getItemName(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
        return itemDAO.updateItem(item,connection);
    }


    public boolean deleteItem(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.deleteItem(id,connection);
    }
}
