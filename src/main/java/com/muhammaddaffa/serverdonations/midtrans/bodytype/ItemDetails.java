package com.muhammaddaffa.serverdonations.midtrans.bodytype;

import java.util.HashMap;
import java.util.Map;

public class ItemDetails {

    private final String name;
    private final int price;
    private final int qty;

    public ItemDetails(String name, int price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }

    public Map<String, String> toMap(){
        final Map<String, String> item = new HashMap<>();
        item.put("price", price + "");
        item.put("quantity", qty + "");
        item.put("name", name);
        return item;
    }

}
