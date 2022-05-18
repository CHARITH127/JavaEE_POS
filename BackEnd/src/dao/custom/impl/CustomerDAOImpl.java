package dao.custom.impl;

import dao.CrudUtil;
import dao.custom.CustomerDAO;
import entity.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public boolean add(Customer customer, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("Insert into Customer values(?,?,?,?)", connection, customer.getCustomerID(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerSalary());
    }

    @Override
    public ArrayList<Customer> getAll(Connection connection) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.executeQuery("select * from Customer", connection);
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
        ResultSet rest = CrudUtil.executeQuery("select * from Customer where customerID=?", connection, Cid);
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
        return CrudUtil.executeUpdate("Update Customer set customerName=?,customerAddress=?,salary=? where customerID=?", connection, customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerSalary(), customer.getCustomerID());
    }

    @Override
    public boolean delete(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return CrudUtil.executeUpdate("Delete from Customer where customerID=?",connection,id);
    }
}
