package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Y on 25/07/2017.
 */

public class YRetrieveModel extends java.util.HashMap<String,String>
{
   //final static  String host = "http://192.168.0.18/TestAd/Service.svc";
   final static  String host = "http://172.23.134.20/ssisteam2/Classes/WebServices/Service.svc";

    public YRetrieveModel(String itemDes, String totalQty, String retrieveQty)
    {
        put("itemDes", itemDes);
        put("totalQty", totalQty);
        put("retrieveQty",retrieveQty);
    }

    public YRetrieveModel(){}

    public  static List<YRetrieveModel> listEachItemTotalQty()
    {
        List<YRetrieveModel> list = new ArrayList<YRetrieveModel>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/RetriveTQty");
            for (int i = 0; i < a.length(); i++)
            {
                JSONObject j = a.getJSONObject(i);
                list.add(new YRetrieveModel(j.getString("itemDes"), j.getString("totalQty"),Integer.toString(j.getInt("retrieveQty"))));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  list;
    }





}


