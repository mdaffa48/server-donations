package com.muhammaddaffa.serverdonations.database;

import com.muhammaddaffa.serverdonations.configs.ConfigValue;
import me.aglerr.mclibs.hikaricp.HikariConfig;
import me.aglerr.mclibs.libs.Common;
import me.aglerr.mclibs.mysql.HikariConfigBuilder;
import me.aglerr.mclibs.mysql.SQLHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SQLDatabaseInitializer {

    private static final SQLDatabaseInitializer instance = new SQLDatabaseInitializer();

    public static final String TRANSACTION_TABLE = "serverdonations_transaction";
    public static final String USER_TABLE = "serverdonations_user";

    public static SQLDatabaseInitializer getInstance() {
        return instance;
    }

    public void init(JavaPlugin plugin) {
        HikariConfig config;
        HikariConfigBuilder builder = new HikariConfigBuilder()
                .setPoolName("ServerDonation Pool")
                .setWaitTimeout(ConfigValue.MYSQL_WAIT_TIMEOUT)
                .setMaxLifetime(ConfigValue.MYSQL_MAX_LIFETIME)
                .setPublicKeyRetrieval(ConfigValue.MYSQL_KEY_RETRIEVAL);

        if (ConfigValue.MYSQL_ENABLED) {
            config = builder
                    .setHost(ConfigValue.MYSQL_HOST)
                    .setDatabase(ConfigValue.MYSQL_DATABASE)
                    .setUser(ConfigValue.MYSQL_USER)
                    .setPassword(ConfigValue.MYSQL_PASSWORD)
                    .setPort(ConfigValue.MYSQL_PORT)
                    .setUseSSL(ConfigValue.MYSQL_SSL)
                    .buildMysql();
        } else {
            config = builder
                    .setPath("plugins/ServerDonations/database.db")
                    .buildSQLite();
        }

        if (!SQLHelper.createConnection(ConfigValue.MYSQL_ENABLED, "plugins/ServerDonations/database.db", config)) {
            Common.log("Couldn't connect to the database", "Make sure all information is correct!");
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }

        this.createTransactionTable();
        this.createUserTable();
    }

    private void createTransactionTable() {
        SQLHelper.executeUpdate("CREATE TABLE IF NOT EXISTS `" + TRANSACTION_TABLE + "` (" +
                "customer_name TEXT, " +
                "order_id TEXT, " +
                "product TEXT, " +
                "status TEXT, " +
                "PRIMARY KEY (order_id)" +
                ");"
        );
    }

    private void createUserTable() {
        SQLHelper.executeUpdate("CREATE TABLE IF NOT EXISTS `" + USER_TABLE + "` (" +
                "name VARCHAR(255), " +
                "total BIGINT, " +
                "PRIMARY KEY (name)" +
                ");"
        );
    }

}
