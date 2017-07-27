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

    //static String host = "http://172.23.135.222/SSISTeam2/Classes/WebServices/Service.svc";
    static String host = "http://172.23.135.128/SSISTeam2/Classes/WebServices/Service.svc";


    public Request(String id, String name, String date, String reason) {
        put("Id", id);
        put("Name", name);
        put("Date", date);
        put("Reason",reason);

    }

    public Request()
    {}

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
                b.getString("reason"));
                reqList.add(req);
            }




        } catch (Exception e) {
        }
        return reqList;

    }




}
