package dto;

public class OrderDetailsDTO {
    private String orderId;
    private String itemCode;
    private String itemName;
    private double price;
    private int qty;
    private double total;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(String orderId, String itemCode, String itemName, double price, int qty, double total) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.price = price;
        this.qty = qty;
        this.total = total;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
