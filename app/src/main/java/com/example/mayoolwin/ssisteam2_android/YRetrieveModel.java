package com.example.mayoolwin.ssisteam2_android;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

import static android.R.id.list;
import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by Y on 25/07/2017.
 */

public class YRetrieveModel extends java.util.HashMap<String,String>
{

     String  itemCode,itemDes,retrieveQty,totalQty;

    public YRetrieveModel( String itemCode,String itemDes, String retrieveQty,String totalQty)
    {
        put("itemCode", itemCode);
        put("itemDes", itemDes);
        put("retrieveQty",retrieveQty);
        put("totalQty", totalQty);
        this.itemCode = itemCode;
        this.itemDes = itemDes;
        this.retrieveQty = retrieveQty;
        this.totalQty = totalQty;
    }

    public String getItemCode() {
        return itemCode;
    }
    public String getItemDes() {
        return itemDes;
    }
    public String getRetrieveQty() {
        return retrieveQty;
    }
    public String getTotalQty() {return totalQty;}


    public HashMap<String, String> toHashMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("disbursedQty", this.get("totalQty"));
        map.put("itemCode", this.get("itemCode"));
        map.put("itemName", this.get("itemName"));
        map.put("retrievedQty", this.get("retrieveQty"));
        return map;
    }

    public YRetrieveModel(){}

    public  static ArrayList<YRetrieveModel> listEachItemTotalQty(String user)
    {
        ArrayList<YRetrieveModel> list = new ArrayList<YRetrieveModel>();
        try {
            JSONArray jAry = JSONParser.getJSONArrayFromUrl(host+"/RetriveTQty/"+user);
            for (int i = 0; i < jAry.length(); i++)
            {
                JSONObject jObj = jAry.getJSONObject(i);
                list.add(new YRetrieveModel(jObj.getString("itemCode"),
                                            jObj.getString("itemDes"),
                                            jObj.getString("retrieveQty"),
                                            jObj.getString("totalQty")));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  list;
    }


    public  static void updateRetrieveQty(List<YRetrieveModel> retrieveList,String loginUserName)
    {

        JSONArray jsonArray = new JSONArray();

        try {

            for(YRetrieveModel eachObj : retrieveList )
            {
                JSONObject jObj = new JSONObject();
                jObj.put("itemCode",eachObj.get("itemCode"));
                jObj.put("itemDes",eachObj.get("itemDes"));
                jObj.put("retrieveQty", eachObj.get("retrieveQty"));
                jObj.put("totalQty", eachObj.get("totalQty"));
                jsonArray.put(jObj);
            }

            String result = JSONParser.postStream(host+"/RetriveTQty/Update/"+loginUserName,jsonArray.toString() );


        }catch (JSONException e)
        {
                e.printStackTrace();
        }


    }


}








