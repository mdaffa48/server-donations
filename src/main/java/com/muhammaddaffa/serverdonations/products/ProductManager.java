package com.muhammaddaffa.serverdonations.products;

import com.muhammaddaffa.serverdonations.configs.ConfigManager;
import me.aglerr.mclibs.libs.Common;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ProductManager {

    private final Map<String, Product> productMap = new HashMap<>();

    @Nullable
    public Product getProduct(String key) {
        return this.productMap.get(key);
    }

    public Set<String> getAllProducts() {
        return this.productMap.keySet();
    }

    public void load() {
        // Clear the product first
        this.productMap.clear();

        FileConfiguration config = ConfigManager.PRODUCTS.getConfig();
        if (!config.isConfigurationSection("products")) {
            return;
        }

        long start = System.currentTimeMillis();
        Common.log("&eStarting to load all products...");

        for (String key : config.getConfigurationSection("products").getKeys(false)) {
            String name = config.getString("products." + key + ".product-name");
            String displayName = config.getString("products." + key + ".display-name");
            int price = config.getInt("products." + key + ".price");
            List<String> commands = config.getStringList("products." + key + ".commands");

            this.productMap.put(key, new ProductBuilder()
                            .setName(name)
                            .setKey(key)
                            .setDisplayName(displayName)
                            .setPrice(price)
                            .setCommands(commands)
                            .build()
            );

            Common.log("&aLoaded product '" + key + "'");
        }

        Common.log("&eAll products has been loaded (took {ms}ms)"
                .replace("{ms}", System.currentTimeMillis() - start + ""));
    }

}
