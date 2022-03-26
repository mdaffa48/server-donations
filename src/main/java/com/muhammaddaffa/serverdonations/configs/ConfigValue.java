package com.muhammaddaffa.serverdonations.configs;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValue {

    public static boolean DEBUG;

    public static boolean IS_PRODUCTION_MODE;
    public static String PRODUCTION_CLIENT_KEY;
    public static String PRODUCTION_SERVER_KEY;
    public static String SANDBOX_CLIENT_KEY;
    public static String SANDBOX_SERVER_KEY;

    public static int PORT;
    public static String PATH;

    public static void init(FileConfiguration config){
        DEBUG = config.getBoolean("debug");

        IS_PRODUCTION_MODE = config.getBoolean("midtrans.production-mode");
        PRODUCTION_CLIENT_KEY = config.getString("midtrans.production.client-key");
        PRODUCTION_SERVER_KEY = config.getString("midtrans.production.server-key");
        SANDBOX_CLIENT_KEY = config.getString("midtrans.sandbox.client-key");
        SANDBOX_SERVER_KEY = config.getString("midtrans.sandbox.server-key");

        PORT = config.getInt("port", 80);
        PATH = config.getString("notification-path", "/payment/notification");
    }

}
