package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.custom.impl.ItemDAOImpl;
import dto.ItemDTO;
import entity.Item;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public boolean addItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.add(new Item(itemDTO.getItemCode(), itemDTO.getItemName(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice()), connection);
    }

    @Override
    public ItemDTO getItem(String id, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.search(id, connection);
        return new ItemDTO(item.getItemCode(), item.getItemName(), item.getQtyOnHand(), item.getUnitPrice());
    }

    @Override
    public ArrayList<ItemDTO> getAllItems(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Item> items = itemDAO.getAll(connection);
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

    @Override
    public boolean updateItem(ItemDTO itemDTO, Connection connection) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getItemCode(), itemDTO.getItemName(), itemDTO.getQtyOnHand(), itemDTO.getUnitPrice());
        return itemDAO.update(item, connection);
    }

    @Override
    public boolean deleteItem(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id, connection);
    }

    @Override
    public void updateItemQty(String itemCode, int qty, Connection connection) throws SQLException, ClassNotFoundException {
        itemDAO.updateItemQty(itemCode, qty, connection);
    }
}
