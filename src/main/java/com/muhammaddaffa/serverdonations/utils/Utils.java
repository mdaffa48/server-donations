package com.muhammaddaffa.serverdonations.utils;

import com.muhammaddaffa.serverdonations.hooks.SkinsRestorerHook;
import net.skinsrestorer.api.SkinsRestorerAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.logging.Level;

public class Utils {

    public static String getMinepicURL(Player player) {
        String url = "https://minepic.org/avatar/8/";
        // Check if SkinsRestorer is enabled
        if (SkinsRestorerHook.isEnabled()) {
            SkinsRestorerAPI api = SkinsRestorerAPI.getApi();
            url = url + (api.hasSkin(player.getName()) ? api.getSkinName(player.getName()) : player.getName());
        } else {
            url = url + (player.getName());
        }
        return url;
    }

    public static String generateOrderId(Player player){
        return generateOrderId(player.getName());
    }

    public static String generateOrderId(String customerName){
        return customerName + "_" + UUID.randomUUID();
    }

}
