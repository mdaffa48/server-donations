package com.muhammaddaffa.serverdonations.midtrans;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SnapAPIRedirect {

    private final MidtransSnapApi snapAPI;

    public SnapAPIRedirect(String serverKey, String clientKey, boolean production){
        this.snapAPI = new ConfigFactory(
                new Config(serverKey, clientKey, production)
        ).getSnapApi();
    }

    public String generateInvoiceURL(Player player, int amount){
        try{

            return this.snapAPI.createTransactionRedirectUrl(
                    this.requestBody(player, amount)
            );

        } catch (MidtransError ex){
            ex.printStackTrace();
            return null;
        }
    }

    private Map<String, Object> requestBody(Player player, int amount){
        Map<String, Object> params = new HashMap<>();

        Map<String, String> transactionDetails = new HashMap<>();
        transactionDetails.put("order_id", this.generateOrderId(player));
        transactionDetails.put("gross_amount", amount + "");

        params.put("transaction_details", transactionDetails);

        return params;
    }

    private String generateOrderId(Player player){
        return "midtrans_" + player.getName() + "_random=" + UUID.randomUUID();
    }

}
