package com.muhammaddaffa.serverdonations.midtrans;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import com.muhammaddaffa.serverdonations.midtrans.body.RequestBodyBuilder;
import org.bukkit.entity.Player;
import org.eclipse.jetty.http.HttpStatus;
import spark.Spark;

import java.util.*;

public class SnapAPIRedirect {

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

            System.out.println(request);
            System.out.println(response);

            return HttpStatus.OK_200;
        }));
    }

    public void stopReceivingNotifications(){
        Spark.stop();
    }

    private String generateOrderId(Player player){
        return player.getName() + "_" + UUID.randomUUID();
    }

}
