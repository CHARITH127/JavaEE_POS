package bo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class orderDetails {
    public void saveDetailsOnOrderDetails(String orderID,ArrayList orderDetails){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/company", "root", "ijse");
            PreparedStatement stm = connection.prepareStatement("INSERT INTO orderDetails VALUES(?,?,?,?,?,?)");
            for (int i = 0; i < orderDetails.size(); i++) {
                stm.setObject(1,orderID);

            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
