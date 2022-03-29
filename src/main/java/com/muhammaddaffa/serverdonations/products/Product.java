package com.muhammaddaffa.serverdonations.products;

import java.util.List;

public class Product {

    private final String name;
    private final String displayName;
    private final int price;

    private final List<String> commands;

    public Product(String name, String displayName, int price, List<String> commands) {
        this.name = name;
        this.displayName = displayName;
        this.price = price;
        this.commands = commands;
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

    public List<String> getCommands() {
        return commands;
    }
}
