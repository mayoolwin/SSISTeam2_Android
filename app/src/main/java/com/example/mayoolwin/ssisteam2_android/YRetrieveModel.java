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
import java.util.List;

import static android.R.id.list;

/**
 * Created by Y on 25/07/2017.
 */

public class YRetrieveModel extends java.util.HashMap<String,String>
{

   final static  String host = "http://172.23.134.105/ssisteam2/Classes/WebServices/Service.svc";
    //final static  String host = "http://localhost:65454/Classes/WebServices/Service.svc";

    public YRetrieveModel(String itemDes, String retrieveQty,String totalQty)
    {
        put("itemDes", itemDes);
        put("retrieveQty",retrieveQty);
        put("totalQty", totalQty);
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
                list.add(new YRetrieveModel(jObj.getString("itemDes"),
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








