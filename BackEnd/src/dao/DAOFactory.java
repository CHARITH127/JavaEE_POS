package dao;

import dao.custom.impl.CustomerDAOImpl;
import dao.custom.impl.ItemDAOImpl;
import dao.custom.impl.orderDAOImpl;
import dao.custom.impl.orderDetailsDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }
    public static DAOFactory getDaoFactory(){
        if (daoFactory==null) {
            daoFactory= new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new orderDAOImpl();
            case ORDERDETAILS:
                return new orderDetailsDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes{
        CUSTOMER,ITEM,ORDER,ORDERDETAILS
    }
}
