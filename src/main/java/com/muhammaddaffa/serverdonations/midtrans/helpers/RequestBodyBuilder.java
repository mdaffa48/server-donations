package com.muhammaddaffa.serverdonations.midtrans.helpers;

import com.muhammaddaffa.serverdonations.midtrans.bodytype.CustomerDetails;
import com.muhammaddaffa.serverdonations.midtrans.bodytype.ItemDetails;
import com.muhammaddaffa.serverdonations.midtrans.bodytype.TransactionDetails;
import com.muhammaddaffa.serverdonations.products.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestBodyBuilder {

    private final Map<String, Object> body = new HashMap<>();
    private final List<Map<String, String>> items = new ArrayList<>();

    public RequestBodyBuilder setTransactionDetails(String orderId, Product product){
        this.body.put("transaction_details", new TransactionDetails(orderId, product.getPrice()).toMap());
        return this;
    }

    public RequestBodyBuilder setCustomerDetails(String name){
        this.body.put("customer_details", new CustomerDetails(name).toMap());
        return this;
    }

    public RequestBodyBuilder addItemDetail(Product product){
        this.items.add(new ItemDetails(product.getName(), product.getPrice(), 1).toMap());
        return this;
    }

    public Map<String, Object> build(){
        if(!items.isEmpty()){
            this.body.put("item_details", this.items);
        }
        return body;
    }

}
