/*loading current Date */
var now = new Date();
var day = ("0" + now.getDate()).slice(-2);
var month = ("0" + (now.getMonth() + 1)).slice(-2);
var today = now.getFullYear() + "-" + (month) + "-" + (day);
$('#OrderDate').val(today);

/*set order ID*/
function setOrderID(){
    $.ajax({
        url: "http://localhost:8080/JavaEEPOS/orders?option=getOrderID",
        method: "GET",
        success: function (resp) {
            var oID = resp.id;
            $("#OrderID").val(oID);
        }
    });
}

setOrderID();

/*search customer on place order form*/
$("#OrderCustomerId").keydown(function (event) {

    if (event.key == "Enter") {
        var oCustId = $("#OrderCustomerId").val();

        $.ajax({
            url: "http://localhost:8080/JavaEEPOS/customer?option=SEARCH&custID=" + oCustId,
            method: "GET",
            success: function (resp) {
                console.log(resp.id + " " + resp.name + " " + resp.address + " " + resp.salary);
                $("#OrderCustomerId").val(resp.id);
                $("#OrderCustomerName").val(resp.name);
                $("#OrderCustomerAddress").val(resp.address);
                $("#OrderCustomerSalary").val(resp.salary);
            }
        });
    }
});

/*search items of place order form*/
$("#OrderItemCode").keydown(function (event) {
    if (event.key == "Enter") {
        var itemCode = $("#OrderItemCode").val();
        $.ajax({
            url: "http://localhost:8080/JavaEEPOS/item?option=SEARCH&itemCode=" + itemCode,
            method: "GET",
            success: function (res) {
                $("#OrderItemCode").val(res.itemCode);
                $("#OrderItemName").val(res.itemName);
                $("#QtyOnHand").val(res.itemQty);
                $("#OrderItemPrice").val(res.itemPrice);
            }
        });
        $("#OrderQuantity").focus();

    }
});

$("#OrderQuantity").keydown(function (e) {
    if (e.key == "Enter") {
        $("#addToCart").attr("disabled", false);
    }
})

$("#cash").keydown(function (e) {
    if (e.key == "Enter") {
        $("#purchaseOrder").attr("disabled", false);
    }
});

$("#addToCart").click(function () {

    var itemCode = $("#OrderItemCode").val();

    if (checkItemIsExsist(itemCode)) {
        isExitItemCode(itemCode);
        setNetTotal();
    } else {
        addToCart();
        loadAllDetailsToCart();
    }
});

/*Add items to the cart*/
let cartDB = new Array();
function addToCart() {
    let itemCode = $("#OrderItemCode").val();
    let itemName = $("#OrderItemName").val();
    let qty = parseInt($("#OrderQuantity").val());
    var unitPrice = parseInt($("#OrderItemPrice").val());
    let total = unitPrice * qty;
    var newQty = parseInt($("#QtyOnHand").val())-parseInt($("#OrderQuantity").val());
    $("#QtyOnHand").val(newQty);
    let cartTm ={
        itemCode:itemCode,
        itemName:itemName,
        unitPrice:unitPrice,
        qty:qty,
        total:total
    }
    cartDB.push(cartTm);
}

function loadAllDetailsToCart() {
    $(".cartTableBody").empty();
    for (let cartitem of cartDB) {
        var row = `<tr><td>${cartitem.itemCode}</td><td>${cartitem.itemName}</td><td>${cartitem.unitPrice}</td><td>${cartitem.qty}</td><td>${cartitem.total}</td></tr>`;
        $(".cartTableBody").append(row);
        setNetTotal();
    }

    /*to remove the item from the cart*/
    $(".cartTableBody>tr").click(function () {
        if (confirm("Are you sure to delete this item..?")) {
            var row = $(this).closest("tr");
            var itemID = row.find("td:eq(0)").text();
            var itemQty = parseInt(row.find("td:eq(3)").text());
            var newQty = parseInt($("#QtyOnHand").val())+itemQty;
            $("#QtyOnHand").val(newQty);

            setNetTotal();
        }
        loadAllDetailsToCart();
    });
}

/*set net total*/
function setNetTotal() {
    var netTotal = 0;
    for (let i = 0; i < cartDB.length; i++) {
        netTotal += cartDB[i].total;
    }
    $("#totalnumber").text(netTotal + " .00");
}

