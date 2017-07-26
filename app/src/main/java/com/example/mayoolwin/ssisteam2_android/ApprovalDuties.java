package com.example.mayoolwin.ssisteam2_android;

import android.app.LauncherActivity;
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

public class ApprovalDuties extends java.util.HashMap<String,String> {
    //final static String host = "http:// 192.168.1.14:8090/DelegateAuthorityWebService/Service.svc";
    //final static String host = "http://172.23.135.69/DelegateAuthorityWebService/Service.svc";

    final static String host = "http://192.168.1.14/SSISTeam2/Classes/WebServices/Service.svc";


    public ApprovalDuties(String username, Date startDate, Date endDate, String deptCode,Date createdDate,String deleted,String reason) {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss aa");
        ArrayList<String> listItems = new ArrayList<String>();
        try {
            jApprovalDuties.put("UserName", ad.get("UserName"));
            jApprovalDuties.put("StartDate", dateFormat.parse(ad.get("StartDate")));
            jApprovalDuties.put("EndDate", dateFormat.parse(ad.get("EndDate")));
            jApprovalDuties.put("DeptCode", ad.get("DeptCode"));
            jApprovalDuties.put("CreatedDate", dateFormat.parse(ad.get("CreatedDate")));
            jApprovalDuties.put("Deleted", "N");
            jApprovalDuties.put("Reason", ad.get("Reason"));
        } catch (Exception e) {
        }

        String result = JSONParser.postStream(host+"/Create", jApprovalDuties.toString());
    }
}
