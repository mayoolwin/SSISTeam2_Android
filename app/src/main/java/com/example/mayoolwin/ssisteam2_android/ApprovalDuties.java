package com.example.mayoolwin.ssisteam2_android;

import android.app.LauncherActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by May Oo Lwin on 7/26/2017.
 */

public class ApprovalDuties extends java.util.HashMap<String,String> {

    final static String host = "http://172.20.10.5/SSISTeam2/Classes/WebServices/Service.svc";

    public ApprovalDuties(String username, String startDate, String endDate, String deptCode,String createdDate,String deleted,String reason) {
        put("UserName", username);
        put("StartDate", startDate.toString());
        put("EndDate", endDate.toString());
        put("DeptCode", deptCode.toString());
        put("CreatedDate", createdDate.toString());
        put("Deleted", deleted);
        put("Reason", reason);
    }

    public ApprovalDuties(){}

    public static List<String> listEmployeeName(String dept_code) {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/EmployeeList/"+dept_code);
            for (int i=0; i<a.length(); i++) {
                String c = a.getString(i);
                list.add(c);

            }
        } catch (Exception e) {
        }
        return list;
    }
   public static void createCustomer(ApprovalDuties ad) {
        JSONObject jApprovalDuties = new JSONObject();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
        ArrayList<String> listItems = new ArrayList<String>();
        try {
            jApprovalDuties.put("UserName", ad.get("UserName"));
            jApprovalDuties.put("StartDate", ad.get("StartDate"));
            jApprovalDuties.put("EndDate", ad.get("EndDate"));
            jApprovalDuties.put("DeptCode", ad.get("DeptCode"));
            jApprovalDuties.put("CreatedDate", ad.get("CreatedDate"));
            jApprovalDuties.put("Deleted",ad.get("Deleted"));
            jApprovalDuties.put("Reason", ad.get("Reason"));
        } catch (Exception e) {
        }
       Log.e("dddCreatetttteees","REsult"+jApprovalDuties.toString());

       String result = "";
       try {
           result = JSONParser.postStream(host+"/Create", jApprovalDuties.toString()); } catch (Exception e) {
       }
       Log.e("dddCreate","REsult"+result);

   }

    public static ApprovalDuties checkApprovalDuties(String dept_code) {
        ApprovalDuties ap = null;
        try {
            JSONObject a = JSONParser.getJSONFromUrl(host+"/CheckApprovalDuties/"+dept_code);
            Log.e("Error Handling","Test"+a);
            if(a!=null){
                ap = new ApprovalDuties(a.getString("UserName"),
                        a.getString("StartDate"),
                        a.getString("EndDate"),
                        a.getString("DeptCode"),
                        a.getString("CreatedDate"),
                        a.getString("Deleted"),
                        a.getString("Reason"));

            }else{
                Log.e("Error Handling Block","Test");
                ap = null;
            }

        } catch (Exception e) {
        }
        return ap;
    }

    public static void updateApprovalDutiesAssign(ApprovalDuties ap) {
        JSONObject jdeletelDuties = new JSONObject();
        try {
            jdeletelDuties.put("UserName", ap.get("UserName"));
            jdeletelDuties.put("StartDate", ap.get("StartDate"));
            jdeletelDuties.put("EndDate", ap.get("EndDate"));
            jdeletelDuties.put("DeptCode", ap.get("DeptCode"));
            jdeletelDuties.put("CreatedDate", ap.get("CreatedDate"));
            jdeletelDuties.put("Deleted",ap.get("Deleted"));
            jdeletelDuties.put("Reason", ap.get("Reason"));
        } catch (Exception e) {
        }

        Log.e("dddCreatettt","REsult"+jdeletelDuties.toString());
        try {
        String result = JSONParser.postStream(host+"/Update", jdeletelDuties.toString());
        Log.e("ddd","REsult"+result);
        } catch (Exception e) {

        }
    }
}
