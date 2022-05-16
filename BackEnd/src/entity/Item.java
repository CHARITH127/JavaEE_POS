package entity;

public class Item {
    private String itemCode;
    private String itemName;
    private int qtyOnHand ;
    private double UnitPrice;

    public Item() {
    }

    public Item(String itemCode, String itemName, int qtyOnHand, double unitPrice) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.qtyOnHand = qtyOnHand;
        UnitPrice = unitPrice;
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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }
}
