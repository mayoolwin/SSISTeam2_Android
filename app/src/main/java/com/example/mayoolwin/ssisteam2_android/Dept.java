package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Y on 01/08/2017.
 */

public class Dept  extends HashMap<String,String> {

    final static  String host = "http://172.23.134.105/ssisteam2/Classes/WebServices/Service.svc";

    public Dept(String ContactNumber, String DeptCode, String RepUser ) {

        put("ContactNumber", ContactNumber);
        put("DeptCode", DeptCode);
        put("RepUser", RepUser);

    }

    public  static Dept getDept(String deptName)
    {
        Dept d = null;
        String s =null;
        try {
            JSONObject c = JSONParser.getJSONFromUrl(host+"/Department/"+deptName);
            d = new Dept(c.getString("ContactNumber"),c.getString("DeptCode"),c.getString("RepUser"));

        } catch (Exception e) {

        }
        return d;
    }
}