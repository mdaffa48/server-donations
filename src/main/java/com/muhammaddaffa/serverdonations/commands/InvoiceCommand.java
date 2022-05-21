package com.muhammaddaffa.serverdonations.commands;

import com.midtrans.httpclient.error.MidtransError;
import com.muhammaddaffa.serverdonations.midtrans.SnapAPIRedirect;
import com.muhammaddaffa.serverdonations.products.Product;
import com.muhammaddaffa.serverdonations.products.ProductManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InvoiceCommand implements CommandExecutor {

    private final SnapAPIRedirect client;
    private final ProductManager productManager;

    public InvoiceCommand(SnapAPIRedirect client, ProductManager productManager) {
        this.client = client;
        this.productManager = productManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        // Command: /invoice (amount)
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        Product product = this.productManager.getProduct("vip");
        this.client.generateInvoiceURL(player, product).thenAccept(url ->
                player.sendMessage("Invoice: " + url));
        return true;
    }

}
