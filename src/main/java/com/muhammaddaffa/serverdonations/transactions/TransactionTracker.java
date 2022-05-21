package com.muhammaddaffa.serverdonations.transactions;

import java.util.HashSet;
import java.util.Set;

public class TransactionTracker {

    private final Set<String> playerSet = new HashSet<>();

    public boolean hasPendingOrder(String name) {
        return this.playerSet.contains(name);
    }

    public void add(String name) {
        this.playerSet.add(name);
    }

    public void remove(String name) {
        this.playerSet.remove(name);
    }

}
