package com.muhammaddaffa.serverdonations.configs;

import me.aglerr.mclibs.libs.CustomConfig;

public class ConfigManager {

    public static CustomConfig CONFIG;
    public static CustomConfig PRODUCTS;

    public static void init(){
        CONFIG = new CustomConfig("config.yml", null);
        PRODUCTS = new CustomConfig("products.yml", null);
    }

    public static void reload(){
        CONFIG.reloadConfig();
        PRODUCTS.reloadConfig();
    }

}
