/*================ loading context according to the selected item of the nav bar =====================================*/

/* nav home*/
$("#home").click(function () {
    $("#itemManage").css("display", "none");
    $("#customerManage").css("display", "none");
    $("#orderManagement").css("display", "none");
    $("#orderInvoice").css("display", "block");
});

/*nav Order*/
$("#order").click(function () {
    $("#itemManage").css("display", "none");
    $("#orderInvoice").css("display", "none");
    $("#customerManage").css("display", "none");
    $("#orderManagement").css("display", "block");
})

/*nav Items*/
$("#item").click(function () {
    $("#orderInvoice").css("display", "none");
    $("#customerManage").css("display", "none");
    $("#orderManagement").css("display", "none");
    $("#itemManage").css("display", "block");

});

/*nav Customer*/
$("#customer").click(function () {
    $("#orderInvoice").css("display", "none");
    $("#itemManage").css("display", "none");
    $("#orderManagement").css("display", "none");
    $("#customerManage").css("display", "block");
});
/*
/!*================Item Page Event Start===========================*!/
/!*save item butten*!/
$("#btn_AddNewItem").click(function () {
    saveItem();
    loadAllItems();
    clearAllItemAdd();
    $("#btn_AddNewItem").attr("disabled", false);
});

/!*search butten*!/
$("#searchButten").click(function () {
    var searchCode = $("#ItemSearch").val();
    var response = searchItem(searchCode);
    if (response) {
        $("#searchItemCode").val(response.getItemCodeO());
        $("#searchItemName").val(response.getItemNameO());
        $("#searchItemQuantity").val(response.getItemQuantityO());
        $("#searchItemPrice").val(response.getItemPriceO());
    } else {
        alert("Not such a Item in here");
    }
});

/!*delete butten*!/
$("#deleteItem").click(function () {
    if (confirm("Are you sure to delete this item..?")) {
        var searchCode = $("#ItemSearch").val();
        deleteItems(searchCode);
        loadAllItems();
        clearAllSerchForm();
    }

});

/!*update butten*!/
$("#update").click(function () {
    if (updateItem()) {

    }else {
        alert("Successfully updated");
    }
});

/!*================Item Page Event End===========================*!/




/!*================Customer Page Event Start===========================*!/

/!* btn save Customer *!/
$("#btn_AddCustomer").click(function () {
    saveCustomer();
    loadAllCustomer();
    clearAddCustomer();
    $("#btn_AddCustomer").attr("disabled", false);
});

/!* btn search customer *!/
$("#btnSearchCustomer").click(function () {
    var code = $("#CustomerSearch").val();
    var resp = searchCustomer(code);
    $("#searchCustomerID").val(resp.getCustomerID());
    $("#searchCustomerName").val(resp.getCustomerName());
    $("#searchCustomerAddress").val(resp.getCustomerAddress());
    $("#searchCustomerSalary").val(resp.getCustomerSalary());
});

/!* btn delete customer*!/
$("#deleteCustomer").click(function () {
    var code = $("#CustomerSearch").val();
    if (confirm("Are you sure to delete this item..?")) {
        deleteCustomer(code);
        loadAllCustomer();
    }
});
/!* btn update customer*!/
$("#updateCustomer").click(function () {
    updateCustomer();
});

/!*clear all customer search*!/
$("#clearAllCustomer").click(function () {
    clearSearchCustomer();
});

/!*clear add customer text field*!/
function clearAddCustomer(){
    $("#CustomerID").val("");
    $("#CustomerName").val("");
    $("#CustomerAddress").val("");
    $("#CustomerSalary").val("");
}

/!*clear search customer text field*!/
function clearSearchCustomer(){
    $("#searchCustomerID").val("");
    $("#searchCustomerName").val("");
    $("#searchCustomerAddress").val("");
    $("#searchCustomerSalary").val("");
}

/!*================Customer Page Event End===========================*!/

/!*=========================== Order Page Events start=====================*!/

/!*loading current Date *!/
d = new Date();
$("#OrderDate").val(d.toLocaleDateString());

/!*set order ID*!/
var orderId = setOrderId();
$("#OrderID").val("O00 - " + orderId);

/!*load customer details to the order form*!/
$("#OrderCustomerId").keydown(function (e) {
    var id = $("#OrderCustomerId").val();
    if (e.key == "Enter") {
        var Customer = searchCustomer(id);
        $("#OrderCustomerName").val(Customer.getCustomerName());
        $("#OrderCustomerAddress").val(Customer.getCustomerAddress());
        $("#OrderCustomerSalary").val(Customer.getCustomerSalary());
    }
})

/!*load item details to the order form*!/
$("#OrderItemCode").keydown(function (e) {
    var id = $("#OrderItemCode").val();
    if (e.key == "Enter") {
        var Item = searchItem(id);
        $("#OrderItemName").val(Item.getItemNameO());
        $("#QtyOnHand").val(Item.getItemQuantityO());
        $("#OrderItemPrice").val(Item.getItemPriceO());
        $("#OrderQuantity").focus();
    }
})

/!*add items to the cart *!/
$("#addToCart").click(function () {
    var code = $("#OrderItemCode").val();
    if (isExistItem(code) != true) {
        addDetailsToTheCart();
        loadAllItemDetails();
    } else {
        itemIsExistOnCart();
    }
});
$("#OrderQuantity").keydown(function (e) {
    if (e.key == "Enter") {
        $("#addToCart").attr("disabled", false);
    }
})

/!*set subTotal according to the discount event*!/
$("#Discount").keydown(function (e) {
    if (e.key == "Enter") {
        var discount = parseInt($("#Discount").val());
        setSubtotal(discount);
    }
});

/!*calcuate balance event *!/
$("#cash").keydown(function (e) {
    if (e.key == "Enter") {
        var cash = parseInt($("#cash").val());
        calculateBalance(cash);
        $("#purchaseOrder").attr("disabled", false);
    }
});

/!*purchase order event*!/
$("#purchaseOrder").click(function () {
    placeOrder();
    alert("Order is successfully placed.");
    var orderId = setOrderId();
    $("#OrderID").val("O00 - " + orderId);
    clearAllOrderPage();
});

/!*=========================== Order Page Events Ends=====================*!/
*/
