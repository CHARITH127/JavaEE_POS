package dao.custom.impl;

import dao.custom.CustomerDAO;
import entity.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean add(Customer customer, Connection connection) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("Insert into Customer values(?,?,?,?)");
        stm.setObject(1, customer.getCustomerID());
        stm.setObject(2, customer.getCustomerName());
        stm.setObject(3, customer.getCustomerAddress());
        stm.setObject(4, customer.getCustomerSalary());

        if (stm.executeUpdate() > 0) {
            connection.close();
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection)throws SQLException, ClassNotFoundException{
        ResultSet rst = connection.prepareStatement("select * from Customer").executeQuery();
        ArrayList<Customer> customers = new ArrayList<>();
        while (rst.next()) {
            Customer customer = new Customer();
            customer.setCustomerID(rst.getString(1));
            customer.setCustomerName(rst.getString(2));
            customer.setCustomerAddress(rst.getString(3));
            customer.setCustomerSalary(rst.getDouble(4));

            customers.add(customer);

        }
        return customers;
    }

    @Override
    public Customer search(String Cid, Connection connection) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = connection.prepareStatement("select * from Customer where customerID=?");
        stm.setObject(1, Cid);
        ResultSet rest = stm.executeQuery();
        Customer customer = new Customer();
        while (rest.next()) {
            customer.setCustomerID(rest.getString(1));
            customer.setCustomerName(rest.getString(2));
            customer.setCustomerAddress(rest.getString(3));
            customer.setCustomerSalary(rest.getDouble(4));
        }
        return customer;
    }

    @Override
    public boolean update(Customer customer, Connection connection) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = connection.prepareStatement("Update Customer set customerName=?,customerAddress=?,salary=? where customerID=?");
        stm.setObject(1, customer.getCustomerName());
        stm.setObject(2, customer.getCustomerAddress());
        stm.setObject(3, customer.getCustomerSalary());
        stm.setObject(4, customer.getCustomerID());

        if (stm.executeUpdate() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        PreparedStatement stm = connection.prepareStatement("Delete from Customer where customerID=?");
        stm.setObject(1, id);
        if (stm.executeUpdate() > 0) {
            return true;
        }
        return false;
    }
}
