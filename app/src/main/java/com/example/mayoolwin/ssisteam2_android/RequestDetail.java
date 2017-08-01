package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by Htein Lin Aung on 7/26/2017.
 */

public class RequestDetail extends HashMap<String,String> {


    public RequestDetail(String item_name, String quantity) {

        put("ItemName", item_name);
        put("Quantity", quantity);


    }
    public RequestDetail()
    {}

    public static List<RequestDetail> listRequestDetail(String id) {

        List<RequestDetail> reqList = new ArrayList<RequestDetail>();
        try {
            JSONArray c = JSONParser.getJSONArrayFromUrl(host+"/requestdetail/"+id);
            for(int i=0;i< c.length();i++)
            {
                JSONObject b=c.getJSONObject(i);
                RequestDetail req = new RequestDetail(b.getString("itemdesc"),

                        b.getString("quantity"));
                reqList.add(req);
            }




        } catch (Exception e) {
        }
        return reqList;

    }

    public static void Update(String id,String qty)
    {

        JSONArray c = JSONParser.getJSONArrayFromUrl(host+"/UpdateRequestDetail/"+id+"/"+qty);
    }

    public static void Delete(String id)
    {

        JSONArray c = JSONParser.getJSONArrayFromUrl(host+"/DeleteRequestDetail/"+id);
    }
}
