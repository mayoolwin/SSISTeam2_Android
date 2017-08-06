package com.example.mayoolwin.ssisteam2_android;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by Y on 25/07/2017.
 */

public class YDisburseModel extends java.util.HashMap<String,String>
{

    public YDisburseModel(){}

    public  static List<String> listCollectP()
    {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/DisbCollectP");
            for (int i = 0; i < a.length(); i++)
            {
                list.add(a.getString(i));
            }
        }catch (JSONException e)
        {
            e.printStackTrace();
        }
        return  list;
    }

    public  static List<String> listCollectDept(String cpid)
    {
        List<String> list = new ArrayList<String>();
        try {

            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/DisbCollectDept/"+cpid);
            if(a.length()==0)
            {
                list.add("No department");
            }
            else
            {
                for (int i = 0; i < a.length(); i++)
                {
                    list.add(a.getString(i));
                }
            }

        }catch (JSONException e)
        {

        }

        return  list;
    }




}


