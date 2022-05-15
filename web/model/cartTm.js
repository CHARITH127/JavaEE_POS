function CartTm(itemId, itemName, price, quantity, total) {
    var __itemId = itemId;
    var __itemName = itemName;
    var __itemPrice = price;
    var __itemQuantity = quantity;
    var __itemTotal = total;

    this.setItemID = function (itemId) {
        __itemId = itemId;
    }
    this.getItemID = function () {
        return __itemId;
    }
    this.setItemName = function (itemName) {
        __itemName = itemName;
    }
    this.getItemName = function () {
        return __itemName;
    }
    this.setItemPrice = function (itemPrice) {
        __itemPrice = itemPrice;
    }
    this.getItemPrice = function () {
        return __itemPrice;
    }
    this.setItemQuantity = function (itemQuantity) {
        __itemQuantity = itemQuantity;
    }
    this.getItemQuantity = function () {
        return __itemQuantity;
    }
    this.setItemTotal = function (itemTotal) {
        __itemTotal = itemTotal;
    }
    this.getItemTotal = function () {
        return __itemTotal;
    }
}