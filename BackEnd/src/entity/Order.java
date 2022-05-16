package entity;

import java.sql.Date;

public class Order {
    private String orderId;
    private Date oDate;
    private String customerId;
    private double total;

    public Order() {
    }

    public Order(String orderId, Date oDate, String customerId, double total) {
        this.orderId = orderId;
        this.oDate = oDate;
        this.customerId = customerId;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getoDate() {
        return oDate;
    }

    public void setoDate(Date oDate) {
        this.oDate = oDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
