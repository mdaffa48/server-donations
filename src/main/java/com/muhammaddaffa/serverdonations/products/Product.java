package com.muhammaddaffa.serverdonations.products;

public class Product {

    private final String name;
    private final String displayName;
    private final int price;

    public Product(String name, String displayName, int price) {
        this.name = name;
        this.displayName = displayName;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getPrice() {
        return price;
    }

}
