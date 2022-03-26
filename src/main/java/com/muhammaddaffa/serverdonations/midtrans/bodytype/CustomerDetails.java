package com.muhammaddaffa.serverdonations.midtrans.bodytype;

import java.util.HashMap;
import java.util.Map;

public class CustomerDetails {

    private final String name;

    public CustomerDetails(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Map<String, String> toMap(){
        final Map<String, String> customerDetails = new HashMap<>();
        customerDetails.put("first_name", this.name);
        return customerDetails;
    }
}
