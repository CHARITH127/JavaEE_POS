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