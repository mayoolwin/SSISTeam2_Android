package com.example.mayoolwin.ssisteam2_android;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by Htein Lin Aung on 7/28/2017.
 */

public class NewRequest extends HashMap<String,String> {

    public NewRequest(String username, String dept_code, String reason, String status,String date_time) {
        put("Name", username);
        put("DeptCode", dept_code);
        put("Reason", reason);
        put("Status",status);
        put("Date",date_time);

    }

    public NewRequest(){}

    public static  void InsertNewRequest(String name,String dept_code,String reason,String date)
    {
        JSONArray c=JSONParser.getJSONArrayFromUrl(host+"/NewRequest/"+name+"/"+dept_code+"/"+reason+"/"+date);
    }

    public static void InsertRequest(NewRequest req) {
        JSONObject jreq = new JSONObject();
        try {
            jreq.put("Name", req.get("Name"));
            jreq.put("DeptCode", req.get("DeptCode"));
            jreq.put("Reason", req.get("Reason"));
            jreq.put("Status", req.get("Status"));
            jreq.put("Date", req.get("Date"));


        } catch (Exception e) {
        }
        String result = JSONParser.postStream(host+"/CreateNewRequest", jreq.toString());
    }


}
