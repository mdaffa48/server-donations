package com.muhammaddaffa.serverdonations.configs;

import org.bukkit.configuration.file.FileConfiguration;

import javax.jnlp.FileContents;

public class ConfigValue {

    public static String PRODUCTION_CLIENT_KEY;
    public static String PRODUCTION_SERVER_KEY;

    public static String SANDBOX_CLIENT_KEY;
    public static String SANDBOX_SERVER_KEY;

    public static boolean IS_PRODUCTION_MODE;

    public static void init(FileConfiguration config){
        PRODUCTION_CLIENT_KEY = config.getString("production-client-key");
        PRODUCTION_SERVER_KEY = config.getString("production-server-key");

        SANDBOX_CLIENT_KEY = config.getString("sandbox-client-key");
        SANDBOX_SERVER_KEY = config.getString("sandbox-server-key");

        IS_PRODUCTION_MODE = config.getBoolean("production-mode");
    }

}
