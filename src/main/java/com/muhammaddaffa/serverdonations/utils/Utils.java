package com.muhammaddaffa.serverdonations.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Level;

public class Utils {

    public static void debug(String... messages){
        for (String message : messages) {
            Bukkit.getLogger().log(Level.INFO, message);
        }
    }

    public static String generateOrderId(Player player){
        return generateOrderId(player.getName());
    }

    public static String generateOrderId(String customerName){
        return customerName + "_" + UUID.randomUUID();
    }

}
