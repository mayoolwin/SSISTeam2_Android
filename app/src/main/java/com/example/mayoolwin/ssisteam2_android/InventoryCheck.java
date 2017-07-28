package com.example.mayoolwin.ssisteam2_android;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mayoolwin.ssisteam2_android.JSONParser;
import com.example.mayoolwin.ssisteam2_android.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by heng_ on 26/7/2017.
 */

public class InventoryCheck extends HashMap<String,String> implements Serializable {

    final static String host = "http://172.23.134.205/SSISTeam2/Classes/WebServices/Service.svc";

    private String itemCode;
    private String itemDescription;
    private String categoryName;
    private int currentQuantity;
    private int actualQuantity;
    private String reason;

    public InventoryCheck(String itemCode, String itemDescription, String categoryName, String currentQuantity,
                        String actualQuantity, String reason) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.categoryName = categoryName;
        this.currentQuantity = Integer.parseInt(currentQuantity);
        this.actualQuantity = Integer.parseInt((actualQuantity));
        this.reason = reason;
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("itemCode", String.valueOf(itemCode));
        map.put("itemDescription", itemDescription);
        map.put("categoryName", categoryName);
        map.put("currentQuantity", String.valueOf(currentQuantity));
        map.put("actualQuantity", String.valueOf(actualQuantity));
        map.put("reason", reason);

        return map;
    }

    public static InventoryCheck fromHashMap(HashMap<String, String> map) {
        String itemCode = map.get("itemCode");
        String itemDescription = map.get("itemDescription");
        String categoryName = map.get("categoryName");
        String currentQuantity = map.get("currentQuantity");
        String actualQuantity = map.get("actualQuantity");
        String reason = map.get("reason");

        InventoryCheck inventoryCheck = new InventoryCheck(itemCode, itemDescription, categoryName, currentQuantity, actualQuantity, reason);
        return  inventoryCheck;
    }

    public static ArrayList<InventoryCheck> listInventoryCheck() {
        ArrayList<InventoryCheck> list = new ArrayList<InventoryCheck>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/InventoryCheck");

            for (int i=0; i<a.length(); i++) {
                JSONObject c = a.getJSONObject(i);
                InventoryCheck inventoryCheck = new InventoryCheck(c.getString("itemCode"),
                        c.getString("itemDescription"),
                        c.getString("categoryName"),
                        c.getString("currentQuantity"),
                        c.getString("actualQuantity"),
                        c.getString("reason"));
                list.add(inventoryCheck);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static InventoryCheck fromJSONObject(JSONObject jsonObject) throws JSONException{
        InventoryCheck inventoryCheck = new InventoryCheck(jsonObject.getString("itemCode"),
                jsonObject.getString("itemDescription"),
                jsonObject.getString("categoryName"),
                jsonObject.getString("currentQuantity"),
                jsonObject.getString("actualQuantity"),
                jsonObject.getString("reason"));

        return inventoryCheck;
    }

    public static ArrayList<InventoryCheck> fromJSONString(String JSONString) throws JSONException{

        ArrayList<InventoryCheck> inventoryChecks = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(JSONString);
        for (int i = 0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            InventoryCheck inventoryCheck = InventoryCheck.fromJSONObject(jsonObject);
            inventoryChecks.add((inventoryCheck));
        }

        return inventoryChecks;
    }

    public static InventoryCheck getInventoryCheck(String id) {
        InventoryCheck inventoryCheck = null;
        try {
            JSONObject c = JSONParser.getJSONFromUrl(host+"/InventoryCheck/"+id);
            inventoryCheck = new InventoryCheck(c.getString("itemCode"),
                    c.getString("itemDescription"),
                    c.getString("categoryName"),
                    c.getString("currentQuantity"),
                    c.getString("actualQuantity"),
                    c.getString("reason"));

        } catch (Exception e) {
        }
        return inventoryCheck;
    }

    public static void UpdateInventoryChecks(ArrayList<InventoryCheck> inventoryChecks) {
        JSONArray jInventoryChecks = toJSONArrayString(inventoryChecks);
        String result = JSONParser.postStream(host + "/InventoryCheck/Update", jInventoryChecks.toString());
    }

    public static JSONArray toJSONArrayString (ArrayList<InventoryCheck> inventoryChecks) {
        JSONArray jInventoryChecks = new JSONArray();
        for (InventoryCheck inventoryCheck: inventoryChecks
             ) {
            JSONObject jInventoryCheck = new JSONObject();
            try {
                jInventoryCheck.put("itemCode", inventoryCheck.getItemCode());
                jInventoryCheck.put("itemDescription", inventoryCheck.getItemDescription());
                jInventoryCheck.put("categoryName", inventoryCheck.getCategoryName());
                jInventoryCheck.put("currentQuantity", inventoryCheck.getCurrentQuantity());
                jInventoryCheck.put("actualQuantity", inventoryCheck.getActualQuantity());
                jInventoryCheck.put("reason", inventoryCheck.getReason());
                jInventoryChecks.put(jInventoryCheck);
            } catch (JSONException e) {
            }
        }
        return jInventoryChecks;
    }

    public static String getJsonStringFromUrl(String url) throws JSONException {
        StringBuilder b = new StringBuilder();
        HttpURLConnection connection = null;
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();

            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);

            connection.connect();

            InputStream ins = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line;
            while ((line = reader.readLine()) != null) {
                b.append(line);
                b.append('\n');
            }

            return b.toString();
        } catch (Exception e) {
            System.err.println(e.toString());
            e.printStackTrace();
            return null;
        } finally {
            assert connection != null;
            connection.disconnect();
        }
    }

    public static String getHost() {
        return host;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(int currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public int getActualQuantity() {
        return actualQuantity;
    }

    public void setActualQuantity(int actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
