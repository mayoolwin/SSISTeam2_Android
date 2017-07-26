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

    final static String host = "http://172.23.134.213/SSISTeam2/Classes/WebServices/Service.svc";


    public Request(String id, String name, String date) {
        put("Id", id);
        put("Name", name);
        put("Date", date);

    }

    public Request()
    {}

    public static List<Request> listRequest(String dept_code) {

        List<Request> reqList = null;
        try {
            JSONObject c = JSONParser.getJSONFromUrl(host+"/pendingrequest/"+dept_code);
            Request req = new Request(c.getString("req_id"),
                    c.getString("user"),
                    c.getString("requestdate"));
            reqList.add(req);

        } catch (Exception e) {
        }
        return reqList;

    }




}
