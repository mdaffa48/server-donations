package com.muhammaddaffa.serverdonations.transactions;

import com.muhammaddaffa.serverdonations.database.SQLDatabaseInitializer;
import com.muhammaddaffa.serverdonations.products.ProductManager;
import me.aglerr.mclibs.mysql.SQLHelper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class TransactionManager {

    private final Executor executor = Executors.newSingleThreadExecutor();

    private final ProductManager productManager;

    public TransactionManager(ProductManager productManager) {
        this.productManager = productManager;
    }

    public CompletableFuture<Transaction> getTransactionByCustomerName(String customerName) {
        return CompletableFuture.supplyAsync(() -> {
            AtomicReference<Transaction> transaction = null;
            SQLHelper.executeQuery(this.getQuery(SearchBy.CUSTOMER_NAME, customerName), resultSet -> {
                if (resultSet.next()) {
                    transaction.set(new Transaction(
                            resultSet.getString("customer_name"),
                            resultSet.getString("order_id"),
                            this.productManager.getProductByKey(resultSet.getString("product"))
                    ));
                }
            });

            return transaction.get();
        }, this.executor);
    }

    public CompletableFuture<Transaction> getTransactionByOrderId(String orderId) {
        return CompletableFuture.supplyAsync(() -> {
            AtomicReference<Transaction> transaction = null;
            SQLHelper.executeQuery(this.getQuery(SearchBy.ORDER_ID, orderId), resultSet -> {
                if (resultSet.next()) {
                    transaction.set(new Transaction(
                            resultSet.getString("customer_name"),
                            resultSet.getString("order_id"),
                            this.productManager.getProductByKey(resultSet.getString("product"))
                    ));
                }
            });

            return transaction.get();
        }, this.executor);
    }

    private String getQuery(SearchBy type, String value) {
        return "SELECT * FROM `" + SQLDatabaseInitializer.TRANSACTION_TABLE + "` WHERE " +
                SearchBy.getColumnName(type) + "=\"" + value + "\";";
    }

    private enum SearchBy {
        CUSTOMER_NAME,
        ORDER_ID;

        public static String getColumnName(SearchBy type){
            return type.name().toLowerCase();
        }

    }

}
