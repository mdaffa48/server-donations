package com.muhammaddaffa.serverdonations.midtrans;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import com.muhammaddaffa.serverdonations.midtrans.helpers.JsonObjectWrapper;
import com.muhammaddaffa.serverdonations.midtrans.helpers.RequestBodyBuilder;
import com.muhammaddaffa.serverdonations.products.Product;
import com.muhammaddaffa.serverdonations.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import spark.Spark;

import java.util.*;

public class SnapAPIRedirect {

    private final Gson gson = new Gson();
    private final MidtransSnapApi snapAPI;

    public SnapAPIRedirect(String serverKey, String clientKey, boolean production) {
        this.snapAPI = new ConfigFactory(new Config(serverKey, clientKey, production))
                .getSnapApi();
    }

    public String generateInvoiceURL(Player player, Product product) throws MidtransError {
        return this.snapAPI
                .createTransactionRedirectUrl(this.requestBody(player, product));
    }

    private Map<String, Object> requestBody(Player player, Product product) {
        return new RequestBodyBuilder()
                .setTransactionDetails(Utils.generateOrderId(player), product)
                .setCustomerDetails(player.getName())
                .addItemDetail(product)
                .build();
    }

    public void startReceivingNotifications() {
        Spark.port(80);
        Spark.init();

        Spark.post("/payment/notification", ((request, response) -> {

            JsonObjectWrapper wrapper = new JsonObjectWrapper(request.body());

            // Successfully getting notification data
            response.status(200);

            String orderId = wrapper.getString("order_id");
            Bukkit.broadcastMessage("Order ID: " + orderId);
            String transactionStatus = wrapper.getString("transaction_status");
            String fraudStatus = wrapper.getString("fraud_status");
            String statusCode = wrapper.getString("status_code");

            Bukkit.broadcastMessage(" ");
            Bukkit.broadcastMessage("Midtrans Notification:");
            Bukkit.broadcastMessage("Order ID: " + orderId);
            Bukkit.broadcastMessage("Transaction Status: " + transactionStatus);
            Bukkit.broadcastMessage("Fraud Status: " + fraudStatus);
            Bukkit.broadcastMessage("Status Code: " + statusCode);
            Bukkit.broadcastMessage(" ");

            if (transactionStatus.equals("capture") &&
                    fraudStatus.equals("accept") &&
                    statusCode.equals("200")) {

                Bukkit.broadcastMessage(orderId + " telah dibayar!");
                Bukkit.getConsoleSender().sendMessage("broadcast &a&laglerr just donated to the server!");
            }

            response.status(200);
            return "";
        }));
    }

    public void stopReceivingNotifications() {
        Spark.stop();
    }

    private String generateOrderId(Player player) {
        String orderId = player.getName() + "_" + UUID.randomUUID();
        Bukkit.broadcastMessage("Order ID: " + orderId);
        return orderId;
    }

}
