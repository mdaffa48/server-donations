package com.muhammaddaffa.serverdonations;

import com.muhammaddaffa.serverdonations.configs.ConfigManager;
import com.muhammaddaffa.serverdonations.configs.ConfigValue;
import com.muhammaddaffa.serverdonations.database.SQLDatabaseInitializer;
import com.muhammaddaffa.serverdonations.hooks.SkinsRestorerHook;
import com.muhammaddaffa.serverdonations.midtrans.SnapAPIRedirect;
import com.muhammaddaffa.serverdonations.products.ProductManager;
import me.aglerr.mclibs.MCLibs;
import me.aglerr.mclibs.libs.Common;
import me.aglerr.mclibs.libs.Debug;
import me.aglerr.mclibs.mysql.SQLHelper;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import spark.Spark;

public class ServerDonation extends JavaPlugin {

    private SnapAPIRedirect client;

    private final ProductManager productManager = new ProductManager();

    @Override
    public void onEnable(){
        // Initialize MCLibs
        MCLibs.init(this);
        Common.setPrefix("[ServerDonation]");
        // Initialize config and config value
        ConfigManager.init();
        ConfigValue.init(this.getConfig());
        // Should we enable debug?
        if(ConfigValue.DEBUG){
            Debug.enable();
        }
        // Load products
        this.productManager.load();
        // Initialize hooks
        SkinsRestorerHook.init();
        // Initialize the database
        SQLDatabaseInitializer.getInstance().init(this);
        // Initialize Midtrans
        this.connectSnapAPI();

        this.registerCommands();
        this.registerListeners();
    }

    @Override
    public void onDisable(){
        // Close the Midtrans SnapAPIRedirect
        Spark.stop();
        // Close the database connection
        SQLHelper.close();
    }

    private void registerCommands() {

    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(this.client, this);
    }

    private void connectSnapAPI(){
        this.client = new SnapAPIRedirect(
                ConfigValue.SERVER_KEY,
                ConfigValue.CLIENT_KEY,
                ConfigValue.IS_PRODUCTION_MODE,
                this.productManager
        );
        this.client.handleNotification();
    }

}
