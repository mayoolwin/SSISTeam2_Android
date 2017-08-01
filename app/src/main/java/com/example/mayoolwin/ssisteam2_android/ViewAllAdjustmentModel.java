package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by May Oo Lwin on 7/29/2017.
 */
public class ViewAllAdjustmentModel extends java.util.HashMap<String,String>
{
//    final static String host = "http://172.23.135.97/SSISTeam2/Classes/WebServices/Service.svc";
    String  vocherId,clerk,date,status,highestCost;
    public ViewAllAdjustmentModel(String VoucherID, String Clerk,String Date,String Status,String HighestCost)
    {
        put("VoucherID", VoucherID);
        put("Clerk", Clerk);
        put("Date", Date);
        put("Status", Status);
        put("HighestCost", HighestCost);
        this.vocherId = VoucherID;
        this.clerk = Clerk;
        this.date  = Date;
        this.status = Status;
        this.highestCost = HighestCost;
    }

    public static String getHost() {
        return host;
    }

    public String getVocherId() {
        return vocherId;
    }

    public String getClerk() {
        return clerk;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getHighestCost() {
        return highestCost;
    }

    public ViewAllAdjustmentModel(){}

    public  static List<ViewAllAdjustmentModel> ViewAllAdjustment(String role)
    {
        List<ViewAllAdjustmentModel> list = new ArrayList<ViewAllAdjustmentModel>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/ViewAllAdjustment/"+role);
            for (int i = 0; i < a.length(); i++)
            {
                JSONObject j = a.getJSONObject(i);
                list.add(new ViewAllAdjustmentModel(j.getString("VoucherID"), j.getString("Clerk"),j.getString("Date"),j.getString("Status"),j.getString("HighestCost")));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return  list;
    }
}