package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.impl.CustomerDAOImpl;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAOImpl customerDAO = (CustomerDAOImpl) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean addCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(customerDTO.getCustomerID(), customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerSalary()), connection);
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id, connection);
        return new CustomerDTO(customer.getCustomerID(), customer.getCustomerName(), customer.getCustomerAddress(), customer.getCustomerSalary());
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomer = customerDAO.getAll(connection);
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        for (int i = 0; i < allCustomer.size(); i++) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerID(allCustomer.get(i).getCustomerID());
            customerDTO.setCustomerName(allCustomer.get(i).getCustomerName());
            customerDTO.setCustomerAddress(allCustomer.get(i).getCustomerAddress());
            customerDTO.setCustomerSalary(allCustomer.get(i).getCustomerSalary());

            customerDTOS.add(customerDTO);
        }

        return customerDTOS;
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(customerDTO.getCustomerID(), customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerSalary());
        return customerDAO.update(customer, connection);
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id, connection);
    }
}
