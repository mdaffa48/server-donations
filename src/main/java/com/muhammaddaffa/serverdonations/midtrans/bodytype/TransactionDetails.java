package com.muhammaddaffa.serverdonations.midtrans.bodytype;

import java.util.HashMap;
import java.util.Map;

public class TransactionDetails {

    private final String orderId;
    private final int price;

    public TransactionDetails(String orderId, int price) {
        this.orderId = orderId;
        this.price = price;
    }

    public String getOrderId(){
        return orderId;
    }

    public int getPrice(){
        return price;
    }

    public Map<String, String> toMap(){
        final Map<String, String> map = new HashMap<>();
        map.put("order_id", this.orderId);
        map.put("gross_amount", String.valueOf(this.price));
        return map;
    }

}
