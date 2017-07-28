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

public class Category extends HashMap<String,String> {



    public Category(String cat_id, String itemcode, String itemdesc, String uom) {
        put("CatId", cat_id);
        put("ItemCode", itemcode);
        put("ItemDesc", itemdesc);
        put("Unit",uom);

    }

    public static List<String> listCategory() {
        List<String> list = new ArrayList<String>();
        try {
            JSONArray a = JSONParser.getJSONArrayFromUrl(host+"/Category");
            for (int i=0; i<a.length(); i++) {
                String c = a.getString(i);
                list.add(c);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static List<Category> listItem(String id) {

        List<Category> itemList = new ArrayList<Category>();
        try {
            JSONArray c = JSONParser.getJSONArrayFromUrl(host+"/Category/"+id);
            for(int i=0;i< c.length();i++)
            {
                JSONObject b=c.getJSONObject(i);
                Category item = new Category(b.getString("catId"),b.getString("itemCode"),
                        b.getString("itemDesc"),b.getString("uom"));
                itemList.add(item);
            }




        } catch (Exception e) {
        }
        return itemList;

    }
}
