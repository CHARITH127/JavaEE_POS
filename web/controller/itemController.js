function loadAllItems() {
    $(".itemTableBody").empty();
    $.ajax({
        url: "item?option=GETALL",
        method: "GET",
        success: function (resp) {
            for (const item of resp) {
                let row = `<tr><td>${item.itemCode}</td><td>${item.itemName}</td><td>${item.itemQty}</td><td>${item.itemPrice}</td></tr>`;
                $(".itemTableBody").append(row);
            }
        }
    });
}

loadAllItems();

$("#btn_AddNewItem").click(function () {
    var itemCode = $("#ItemCode").val();
    var itemName = $("#ItemName").val();
    var itemQty = $("#ItemQuantity").val();
    var itemPrice = $("#ItemPrice").val();

    let item = {
        itemCode: itemCode,
        itemName: itemName,
        itemQty: itemQty,
        itemPrice: itemPrice
    }

    $.ajax({
        url: "item",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(item),
        success: function (res) {
            if (res.status == 200) {
                alert(res.message);
                loadAllItems();
            } else if (res.status == 400) {
                alert(res.message);
            } else {
                alert(res.data);
            }
        }
    });
});

function searchItem() {
    let itemCode = $("#ItemSearch").val();

    $.ajax({
        url: "item?option=SEARCH&itemCode="+itemCode,
        method: "GET",
        success: function (resp) {
            console.log(resp.itemCode + " " + resp.itemName + " " + resp.itemQty + " " + resp.itemPrice);
            $("#searchItemCode").val(resp.itemCode);
            $("#searchItemName").val(resp.itemName);
            $("#searchItemQuantity").val(resp.itemQty);
            $("#searchItemPrice").val(resp.itemPrice);
        }
    });
}

$("#searchButten").click(function () {
    searchItem();
});

$("#deleteItem").click(function () {
    searchItem()
    let itemCode = $("#ItemSearch").val();
    $.ajax({
        url: "item?itemCode=" + itemCode,
        method: "DELETE",
        success: function (resp) {
            if (resp.status == 200) {
                alert(resp.message);
                loadAllItems();
            } else if (resp.status == 400) {
                alert(resp.data);
            } else {
                alert(resp.data);
            }
        }
    });
});

$("#update").click(function () {

    searchItem();

    let itemCode = $("#searchItemCode").val();
    let itemName = $("#searchItemName").val();
    let itemQty = $("#searchItemQuantity").val();
    let itemPrice = $("#searchItemPrice").val();

    let updateItem = {
        itemCode: itemCode,
        itemName: itemName,
        itemQty: itemQty,
        itemPrice: itemPrice
    }

    $.ajax({
        url: "item?option=UpdateItem",
        method: "PUT",
        contentType: "application/json",
        data: JSON.stringify(updateItem),
        success: function (resp) {
            if (resp.status == 200) { // process is  ok
                alert(resp.message);
                loadAllItems();
            } else if (resp.status == 400) { // there is a problem with the client side
                alert(resp.message);
            } else {
                alert(resp.data); // else maybe there is an exception
            }
        }
    });
});
