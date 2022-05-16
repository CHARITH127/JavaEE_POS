package dbcp;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class myListner implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/company");
        bds.setUsername("root");
        bds.setPassword("ijse");
        bds.setMaxTotal(10);
        bds.setInitialSize(10);

        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("bds",bds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");
        try {
            bds.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
