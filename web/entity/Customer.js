function Customer(customerId,customerName,customerAddress,customerSalary) {
    var __customerId =customerId;
    var __customerName =customerName;
    var __customerAddress =customerAddress;
    var __customerSalary =customerSalary;

    this.setCustomerId = function (customerId) {
        __customerId=customerId;
    }
    this.getCustomerId = function () {
        return __customerId;
    }
    this.setCustomerName = function (customerName) {
        __customerName=customerName;
    }
    this.getCustomerName = function () {
        return __customerName;
    }
    this.setCustomerAddress = function (customerAddress) {
        __customerAddress=customerAddress;
    }
    this.getCustomerAddress = function () {
        return __customerAddress;
    }
    this.setCustomerSalary = function (customerSalary) {
        __customerSalary=customerSalary;
    }
    this.getCustomerSalary = function () {
        return __customerSalary;
    }
}