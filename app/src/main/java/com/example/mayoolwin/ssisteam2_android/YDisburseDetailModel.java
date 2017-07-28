package com.example.mayoolwin.ssisteam2_android;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Y on 27/07/2017.
 */

public class YDisburseDetailModel extends java.util.HashMap<String,String> {

    final static  String host = "http://172.23.134.20/ssisteam2/Classes/WebServices/Service.svc";
    //final static  String host = "http://192.168.0.18/TestAd/Service.svc";

    public YDisburseDetailModel(String itemName, String reqQty) {
        put("itemName", itemName);
        put("reqQty", reqQty);
    }
    public YDisburseDetailModel(){}

    public  static List<YDisburseDetailModel> listDisDeptDetail(String deptname)
    {
        List<YDisburseDetailModel> yDisburseLsit = new ArrayList<YDisburseDetailModel>();

        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/DisbDeptDetail/"+deptname);

            for(int i=0; i<a.length(); i++)
            {
                JSONObject jObj = a.getJSONObject(i);
                YDisburseDetailModel obj = new YDisburseDetailModel (jObj.getString("itemName"),Integer.toString(jObj.getInt("reqQty")));
                yDisburseLsit.add(obj);

            }
        }catch (Exception e)
        {
            Log.e("JSON ARRAY error",e.toString());
        }

        return  yDisburseLsit;
    }

}
