package com.muhammaddaffa.serverdonations.transactions;

import com.muhammaddaffa.serverdonations.products.Product;

public class Transaction {

    private final String customerName;
    private final String orderId;
    private final Product product;

    public Transaction(String customerName, String orderId, Product product) {
        this.customerName = customerName;
        this.orderId = orderId;
        this.product = product;
    }


    public String getCustomerName() {
        return customerName;
    }

    public String getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

}
