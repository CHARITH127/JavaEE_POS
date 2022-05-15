$("#btnsearchOrder").click(function () {
    var OrderID = $("#searchOrder").val();

    $.ajax({
        url:"orderDetails?oID="+OrderID,
        method:"GET",
        success:function (resp) {
            for (const rest of resp) {
                var tableRow = `<tr><td>${rest.itemCode}</td><td>${rest.itemName}</td><td>${rest.itemPrice}</td><td>${rest.itemQty}</td><td>${rest.total}</td></tr>`;
                $(".manageOrderTable").append(tableRow);
            }
        }
    });
});