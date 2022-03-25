package com.muhammaddaffa.serverdonations;

import com.muhammaddaffa.serverdonations.commands.InvoiceCommand;
import com.muhammaddaffa.serverdonations.midtrans.SnapAPIRedirect;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerDonation extends JavaPlugin {

    private final String SERVER_KEY = "SB-Mid-server-aIO51MbOFbDFABlBZjbO39nn";
    private final String CLIENT_KEY = "SB-Mid-client-_zlNhT1-oBF2TxAC";

    private SnapAPIRedirect client;

    @Override
    public void onEnable(){
        this.client = new SnapAPIRedirect(SERVER_KEY, CLIENT_KEY, false);
        this.getCommand("invoice").setExecutor(new InvoiceCommand(this.client));
    }

    public SnapAPIRedirect getClient() {
        return client;
    }

}
