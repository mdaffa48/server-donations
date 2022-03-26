package com.muhammaddaffa.serverdonations.midtrans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import com.muhammaddaffa.serverdonations.midtrans.body.RequestBodyBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.eclipse.jetty.http.HttpStatus;
import org.json.JSONObject;
import spark.Spark;

import java.beans.PersistenceDelegate;
import java.util.*;

public class SnapAPIRedirect {

    private final Gson gson = new Gson();
    private final MidtransSnapApi snapAPI;

    public SnapAPIRedirect(String serverKey, String clientKey, boolean production){
        this.snapAPI = new ConfigFactory(
                new Config(serverKey, clientKey, production)
        ).getSnapApi();
    }

    public String generateInvoiceURL(Player player, int amount) throws MidtransError{
        return this.snapAPI.createTransactionRedirectUrl(
                this.requestBody(player, amount)
        );
    }

    private Map<String, Object> requestBody(Player player, int amount){
        return new RequestBodyBuilder()
                .setTransactionDetails(this.generateOrderId(player), amount)
                .setCustomerDetails(player.getName())
                .addItemDetail(amount, 1, "1000 Coins")
                .build();
    }

    public void startReceivingNotifications(){
        Spark.get("/midtrans/notification", ((request, response) -> {

            JSONObject json = this.gson.fromJson(request.body(), JSONObject.class);

            String orderId = json.getString("order_id");

            String transactionStatus = json.getString("transaction_status");
            String fraudStatus = json.getString("fraud_status");
            String statusCode = json.getString("status_code");

            if(transactionStatus.equals("capture") &&
                    fraudStatus.equals("accept") &&
                    statusCode.equals("200")){

                Bukkit.broadcastMessage(orderId + " telah dibayar!");
            }

            response.status(200);
            return "";
        }));
    }

    public void stopReceivingNotifications(){
        Spark.stop();
    }

    private String generateOrderId(Player player){
        String orderId = player.getName() + "_" + UUID.randomUUID();
        Bukkit.broadcastMessage("Order ID: " + orderId);
        return orderId;
    }

}
