package com.muhammaddaffa.serverdonations.products;

import com.muhammaddaffa.serverdonations.configs.ConfigValue;
import com.muhammaddaffa.serverdonations.utils.ImageChar;
import com.muhammaddaffa.serverdonations.utils.ImageMessage;
import com.muhammaddaffa.serverdonations.utils.Utils;
import me.aglerr.mclibs.libs.Common;
import me.aglerr.mclibs.libs.Executor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public record Product(
        String key,
        String name,
        String displayName,
        int price,
        List<String> commands
) {

    public void broadcast(Player player) {
        Executor.async(() -> {
            try {

                if (ConfigValue.USE_AVATAR_MESSAGE) {
                    URL url = new URL(Utils.getMinepicURL(player));
                    BufferedImage image = ImageIO.read(url);

                    String[] additional = new String[]{
                            this.parse(player, ConfigValue.AVATAR_LINE1),
                            this.parse(player, ConfigValue.AVATAR_LINE2),
                            this.parse(player, ConfigValue.AVATAR_LINE3),
                            this.parse(player, ConfigValue.AVATAR_LINE4),
                            this.parse(player, ConfigValue.AVATAR_LINE5),
                            this.parse(player, ConfigValue.AVATAR_LINE6),
                            this.parse(player, ConfigValue.AVATAR_LINE7),
                            this.parse(player, ConfigValue.AVATAR_LINE8)
                    };

                    // Finally, send the message
                    new ImageMessage(image, 8, ImageChar.BLOCK.getChar())
                            .appendText(additional)
                            .sendMessage();
                } else {

                    // Normal messages
                    ConfigValue.NORMAL_MESSAGES.forEach(message ->
                            Bukkit.broadcastMessage(Common.color(message)));
                }

                // Run all commands
                Executor.sync(() -> {
                    for (String command : this.commands()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.parse(player, command));
                    }
                });

            } catch (IOException ex) {
                Common.log("&cFailed to send broadcast donation");
                ex.printStackTrace();
            }
        });
    }

    private String parse(Player player, String message) {
        return message
                .replace("{player}", player.getName())
                .replace("{product_name}", this.displayName())
                .replace("{product_price}", Utils.formatNumber(this.price()));
    }

}
