package com.muhammaddaffa.serverdonations.commands;

import com.muhammaddaffa.serverdonations.midtrans.SnapAPIRedirect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvoiceCommand implements CommandExecutor {

    private final SnapAPIRedirect client;
    public InvoiceCommand(SnapAPIRedirect client){
        this.client = client;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Command: /invoice (amount)
        if(!(sender instanceof Player)){
            return true;
        }
        if(args.length < 1){
            sender.sendMessage("Usage: /invoice (amount)");
            return true;
        }
        Player player = (Player) sender;
        int amount = Integer.parseInt(args[0]);

        player.sendMessage("Your invoice URL: " + client.generateInvoiceURL(player, amount));
        return true;
    }

}
