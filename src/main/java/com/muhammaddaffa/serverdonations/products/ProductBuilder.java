package com.muhammaddaffa.serverdonations.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductBuilder {

    private String key;
    private String name;
    private String displayName;
    private Integer price;
    private List<String> commands = new ArrayList<>();

    public ProductBuilder setKey(String key){
        this.key = key;
        return this;
    }

    public ProductBuilder setName(String name){
        this.name = name;
        return this;
    }

    public ProductBuilder setDisplayName(String displayName){
        this.displayName = displayName;
        return this;
    }

    public ProductBuilder setPrice(int price){
        this.price = price;
        return this;
    }

    public ProductBuilder setCommands(List<String> commands){
        this.commands = commands;
        return this;
    }

    public Product build(){
        Objects.requireNonNull(key, "Key of the product cannot be null");
        Objects.requireNonNull(name, "Name of the product cannot be null");
        Objects.requireNonNull(displayName, "Display Name of the product cannot be null");
        Objects.requireNonNull(price, "Price of the product cannot be null");

        return new Product(
                key,
                name,
                displayName,
                price,
                commands
        );
    }

}
