package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by Htein Lin Aung on 7/27/2017.
 */

public class Item extends HashMap<String,String> {

    public Item(String item_desc, String quantity) {
        put("ItemDesc", item_desc);
        put("Quantity", quantity);
    }

    public  Item()
    {}

    public static void CreateRequestDetail(Item i)
    {


        JSONObject jreq = new JSONObject();
        try {


                jreq.put("ItemDes", i.get("ItemDesc"));
                jreq.put("TotalQty", i.get("Quantity"));




        } catch (Exception e) {
        }
        String result = JSONParser.postStream(host+"/CreateRequestDetail", jreq.toString());

    }

}
