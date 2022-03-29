package com.muhammaddaffa.serverdonations.hooks;

import net.skinsrestorer.api.SkinsRestorerAPI;
import org.bukkit.Bukkit;

public class SkinsRestorerHook {

    private static boolean SKINS_RESTORER;
    private static SkinsRestorerAPI api;

    public static void init(){
        SKINS_RESTORER = Bukkit.getPluginManager().getPlugin("SkinsRestorer") != null;

        if(SKINS_RESTORER){
            api = SkinsRestorerAPI.getApi();
        }

    }

    public static boolean isSkinsRestorer(){
        return SKINS_RESTORER;
    }

    public static SkinsRestorerAPI getAPI(){
        return api;
    }

}
