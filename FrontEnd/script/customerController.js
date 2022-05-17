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
    $("#btn_AddCustomer").attr("disabled", true);
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

/*================ customer page validation===========================*/
const cusIDRegEx = /^(C)[0-9]{1,3}$/;
const cusNameRegEx = /^[A-z ]{5,20}$/;
const cusAddressRegEx = /^[0-9/A-z. ,]{7,}$/;
const cusSalaryRegEx = /^[0-9]{1,}[.]?[0-9]{1,2}$/;

$("#CustomerID").keydown(function (e) {
    if (e.key == "Enter") {
        var output = $("#CustomerID").val();
        if (cusIDRegEx.test(output)) {
            $("#CustomerID").css('border-color', '#04db14');
            $("#CustomerID").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
            $("#CustomerID").css('color', '#04db14');
            $("#lblcustomerID").text("");
            $("#CustomerName").focus();
        } else {
            $("#CustomerID").css('border-color', '#ff0202');
            $("#CustomerID").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
            $("#CustomerID").css('color', '#ff0202');
            $("#lblcustomerID").text("Cus ID is a required field : Pattern C00-000");
            $("#CustomerID").focus();
        }
    }
});
$("#CustomerName").keydown(function (e) {
    if (e.key == "Enter") {
        var output = $("#CustomerName").val();
        if (cusNameRegEx.test(output)) {
            $("#CustomerName").css('border-color', '#04db14');
            $("#CustomerName").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
            $("#CustomerName").css('color', '#04db14');
            $("#lblcustomerName").text("");
            $("#CustomerAddress").focus();
        } else {
            $("#CustomerName").css('border-color', '#ff0202');
            $("#CustomerName").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
            $("#CustomerName").css('color', '#ff0202');
            $("#lblcustomerName").text("Cus Name is a required field : Mimimum 5, Max 20, Spaces Allowed");
            $("#CustomerName").focus();
        }
    }
});
$("#CustomerAddress").keydown(function (e) {
    if (e.key == "Enter") {
        var output = $("#CustomerAddress").val();
        if (cusAddressRegEx.test(output)) {
            $("#CustomerAddress").css('border-color', '#04db14');
            $("#CustomerAddress").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
            $("#CustomerAddress").css('color', '#04db14');
            $("#lblcustomerAddress").text("");
            $("#CustomerSalary").focus();
        } else {
            $("#CustomerAddress").css('border-color', '#ff0202');
            $("#CustomerAddress").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
            $("#CustomerAddress").css('color', '#ff0202');
            $("#lblcustomerAddress").text("Cus Address is a required field : Mimum 7");
            $("#CustomerAddress").focus();
        }
    }
});
$("#CustomerSalary").keydown(function (e) {
    if (e.key == "Enter") {
        var output = $("#CustomerSalary").val();
        if (cusSalaryRegEx.test(output)) {
            $("#CustomerSalary").css('border-color', '#04db14');
            $("#CustomerSalary").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
            $("#CustomerSalary").css('color', '#04db14');
            $("#lblcustomerSalary").text("");
            $("#btn_AddCustomer").attr("disabled", false);
        } else {
            $("#CustomerSalary").css('border-color', '#ff0202');
            $("#CustomerSalary").css('box-shadow', '0 0 0 0.25rem rgb(13 110 253 / 25%)');
            $("#CustomerSalary").css('color', '#ff0202');
            $("#lblcustomerSalary").text("Cus Salary is a required field : Pattern 100.00 or 100");
            $("#CustomerSalary").focus();
        }
    }
});
