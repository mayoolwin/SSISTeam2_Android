package com.example.mayoolwin.ssisteam2_android;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by heng_ on 31/7/2017.
 */

public class FileDiscrepancy extends HashMap<String, String> {

    public FileDiscrepancy(String itemName, String adjustedQty, String reason) {
        this.put("ItemName", itemName);
        this.put("AdjustedQty", adjustedQty);
        this.put("Reason", reason);
    }

    public static ArrayList<FileDiscrepancy> FromDisbursement(HashMap<String, Integer> map) {
        ArrayList<FileDiscrepancy> list = new ArrayList<>();
        for (Map.Entry<String, Integer> pair: map.entrySet()
             ) {
            String itemName = pair.getKey();
            String adjustedQty = String.valueOf(pair.getValue());
            FileDiscrepancy fileDiscrepancy = new FileDiscrepancy(itemName, adjustedQty, "");
            list.add(fileDiscrepancy);
        }
        return list;
    }


    public static JSONObject ToJSONObject(FileDiscrepancy fileDiscrepancy) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ItemName", fileDiscrepancy.get("ItemName"));
            jsonObject.put("AdjustedQty", fileDiscrepancy.get("AdjustedQty"));
            jsonObject.put("Reason", fileDiscrepancy.get("Reason"));
        } catch (JSONException e) {

        }
        return jsonObject;
    }

    public static JSONArray ToJSONArray(ArrayList<FileDiscrepancy> fileDiscrepancies) {

        JSONArray JSONArrayFile = new JSONArray();
        for (FileDiscrepancy file: fileDiscrepancies
             ) {
            JSONObject jsonFile = ToJSONObject(file);
            JSONArrayFile.put(jsonFile);
        }
        return JSONArrayFile;
    }

    public static void UpdateFileDiscrepancies(ArrayList<FileDiscrepancy> fileDiscrepancies, String username) {
        JSONArray jsonArrayFile = ToJSONArray(fileDiscrepancies);
        Log.e("dddCreatettt","REsult" + jsonArrayFile.toString());
        try {
            String result = JSONParser.postStream(host + "/FileDiscrepancies/Update/" + username, jsonArrayFile.toString());
            Log.e("ddd","REsult" + result);
        } catch (Exception e) {

        }
    }

}
