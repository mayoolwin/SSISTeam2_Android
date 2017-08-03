package com.example.mayoolwin.ssisteam2_android;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.User.host;

/**
 * Created by Htein Lin Aung on 8/3/2017.
 */

public class WorkingPartnerModel extends java.util.HashMap<String,String> {

    public WorkingPartnerModel(String user_name, String role,String email,String ph_no) {
        put("UserName", user_name);
        put("Role", role);
        put("Email", email);
        put("PhNo", ph_no);

    }

    public static List<WorkingPartnerModel> getWorkingPartner(String name,String dept_code) {
        List<WorkingPartnerModel> pList = new ArrayList<WorkingPartnerModel>();
        try {
            JSONArray c = JSONParser.getJSONArrayFromUrl(host+"/GetWorkingPartner/"+name+"/"+dept_code);
            for(int i=0;i< c.length();i++)
            {
                JSONObject b=c.getJSONObject(i);
                WorkingPartnerModel partner = new WorkingPartnerModel(b.getString("user"),b.getString("role"),b.getString("email"),b.getString("ph_no"));
                pList.add(partner);
            }




        } catch (Exception e) {
        }
        return pList;
    }

}
