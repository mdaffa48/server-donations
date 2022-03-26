package com.muhammaddaffa.serverdonations.midtrans.helpers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonObjectWrapper {

    private final JsonObject json;

    public JsonObjectWrapper(JsonObject json){
        this.json = json;
    }

    public JsonObjectWrapper(String request){
        this.json = JsonParser.parseString(request).getAsJsonObject();
    }

    public JsonObject getJson(){
        return json;
    }

    public String getString(String key){
        return this.json.getAsJsonPrimitive(key).getAsString();
    }

    public int getInt(String key){
        return this.json.getAsJsonPrimitive(key).getAsInt();
    }

    public boolean getBoolean(String key){
        return this.json.getAsJsonPrimitive(key).getAsBoolean();
    }

}
