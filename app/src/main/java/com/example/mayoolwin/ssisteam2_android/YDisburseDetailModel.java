package com.example.mayoolwin.ssisteam2_android;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by Y on 27/07/2017.
 */

public class YDisburseDetailModel extends java.util.HashMap<String,String> {

//    final static  String host = "http://172.23.134.66/ssisteam2/Classes/WebServices/Service.svc";
    //final static  String host = "http://192.168.0.18/TestAd/Service.svc";

    public YDisburseDetailModel(String itemName, String retrievedQty, String disbursedQty) {
        put("itemName", itemName);
        put("retrievedQty", retrievedQty);
        put("disbursedQty", disbursedQty);
    }
    public YDisburseDetailModel(){}

    public  static List<YDisburseDetailModel> listDisDeptDetail(String user, String deptname)
    {
        List<YDisburseDetailModel> yDisburseLsit = new ArrayList<YDisburseDetailModel>();

        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/DisbDeptDetail/"+user+"/"+deptname);

            for(int i=0; i<a.length(); i++)
            {
                JSONObject jObj = a.getJSONObject(i);
                YDisburseDetailModel obj = new YDisburseDetailModel (jObj.getString("itemName"),
                                                                    Integer.toString(jObj.getInt("retrievedQty")),
                                                                  Integer.toString(jObj.getInt("disbursedQty")));
                yDisburseLsit.add(obj);

            }
        }catch (Exception e)
        {
            Log.e("JSON ARRAY error",e.toString());
        }

        return  yDisburseLsit;
    }

    public  static void UpdateDisburseQty(List<YDisburseDetailModel> objList, String loginUserName, String deptCode)
    {

        JSONArray upJary = new JSONArray();


        try {

            for(YDisburseDetailModel eachObj : objList )
            {
                JSONObject jObj = new JSONObject();
                jObj.put("itemName",eachObj.get("itemName"));
                jObj.put("retrievedQty", Integer.parseInt(eachObj.get("retrievedQty")));
                jObj.put("disbursedQty", Integer.parseInt(eachObj.get("disbursedQty")));
                upJary.put(jObj);
            }


        }catch (Exception e)
        {

        }
        String result = JSONParser.postStream(host+"/DisburseTQty/Update/"+loginUserName+"/"+deptCode, upJary.toString());

    }

}
