package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by macbook on 25/7/17.
 */

public class Request extends HashMap<String,String> {

    final static String host = "http://172.23.135.134/SSISAndroidTeam2/Service.svc";


    public Request(String id, String name, String date) {
        put("Id", id);
        put("Name", name);
        put("Date", date);

    }

    public Request(String id, String name, String date, String status, String item_description, String quantity){}

    public static List<String> listRequest() {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/Request");
            for (int i=0; i<a.length(); i++) {
                String c = a.getString(i);
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static Request getRequest(String id) {
        Request request = null;
        try {
            JSONObject c = JSONParser.getJSONFromUrl(host+"/Request/"+id);
            request = new Request(c.getString("req_id"),
                     c.getString("requestdate"),
                    c.getString("user"));

        } catch (Exception e) {
        }
        return request;
    }
/*
    public static void updateRequest(Request r) {
        JSONObject jrequest = new JSONObject();
        try {
            jrequest.put("Id", r.get("Id"));
            jrequest.put("Name", r.get("Name"));
            jrequest.put("Date", r.get("Date"));
            jrequest.put("Status", r.get("Status"));
            jrequest.put("Item Description", r.get("Item Description"));
            jrequest.put("Quantity", Integer.parseInt(r.get("Quantity")));
        } catch (Exception e) {
        }
        String result = JSONParser.postStream(host+"/Reject", jrequest.toString());
    }*/


}
