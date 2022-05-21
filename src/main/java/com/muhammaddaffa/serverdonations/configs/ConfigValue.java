package com.muhammaddaffa.serverdonations.configs;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

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

    public static boolean USE_AVATAR_MESSAGE;
    public static String AVATAR_HEADER;
    public static String AVATAR_LINE1;
    public static String AVATAR_LINE2;
    public static String AVATAR_LINE3;
    public static String AVATAR_LINE4;
    public static String AVATAR_LINE5;
    public static String AVATAR_LINE6;
    public static String AVATAR_LINE7;
    public static String AVATAR_LINE8;
    public static String AVATAR_FOOTER;

    public static List<String> NORMAL_MESSAGES;

    public static String PAY_BUTTON;
    public static List<String> PAY_MESSAGES;

    public static String NO_PERMISSION;
    public static String RELOAD;
    public static String INVALID_PRODUCT;
    public static String INVALID_PLAYER;
    public static String SEND_DONATION;
    public static List<String> HELP;

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

        USE_AVATAR_MESSAGE = config.getBoolean("use-avatar-message");
        AVATAR_HEADER = config.getString("avatar-messages.header");
        AVATAR_LINE1 = config.getString("avatar-messages.line1");
        AVATAR_LINE2 = config.getString("avatar-messages.line2");
        AVATAR_LINE3 = config.getString("avatar-messages.line3");
        AVATAR_LINE4 = config.getString("avatar-messages.line4");
        AVATAR_LINE5 = config.getString("avatar-messages.line5");
        AVATAR_LINE6 = config.getString("avatar-messages.line6");
        AVATAR_LINE7 = config.getString("avatar-messages.line7");
        AVATAR_LINE8 = config.getString("avatar-messages.line8");
        AVATAR_FOOTER = config.getString("avatar-messages.footer");

        NORMAL_MESSAGES = config.getStringList("normal-messages");

        PAY_BUTTON = config.getString("invoice-message.pay-button");
        PAY_MESSAGES = config.getStringList("invoice-message.messages");

        NO_PERMISSION = config.getString("messages.no-permission");
        RELOAD = config.getString("messages.reload");
        INVALID_PRODUCT = config.getString("messages.invalid-product");
        INVALID_PLAYER = config.getString("messages.invalid-player");
        SEND_DONATION = config.getString("messages.send-donation");
        HELP = config.getStringList("messages.help");
    }

}
