package com.muhammaddaffa.serverdonations.midtrans;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import com.muhammaddaffa.serverdonations.configs.ConfigValue;
import com.muhammaddaffa.serverdonations.database.SQLDatabaseInitializer;
import com.muhammaddaffa.serverdonations.midtrans.helpers.JsonObjectWrapper;
import com.muhammaddaffa.serverdonations.midtrans.helpers.RequestBodyBuilder;
import com.muhammaddaffa.serverdonations.products.Product;
import com.muhammaddaffa.serverdonations.products.ProductManager;
import com.muhammaddaffa.serverdonations.transactions.OrderId;
import com.muhammaddaffa.serverdonations.transactions.TransactionTracker;
import me.aglerr.mclibs.libs.Common;
import me.aglerr.mclibs.libs.Debug;
import me.aglerr.mclibs.libs.Executor;
import me.aglerr.mclibs.mysql.SQLHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;
import spark.Spark;

import java.sql.ResultSet;
import java.util.*;

public class SnapAPIRedirect implements Listener {

    private final MidtransSnapApi snapAPI;
    private final ProductManager productManager;
    private final TransactionTracker tracker;

    private final Map<String, Product> pendingDonations = new HashMap<>();

    public SnapAPIRedirect(String serverKey, String clientKey, boolean production,
                           ProductManager productManager, TransactionTracker tracker) {
        this.snapAPI = new ConfigFactory(new Config(serverKey, clientKey, production))
                .getSnapApi();
        this.productManager = productManager;
        this.tracker = tracker;
    }

    public String generateInvoiceURL(@NotNull Player player, @NotNull Product product) {
        try {
            String orderId = OrderId.generate(player);
            String query = "INSERT INTO `" + SQLDatabaseInitializer.TRANSACTION_TABLE + "` " +
                    "(customer_name, order_id, product, status) " +
                    "VALUES (?, ?, ?, ?);";

            SQLHelper.buildStatement(query, statement -> {
                statement.setString(1, player.getName());
                statement.setString(2, orderId);
                statement.setString(3, product.key());
                statement.setString(4, "UNPAID");

                statement.execute();

            }, ex -> {
                Common.log("&cFailed to generate Redirect URL");
                ex.printStackTrace();
            });

            Map<String, Object> body = new RequestBodyBuilder()
                    .setTransactionDetails(orderId, product)
                    .setCustomerDetails(player.getName())
                    .addItemDetail(product)
                    .build();

            return this.snapAPI.createTransactionRedirectUrl(body);

        } catch (MidtransError ex) {
            Common.log("&cCouldn't generate invoice URL");
            ex.printStackTrace();
        }
        return null;
    }

    public void handleNotification() {
        Spark.port(ConfigValue.PORT);
        Spark.init();

        Spark.post(ConfigValue.PATH, ((request, response) -> {
            JsonObjectWrapper wrapper = new JsonObjectWrapper(request.body());
            // Successfully getting notification data
            response.status(200);

            String orderId = wrapper.getString("order_id");
            String transactionStatus = wrapper.getString("transaction_status");
            String fraudStatus = wrapper.getString("fraud_status");
            String statusCode = wrapper.getString("status_code");

            Debug.send(
                    " ",
                    "&e-- Midtrans Notification --",
                    "&eOrder ID: " + orderId,
                    "&eTransaction Details: " + transactionStatus,
                    "&eFraud Status: " + fraudStatus,
                    "&eStatus Code: " + statusCode,
                    " "
            );

            if (this.isStatusGood(transactionStatus) &&
                    fraudStatus.equals("accept") &&
                    statusCode.equals("200")) {

                Executor.async(() -> {
                    String query = "SELECT * FROM `" + SQLDatabaseInitializer.TRANSACTION_TABLE + "` " +
                            "WHERE order_id=?";

                    SQLHelper.buildStatement(query, statement -> {
                        statement.setString(1, orderId);

                        ResultSet result = statement.executeQuery();
                        if (result.next()) {
                            String playerName = result.getString("customer_name");
                            String productKey = result.getString("product");

                            Player player = Bukkit.getPlayer(playerName);
                            Product product = this.productManager.getProduct(productKey);

                            this.tracker.remove(playerName);
                            this.updateTransaction(orderId);

                            if (player == null) {
                                this.pendingDonations.put(playerName, product);
                                return;
                            }

                            if (product != null) {
                                product.broadcast(player);
                                this.updateUser(player, product);
                            }
                        }
                    }, ex -> {
                        Common.log("&cFailed to find transaction with order id (" + orderId + ")");
                        ex.printStackTrace();
                    });
                });

            }

            return "";
        }));
    }

    @EventHandler()
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Product product = this.pendingDonations.remove(player.getName());

        Executor.async(() -> {
            if (product != null) {
                product.broadcast(player);

                this.updateUser(player, product);
            }
        });
    }

    private void updateTransaction(String orderId) {
        String query = "UPDATE `" + SQLDatabaseInitializer.TRANSACTION_TABLE + "` " +
                "SET `status`=? " +
                "WHERE `order_id`=?;";

        SQLHelper.buildStatement(query, statement -> {
            statement.setString(1, "PAID");
            statement.setString(2, orderId);

            statement.execute();

        }, ex -> {
            Common.log("&cFailed to set transaction status");
            ex.printStackTrace();
        });
    }

    private void updateUser(Player player, Product product) {
        String query = "SELECT * FROM `" + SQLDatabaseInitializer.USER_TABLE + "` " +
                "WHERE `name`=?";

        SQLHelper.buildStatement(query, statement -> {
            statement.setString(1, player.getName());

            ResultSet result = statement.executeQuery();
            int total = product.price();

            if (result.next()) {
                total += result.getInt("total");
            }

            String updateQuery = "REPLACE INTO `" + SQLDatabaseInitializer.USER_TABLE + "` " +
                    "SET `total`=? " +
                    "WHERE `name`=?;";

            final int finalTotal = total;

            SQLHelper.buildStatement(updateQuery, updateStatement -> {
                updateStatement.setInt(1, finalTotal);
                updateStatement.setString(2, player.getName());

                updateStatement.execute();

            }, ex -> {
                Common.log("&cFailed to update '" + player.getName() + "' total donation");
                ex.printStackTrace();
            });

        }, ex -> {
        });
    }

    private boolean isStatusGood(String status) {
        return status.equals("capture") || status.equals("settlement");
    }

}
