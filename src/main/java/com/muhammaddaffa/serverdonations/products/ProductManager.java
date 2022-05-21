package com.muhammaddaffa.serverdonations.products;

import com.muhammaddaffa.serverdonations.configs.ConfigManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManager {

    private final Map<String, Product> productMap = new HashMap<>();

    @Nullable
    public Product getProduct(String key) {
        return this.productMap.get(key);
    }

    public void load() {
        // Clear the product first
        this.productMap.clear();

        FileConfiguration config = ConfigManager.PRODUCTS.getConfig();
        if (!config.isConfigurationSection("products")) {
            return;
        }
        for (String key : config.getConfigurationSection("products").getKeys(false)) {
            String displayName = config.getString("products." + key + ".display-name");
            int price = config.getInt("products." + key + ".price");
            List<String> commands = config.getStringList("products." + key + ".commands");

            this.productMap.put(key, new ProductBuilder()
                            .setKey(key)
                            .setDisplayName(displayName)
                            .setPrice(price)
                            .setCommands(commands)
                            .build()
            );
        }
    }

}
