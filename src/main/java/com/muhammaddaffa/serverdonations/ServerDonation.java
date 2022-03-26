package com.muhammaddaffa.serverdonations;

import com.muhammaddaffa.serverdonations.commands.InvoiceCommand;
import com.muhammaddaffa.serverdonations.configs.ConfigManager;
import com.muhammaddaffa.serverdonations.configs.ConfigValue;
import com.muhammaddaffa.serverdonations.midtrans.SnapAPIRedirect;
import me.aglerr.mclibs.MCLibs;
import me.aglerr.mclibs.libs.Common;
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

        if(ConfigValue.IS_PRODUCTION_MODE){
            this.client = new SnapAPIRedirect(
                    ConfigValue.PRODUCTION_SERVER_KEY,
                    ConfigValue.PRODUCTION_CLIENT_KEY,
                    true
            );
        } else {
            this.client = new SnapAPIRedirect(
                    ConfigValue.SANDBOX_SERVER_KEY,
                    ConfigValue.SANDBOX_CLIENT_KEY,
                    false
            );
        }
        this.client.startReceivingNotifications();

        this.getCommand("invoice").setExecutor(new InvoiceCommand(this.client));
    }

    @Override
    public void onDisable(){
        this.client.stopReceivingNotifications();
    }

    public SnapAPIRedirect getClient() {
        return client;
    }

}
