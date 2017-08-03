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

    public FileDiscrepancy(String itemCode,String itemName, String retrievedQty, String disbursedQty, String adjustQty, String reason) {
        this.put("ItemCode", itemCode);
        this.put("ItemName", itemName);
        this.put("RetrievedQty", retrievedQty);
        this.put("DisbursedQty", disbursedQty);
        this.put("AdjustedQty", adjustQty);
        this.put("Reason", reason);
    }

    public static ArrayList<FileDiscrepancy> FromDisbursement(ArrayList<HashMap<String, String>> mapsList) {
        ArrayList<FileDiscrepancy> list = new ArrayList<>();
        for (HashMap<String, String> map: mapsList
             ) {
            String itemCode = map.get("itemCode");
            String itemName = map.get("itemName");
            String disburseQty = map.get("disbursedQty");
            String retrievedQty = map.get("retrievedQty");
            int retrievedInt = Integer.parseInt(retrievedQty);
            int disbursedInt = Integer.parseInt(disburseQty);
            int adjustedInt = disbursedInt - retrievedInt;
            String adjustedQty = String.valueOf(adjustedInt);
            FileDiscrepancy fileDiscrepancy = new FileDiscrepancy(itemCode, itemName, retrievedQty, disburseQty, adjustedQty, "");
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
