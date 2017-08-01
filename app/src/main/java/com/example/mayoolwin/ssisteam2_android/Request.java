package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by macbook on 25/7/17.
 */

public class Request extends HashMap<String,String> {





    public Request(String id, String name, String date, String reason, String status) {
        put("Id", id);
        put("Name", name);
        put("Date", date);
        put("Reason",reason);
        put("Status",status);

    }

    public Request()
    {}

    public static void Approve(String id)
    {

        JSONArray c = JSONParser.getJSONArrayFromUrl(host+"/Approve/"+id);
    }

    public static void Reject(String id)
    {

        JSONArray c = JSONParser.getJSONArrayFromUrl(host+"/Reject/"+id);
    }

    public static List<Request> listRequest(String dept_code) {

        List<Request> reqList = new ArrayList<Request>();
        try {
            JSONArray c = JSONParser.getJSONArrayFromUrl(host+"/pendingrequest/"+dept_code);
            for(int i=0;i< c.length();i++)
            {
                JSONObject b=c.getJSONObject(i);
                Request req = new Request(b.getString("req_id"),
                        b.getString("user"),
                        b.getString("requestdate"),
                b.getString("reason"),b.getString("status"));
                reqList.add(req);
            }




        } catch (Exception e) {
        }
        return reqList;

    }

    public static List<Request> GetRequestByDeptCode(String dept_code) {

        List<Request> reqList = new ArrayList<Request>();
        try {
            JSONArray c = JSONParser.getJSONArrayFromUrl(host+"/GetRequestByDeptCode/"+dept_code);
            for(int i=0;i< c.length();i++)
            {
                JSONObject b=c.getJSONObject(i);
                Request req = new Request(b.getString("req_id"),
                        b.getString("user"),
                        b.getString("requestdate"),
                        b.getString("reason"),b.getString("status"));
                reqList.add(req);
            }




        } catch (Exception e) {
        }
        return reqList;

    }

    public static List<Request> GetRequestByUserName(String dept_code,String user) {

        List<Request> reqList = new ArrayList<Request>();
        try {
            JSONArray c = JSONParser.getJSONArrayFromUrl(host + "/GetRequestByUserName/"+dept_code+"/" + user);
            for (int i = 0; i < c.length(); i++) {
                JSONObject b = c.getJSONObject(i);
                Request req = new Request(b.getString("req_id"),
                        b.getString("user"),
                        b.getString("requestdate"),
                        b.getString("reason"), b.getString("status"));
                reqList.add(req);
            }


        } catch (Exception e) {
        }
        return reqList;
    }





}
