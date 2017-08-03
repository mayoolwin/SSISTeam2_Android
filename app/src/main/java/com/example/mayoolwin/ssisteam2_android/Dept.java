package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONObject;

import java.util.HashMap;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by Y on 01/08/2017.
 */

public class Dept  extends HashMap<String,String> {


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