function checkItemIsExsist(code) {
    for (let i = 0; i < cartDB.length; i++) {
        if (cartDB[i].itemCode == code) {
            return true;
        }
    }
}

function isExitItemCode(itemCode) {

    if (checkTheItemQtyAvailability(itemCode)) {
        for (let i = 0; i < cartDB.length; i++) {
            if (cartDB[i].itemCode == itemCode) {
                var secndQty = parseInt($("#OrderQuantity").val());
                var newQty = +cartDB[i].qty+ +secndQty;
                var updateQty =parseInt($("#QtyOnHand").val())-newQty;
                $("#QtyOnHand").val(updateQty);
                var newTotal = parseInt($("#OrderItemPrice").val()) * parseInt(newQty);
                cartDB[i].qty=newQty;
                cartDB[i].total=newTotal;

                /*set total*/
                setNetTotal();

                /*refresh the Item Table*/
                loadAllItems();

                /*load all items to the cart*/
                loadAllDetailsToCart();
            }
        }/*set net total*/
function setNetTotal() {
    var netTotal = 0;
    for (let i = 0; i < cartDB.length; i++) {
        netTotal += cartDB[i].total;
    }
    $("#totalnumber").text(netTotal + " .00");
}
    }else {
        alert("You exead the maximum rate of the item quantity in store please recheck the item quantity.");
    }
}

/*check QTYONHand availability*/
function checkTheItemQtyAvailability(code) {
    var itemObject = parseInt($("#QtyOnHand").val())
    var placedItemQty = parseInt($("#OrderQuantity").val());
    if (itemObject >= placedItemQty) {
        return true;
    } else {
        return false;
    }
}

/*calcuate balance event */
$("#cash").keydown(function (e) {
    if (e.key == "Enter") {
        var cash = parseInt($("#cash").val());
        calculateBalance(cash);
    }
});

/*set subTotal according to the discount event*/
$("#Discount").keydown(function (e) {
    if (e.key == "Enter") {
        var discount = parseInt($("#Discount").val());
        setSubtotal(discount);
    }
});

/*set sub total*/
function setSubtotal(discount) {
    var total = parseInt($("#totalnumber").text());
    var subTotal = total - (total * discount / 100);

    $("#subTotalNumber").text(subTotal);

}

/*calculate the balance*/
function calculateBalance(cash) {
    var balance = cash - parseInt($("#subTotalNumber").text());
    $("#Balance").val(balance + " .00");
}

$("#purchaseOrder").click(function () {
    placeOrder();
    $("#addToCart").attr("disabled", true);
    $("#purchaseOrder").attr("disabled", true);
});



/*place order*/
function placeOrder() {
    var orderId =$("#OrderID").val();
   var date = $("#OrderDate").val();
   var customerID = $("#OrderCustomerId").val();
   var customerTotal =parseInt($("#subTotalNumber").text());
   var cart =cartDB

   var orderObject ={
       orderId:orderId,
       orderDate:date,
       customerId:customerID,
       totalPrice:customerTotal,
       cart:cart
    }


    $.ajax({
        url: "http://localhost:8080/JavaEEPOS/orders",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(orderObject),
        success: function (resp) {
            if (resp.status == 200) { // process is  ok
                alert(resp.message);
                setOrderID();
                clearAllOrderPage();
            } else if (resp.status == 400) { // there is a problem with the client side
                alert(resp.message);
            } else {
                alert(resp.data); // else maybe there is an exception
            }
        }
    });
}

function clearAllOrderPage() {
    $("#OrderCustomerId").val("");
    $("#OrderCustomerName").val("");
    $("#OrderCustomerSalary").val("");
    $("#OrderCustomerAddress").val("");
    $("#OrderItemCode").val("");
    $("#OrderItemName").val("");
    $("#QtyOnHand").val("");
    $("#OrderItemPrice").val("");
    $("#OrderQuantity").val("");
    $("#totalnumber").text("");
    $("#subTotalNumber").text("");
    $("#cash").val("");
    $("#Discount").val("");
    $("#Balance").val("");
    $(".cartTableBody").empty();
    cartDB.splice(0,cartDB.length);
}