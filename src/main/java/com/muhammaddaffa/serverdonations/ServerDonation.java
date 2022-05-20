package com.muhammaddaffa.serverdonations;

import com.muhammaddaffa.serverdonations.commands.InvoiceCommand;
import com.muhammaddaffa.serverdonations.configs.ConfigManager;
import com.muhammaddaffa.serverdonations.configs.ConfigValue;
import com.muhammaddaffa.serverdonations.midtrans.SnapAPIRedirect;
import me.aglerr.mclibs.MCLibs;
import me.aglerr.mclibs.libs.Common;
import me.aglerr.mclibs.libs.Debug;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerDonation extends JavaPlugin {

    private SnapAPIRedirect client;

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

        // Initialize Midtrans
        this.connectSnapAPI();

        this.getCommand("invoice").setExecutor(new InvoiceCommand(this.client));
    }

    @Override
    public void onDisable(){
        this.client.stopReceivingNotifications();
    }

    protected void connectSnapAPI(){
        this.client = new SnapAPIRedirect(
                ConfigValue.SERVER_KEY,
                ConfigValue.CLIENT_KEY,
                ConfigValue.IS_PRODUCTION_MODE
        );
        this.client.startReceivingNotifications();
    }

}
