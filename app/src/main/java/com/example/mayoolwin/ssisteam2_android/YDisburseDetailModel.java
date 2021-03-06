package com.example.mayoolwin.ssisteam2_android;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by Y on 27/07/2017.
 */

public class YDisburseDetailModel extends java.util.HashMap<String,String> implements Serializable {

    String  disbursedQty,itemCode,itemName,retrievedQty;

    public YDisburseDetailModel(String disbursedQty,String itemCode, String itemName, String retrievedQty) {
        put("disbursedQty", disbursedQty);
        put("itemCode",itemCode);
        put("itemName", itemName);
        put("retrievedQty", retrievedQty);
        this.disbursedQty = disbursedQty;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.retrievedQty=retrievedQty;
    }
    public YDisburseDetailModel(){}

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("disbursedQty", this.get("disbursedQty"));
        map.put("itemCode", this.get("itemCode"));
        map.put("itemName", this.get("itemName"));
        map.put("retrievedQty", this.get("retrievedQty"));
        return map;
    }

    public String getDisbursedQty(){return disbursedQty;};
    public String getItemCode(){return itemCode;};
    public String getItemName(){return itemName;};
    public String getRetrievedQty(){return retrievedQty;};

    public  static List<YDisburseDetailModel> listDisDeptDetail(String user, String deptCode)
    {
        List<YDisburseDetailModel> yDisburseLsit = new ArrayList<YDisburseDetailModel>();

        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/DisbDeptDetail/" + user + "/" + deptCode);


                for(int i=0; i<a.length(); i++)
                {
                    JSONObject jObj = a.getJSONObject(i);
                    YDisburseDetailModel obj = new YDisburseDetailModel (jObj.getString("disbursedQty"),
                                                                        jObj.getString("itemCode"),
                                                                         jObj.getString("itemName"),
                                                                        jObj.getString("retrievedQty")
                    );
                    yDisburseLsit.add(obj);

               }

        }catch (JSONException e)
        {
            Log.e("JSON ARRAY error",e.toString());
        }
        Log.v("mg","***"+yDisburseLsit);
        return  yDisburseLsit;
    }

    public  static void UpdateDisburseQty(List<YDisburseDetailModel> objList, String loginUserName, String deptCode)
    {

        JSONArray jsonArray = new JSONArray();


        try {

            for(YDisburseDetailModel eachObj : objList )
            {
                JSONObject jObj = new JSONObject();
                jObj.put("disbursedQty", eachObj.get("disbursedQty"));
                jObj.put("itemCode", eachObj.get("itemCode"));
                jObj.put("itemName",eachObj.get("itemName"));
                jObj.put("retrievedQty", eachObj.get("retrievedQty"));
                jsonArray.put(jObj);
            }

            Log.v("MG","***"+jsonArray);
            String result = JSONParser.postStream(host+"/DisburseTQty/Update/"+loginUserName+"/"+deptCode, jsonArray.toString());


        }catch (JSONException e)
        {

        }

    }


}
