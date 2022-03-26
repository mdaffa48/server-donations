package com.muhammaddaffa.serverdonations.commands;

import com.midtrans.httpclient.error.MidtransError;
import com.muhammaddaffa.serverdonations.midtrans.SnapAPIRedirect;
import com.muhammaddaffa.serverdonations.products.Product;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
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
        Player player = (Player) sender;

        try {
            Product product = new Product("one-thousand-coins", "One Thousand Coins", 5000);
            BaseComponent[] components = new ComponentBuilder("[Pay Here]")
                    .color(ChatColor.YELLOW)
                    .bold(true)
                    .event(new ClickEvent(ClickEvent.Action.OPEN_URL, client.generateInvoiceURL(player, product)))
                    .create();

            player.spigot().sendMessage(components);

        } catch (MidtransError ex){
            ex.printStackTrace();
        }
        return true;
    }

}
