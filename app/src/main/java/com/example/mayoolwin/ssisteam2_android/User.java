package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Htein Lin Aung on 7/25/2017.
 */

public class User extends java.util.HashMap<String,String> {

    final static String host = "http://172.23.135.97/SSISTeam2/Classes/WebServices/Service.svc";

    public User(String user_name, String dept_code, String role, String flag) {
        put("UserName", user_name);
        put("DeptCode", dept_code);
        put("Role", role);
        put("Flag", flag);
    }
    /*public User(String user_name, String dept_code, String role,String flag) {
        put("UserName", user_name);
        put("DeptCode", dept_code);
        put("Role", role);
        put("Flag", flag);

    }*/

    /*public static User getUser(String name,String pass) {
        User u = null;
        try {
            JSONObject c = JSONParser.getJSONFromUrl(host+"/login/"+name+"/"+pass);
            u = new User(c.getString("user_name"),
                    c.getString("dept_code"),
                    c.getString("role"),
                    c.getString("flag"));
        } catch (Exception e) {
        }
        return u;
    }*/

    public static User getUser(String name,String pass) {
        User u = null;
        try {
            JSONObject c = JSONParser.getJSONFromUrl(host+"/login/"+name+"/"+pass);
            u = new User(c.getString("user_name"),
                    c.getString("dept_code"),
                    c.getString("role"),
                    c.getString("flag"));
        } catch (Exception e) {
        }
        return u;
    }



}
