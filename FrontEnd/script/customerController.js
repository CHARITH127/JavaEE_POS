function loadCustomer() {
    $(".customerTableBody").empty();
    $.ajax({
        url: "http://localhost:8080/JavaEEPOS/customer?option=GETALL",
        method: "GET",
        success: function (resp) {
            for (const customer of resp) {
                let row = `<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`;
                $(".customerTableBody").append(row);
            }
        }
    });
};

loadCustomer();

$("#btn_AddCustomer").click(function () {
    var customerID = $("#CustomerID").val();
    var customerName = $("#CustomerName").val();
    var customerAddress = $("#CustomerAddress").val();
    var customerSalary = $("#CustomerSalary").val();

    let customer = {
        id: customerID,
        name: customerName,
        address: customerAddress,
        salary: customerSalary
    }

    $.ajax({
        url: "http://localhost:8080/JavaEEPOS/customer",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(customer),
        success:function (res) {
            if (res.status == 200) {
                alert(res.message);
                loadCustomer();
            } else if (res.status == 400) {
                alert(res.message);
            } else {
                alert(res.data);
            }
        }
    });
});

function searchCustomer(){
    let customerID = $("#CustomerSearch").val();

    $.ajax({
        url:"http://localhost:8080/JavaEEPOS/customer?option=SEARCH&custID="+customerID,
        method:"GET",
        success:function (resp) {
            console.log(resp.id+" "+resp.name+" "+resp.address+" "+resp.salary);
            $("#searchCustomerID").val(resp.id);
            $("#searchCustomerName").val(resp.name);
            $("#searchCustomerAddress").val(resp.address);
            $("#searchCustomerSalary").val(resp.salary);
        }
    });
}

$("#btnSearchCustomer").click(function () {
    searchCustomer();
});

$("#deleteCustomer").click(function () {
    searchCustomer();
    let custID = $("#CustomerSearch").val();
    $.ajax({
        url:"http://localhost:8080/JavaEEPOS/customer?cutID="+custID,
        method:"DELETE",
        success:function (resp) {
            if (resp.status == 200) {
                alert(resp.message);
                loadCustomer();
            } else if (resp.status == 400) {
                alert(resp.data);
            } else {
                alert(resp.data);
            }
        }
    });
});

$("#updateCustomer").click(function () {

    searchCustomer();

    var customerID = $("#searchCustomerID").val();
    var customerName = $("#searchCustomerName").val();
    var customerAddress = $("#searchCustomerAddress").val();
    let customerSalary = $("#searchCustomerSalary").val();

    let updateCustomer = {
        id: customerID,
        name: customerName,
        address: customerAddress,
        salary: customerSalary
    }

    $.ajax({
        url:"http://localhost:8080/JavaEEPOS/customer",
        method:"PUT",
        contentType: "application/json",
        data: JSON.stringify(updateCustomer),
        success:function (resp) {
            if (resp.status == 200) { // process is  ok
                alert(resp.message);
                loadCustomer();
            } else if (resp.status == 400) { // there is a problem with the client side
                alert(resp.message);
            } else {
                alert(resp.data); // else maybe there is an exception
            }
        }
    });
});
