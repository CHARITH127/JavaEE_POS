package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.OrderBOImpl;
import bo.custom.impl.OrderDetailsBOImpl;

public class BoFactory {
    private static BoFactory boFactory;

    private BoFactory(){

    }

    public static BoFactory getBoFactory(){
        if (boFactory==null) {
            boFactory= new BoFactory();
        }
        return boFactory;
    }

    public SuperBO getBo(BoTypes types){
        switch (types) {
            case Item:
                return new ItemBOImpl();
            case Customer:
                return new CustomerBOImpl();
            case Order:
                return new OrderBOImpl();
            case OrderDetails:
                return new OrderDetailsBOImpl();
            default:
                return null;
        }
    }

    public enum BoTypes {
        Customer, Item, Order, OrderDetails
    }
}
