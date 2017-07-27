package com.example.mayoolwin.ssisteam2_android;

import android.app.LauncherActivity;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by May Oo Lwin on 7/26/2017.
 */

public class ApprovalDuties extends java.util.HashMap<String,String>{
    //final static String host = "http:// 192.168.1.14:8090/DelegateAuthorityWebService/Service.svc";
    //final static String host = "http://172.23.135.69/DelegateAuthorityWebService/Service.svc";

    final static String host = "http://172.23.135.128/DelegateAuthorityWebService/Service.svc";


    public ApprovalDuties(String username, String startDate, String endDate, String deptCode,String createdDate,String deleted,String reason) {
        put("CreatedDate", createdDate);
        put("UserName", username);
        put("Reason", reason);
        put("StartDate", startDate);
        put("EndDate", endDate);
        put("DeptCode", deptCode);
        put("Deleted", deleted);

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

    public static ApprovalDuties checkApprovalDuties(String dept_code) {
        ApprovalDuties ap = null;
        try {
            JSONObject a = JSONParser.getJSONFromUrl(host+"/CheckApprovalDuties/"+dept_code);
            /*ap = new ApprovalDuties(a.getString("CreatedDate"),
                    a.getString("UserName"),
                    a.getString("Reason"),
                    a.getString("StartDate"),
                    a.getString("EndDate"),
                    a.getString("DeptCode"),
                    a.getString("Deleted"));*/
            ap = new ApprovalDuties(a.getString("UserName"),
                    a.getString("StartDate"),
                    a.getString("EndDate"),
                    a.getString("DeptCode"),
                    a.getString("CreatedDate"),
                    a.getString("Deleted"),
                    a.getString("Reason"));

        } catch (Exception e) {
        }
        return ap;
    }
   public static void createCustomer(ApprovalDuties ad) {
        JSONObject jApprovalDuties = new JSONObject();
      //  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
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

        String result = JSONParser.postStream(host+"/Create", jApprovalDuties.toString());
       Log.e("Result",jApprovalDuties.toString()+"Data"+result);
    }
}
