package com.example.mayoolwin.ssisteam2_android;

import java.util.HashMap;

/**
 * Created by Htein Lin Aung on 7/27/2017.
 */

public class Item extends HashMap<String,String> {

    public Item(String item_desc, String quantity) {
        put("ItemDesc", item_desc);
        put("Quantity", quantity);
    }
}
