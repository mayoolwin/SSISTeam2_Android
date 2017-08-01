package com.example.mayoolwin.ssisteam2_android;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by May Oo Lwin on 7/29/2017.
 */

public class ViewAllAdjustmentDetailModel extends java.util.HashMap<String,String> {

    final static String host = "http://172.23.135.97/SSISTeam2/Classes/WebServices/Service.svc";
    public ViewAllAdjustmentDetailModel(String ItemDesc, String QtyAdjust,String PriceAdjust,String Reason)
    {
        put("ItemDesc", ItemDesc);
        put("QtyAdjust", QtyAdjust);
        put("PriceAdjust", PriceAdjust);
        put("Reason", Reason);
    }
    public ViewAllAdjustmentDetailModel(){}

    public  static List<ViewAllAdjustmentDetailModel> ViewAllAdjustmentDetail(String voucherId)
    {

        List<ViewAllAdjustmentDetailModel> list = new ArrayList<ViewAllAdjustmentDetailModel>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/ViewAllAdjustmentDetail/"+voucherId);
            Log.e("Jason Array",a.toString());
            for (int i = 0; i < a.length(); i++)
            {
                JSONObject j = a.getJSONObject(i);
                list.add(new ViewAllAdjustmentDetailModel(j.getString("ItemDesc"), j.getString("QtyAdjust"),j.getString("PriceAdjust"),j.getString("Reason")));
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  list;
    }

    public static void updateInventoryAdjustment(String voucherId) {
        try {
            Log.e("Updateeeeeeeeeeeeeeeeee","Test"+voucherId);
            String result = JSONParser.postStream(host+"/UpdateInventoryAdjustment", voucherId);
            Log.e("ddd","REsult"+result);
        } catch (Exception e) {

        }
    }

    public static void deleteInventoryAdjustment(String voucherId) {
        try {
            Log.e("Deleteeeeeeeeeeeeeeeee","Test"+voucherId);
            String result = JSONParser.postStream(host+"/DeleteInventoryAdjustment", voucherId);
            Log.e("ddd","REsult"+result);
        } catch (Exception e) {

        }
    }
}
