package com.muhammaddaffa.serverdonations.transactions;

public enum TransactionStatus {
    COMPLETED,
    PENDING,
    EXPIRED;

    public static TransactionStatus getStatusByString(String status){
        switch (status.toLowerCase()){
            case "completed": return COMPLETED;
            case "pending": return PENDING;
            case "expired": return EXPIRED;
        }
        return null;
    }

}
