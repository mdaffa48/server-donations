package com.muhammaddaffa.serverdonations.commands;

import com.muhammaddaffa.serverdonations.ServerDonation;
import com.muhammaddaffa.serverdonations.configs.ConfigValue;
import com.muhammaddaffa.serverdonations.midtrans.SnapAPIRedirect;
import com.muhammaddaffa.serverdonations.products.Product;
import com.muhammaddaffa.serverdonations.products.ProductManager;
import com.muhammaddaffa.serverdonations.transactions.TransactionTracker;
import com.muhammaddaffa.serverdonations.utils.Utils;
import me.aglerr.mclibs.libs.Common;
import me.aglerr.mclibs.libs.Executor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCommand implements TabExecutor {

    private final SnapAPIRedirect client;
    private final ProductManager productManager;
    private final ServerDonation plugin;
    private final TransactionTracker tracker;

    public MainCommand(SnapAPIRedirect client, ProductManager productManager, ServerDonation plugin,
                       TransactionTracker tracker) {
        this.client = client;
        this.productManager = productManager;
        this.plugin = plugin;
        this.tracker = tracker;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!sender.hasPermission("serverdonations.admin")) {
            Common.sendMessage(sender, ConfigValue.NO_PERMISSION);
            return true;
        }

        if (args.length == 0) {
            Common.sendMessage(sender, ConfigValue.HELP);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "send": {
                this.send(sender, args);
                break;
            }
            case "reload": {
                this.reload(sender);
                break;
            }
            default: {
                Common.sendMessage(sender, ConfigValue.HELP);
                break;
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("serverdonations.admin")) {
            return new ArrayList<>();
        }
        if (args.length == 1) {
            return Arrays.asList("send", "reload");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("send")) {
            return null;
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("send")) {
            return new ArrayList<>(this.productManager.getAllProducts());
        }
        return new ArrayList<>();
    }

    private void send(CommandSender sender, String[] args) {
        // /donation send (player) (product)
        if (args.length < 3) {
            Common.sendMessage(sender, "&cUsage: /donation send (player) (product)");
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            Common.sendMessage(sender, ConfigValue.INVALID_PLAYER);
            return;
        }
        Product product = this.productManager.getProduct(args[2]);
        if (product == null) {
            Common.sendMessage(sender, ConfigValue.INVALID_PRODUCT);
            return;
        }

        Common.sendMessage(sender, ConfigValue.SEND_DONATION
                .replace("{player}", player.getName())
                .replace("{product_name}", product.displayName()));

        if (this.tracker.hasPendingOrder(player.getName())) {
            Common.sendMessage(sender, ConfigValue.PENDING_DONATION);
            return;
        }

        this.tracker.add(player.getName());
        // Send invoice URL to the player
        Executor.async(() -> {
            String url = this.client.generateInvoiceURL(player, product);

            for (String message : ConfigValue.PAY_MESSAGES) {
                if (message.contains("{pay_button}")) {
                    TextComponent component = new TextComponent(TextComponent
                            .fromLegacyText(Common.color(ConfigValue.PAY_BUTTON)));
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));

                    player.spigot().sendMessage(component);
                    continue;
                }
                Common.sendMessage(player, message
                        .replace("{player}", player.getName())
                        .replace("{product_name}", product.displayName())
                        .replace("{product_price}", Utils.formatNumber(product.price())));
            }

        });
    }

    private void reload(CommandSender sender) {
        this.plugin.reloadEverything();
        Common.sendMessage(sender, ConfigValue.RELOAD);
    }
}
