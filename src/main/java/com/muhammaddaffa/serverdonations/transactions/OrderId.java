package com.muhammaddaffa.serverdonations.transactions;

import org.bukkit.entity.Player;

import java.util.UUID;

public class OrderId {

    public static String generate(Player player){
        return generate(player.getName());
    }

    public static String generate(String customerName){
        return customerName + "_" + UUID.randomUUID();
    }

}
