package com.example.mayoolwin.ssisteam2_android;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Y on 25/07/2017.
 */

public class YDisburseModel extends java.util.HashMap<String,String>
{
   final static  String host = "http://172.23.134.20/ssisteam2/Classes/WebServices/Service.svc";
   //final static  String host = "http://192.168.0.18/TestAd/Service.svc";


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
        }catch (Exception e)
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
                list.add("There is no department!!");
            }
            else
            {
                for (int i = 0; i < a.length(); i++)
                {
                    list.add(a.getString(i));
                }
            }

        }catch (Exception e)
        {

        }
        return  list;
    }




}


