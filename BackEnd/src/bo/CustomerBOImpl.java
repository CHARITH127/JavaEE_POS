package bo;

import dao.CustomerDAOImpl;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl {
    CustomerDAOImpl customerDAO = new CustomerDAOImpl();
    public boolean addCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException {
        return customerDAO.addCustomer(new Customer(customerDTO.getCustomerID(),customerDTO.getCustomerName(),customerDTO.getCustomerAddress(),customerDTO.getCustomerSalary()),connection);
    }


    public CustomerDTO getCustomer(String id, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.getCustomer(id, connection);
        return new CustomerDTO(customer.getCustomerID(),customer.getCustomerName(),customer.getCustomerAddress(),customer.getCustomerSalary());
    }


    public ArrayList<CustomerDTO> getAllCustomers(Connection connection) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> allCustomer = customerDAO.getAllCustomer(connection);
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


    public boolean updateCustomer(CustomerDTO customerDTO, Connection connection) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(customerDTO.getCustomerID(), customerDTO.getCustomerName(), customerDTO.getCustomerAddress(), customerDTO.getCustomerSalary());
        return customerDAO.updateCustomer(customer,connection);
    }


    public boolean deleteCustomer(String id, Connection connection) throws SQLException, ClassNotFoundException {
       return customerDAO.deleteCustomer(id,connection);
    }
}
