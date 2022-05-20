package com.muhammaddaffa.serverdonations.configs;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValue {

    public static boolean DEBUG;

    public static boolean IS_PRODUCTION_MODE;
    public static String CLIENT_KEY;
    public static String SERVER_KEY;

    public static int PORT;
    public static String PATH;

    public static boolean MYSQL_ENABLED;
    public static String MYSQL_HOST;
    public static int MYSQL_PORT;
    public static String MYSQL_DATABASE;
    public static String MYSQL_USER;
    public static String MYSQL_PASSWORD;
    public static boolean MYSQL_SSL;
    public static int MYSQL_WAIT_TIMEOUT;
    public static int MYSQL_MAX_LIFETIME;
    public static boolean MYSQL_KEY_RETRIEVAL;

    public static boolean USE_UUID;

    public static void init(FileConfiguration config){
        DEBUG = config.getBoolean("debug");

        IS_PRODUCTION_MODE = config.getBoolean("midtrans.production-mode");
        if (IS_PRODUCTION_MODE) {
            CLIENT_KEY = config.getString("midtrans.production.client-key");
            SERVER_KEY = config.getString("midtrans.production.server-key");
        } else {
            CLIENT_KEY = config.getString("midtrans.sandbox.client-key");
            SERVER_KEY = config.getString("midtrans.sandbox.server-key");
        }

        PORT = config.getInt("port", 80);
        PATH = config.getString("notification-path", "/payment/notification");

        MYSQL_ENABLED = config.getBoolean("mysql.enabled");
        MYSQL_HOST = config.getString("mysql.host");
        MYSQL_PORT = config.getInt("mysql.port");
        MYSQL_DATABASE = config.getString("mysql.database");
        MYSQL_USER = config.getString("mysql.user");
        MYSQL_PASSWORD = config.getString("mysql.password");
        MYSQL_SSL = config.getBoolean("mysql.useSSL");
        MYSQL_WAIT_TIMEOUT = config.getInt("mysql.hikari.waitTimeout");
        MYSQL_MAX_LIFETIME = config.getInt("mysql.hikari.maxLifetime");
        MYSQL_KEY_RETRIEVAL = config.getBoolean("mysql.hikari.publicKeyRetrieval");

        USE_UUID = config.getBoolean("use-uuid");
    }

}
