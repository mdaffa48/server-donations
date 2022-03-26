package com.muhammaddaffa.serverdonations.midtrans.body;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestBodyBuilder {

    private final Map<String, Object> body = new HashMap<>();
    private final List<Map<String, String>> items = new ArrayList<>();

    public RequestBodyBuilder setTransactionDetails(String orderId, int price){
        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", orderId);
        transactionDetails.put("gross_amount", price + "");

        body.put("transaction_details", transactionDetails);
        return this;
    }

    public RequestBodyBuilder setCustomerDetails(String name){
        Map<String, String> customerDetails = new HashMap<>();
        customerDetails.put("first_name", name);

        body.put("customer_details", customerDetails);
        return this;
    }

    public RequestBodyBuilder addItemDetail(int price, int qty, String name){
        Map<String, String> item = new HashMap<>();
        item.put("price", price + "");
        item.put("quantity", qty + "");
        item.put("name", name);

        this.items.add(item);
        return this;
    }

    public Map<String, Object> build(){
        if(!items.isEmpty()){
            body.put("item_details", this.items);
        }
        return body;
    }

}
