package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayoolwin.ssisteam2_android.fragment.InventoryCheckList;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MonthlyCheckConfirmActivity extends AppCompatActivity implements InventoryCheckList.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_check_confirm);

        Intent intent = getIntent();
        Bundle arg = intent.getBundleExtra("Bundle");
        ArrayList<HashMap<String, String>> maps = (ArrayList<HashMap<String, String>>) arg.getSerializable("List");

        final ArrayList<InventoryCheck> inventoryChecks = new ArrayList<>();
        ArrayList<InventoryCheck> confirmList = new ArrayList<>();

        for (HashMap<String, String> hash: maps
             ) {
            InventoryCheck inventorycheck = InventoryCheck.fromHashMap(hash);
            //For post to web server
            inventoryChecks.add(inventorycheck);

            if (inventorycheck.getActualQuantity() != inventorycheck.getCurrentQuantity()){
                //To be used for confirmation list
                confirmList.add(inventorycheck);
            }
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();

        InventoryCheckList fragment = InventoryCheckList.newInstance(confirmList);
        if (fm.findFragmentByTag("confirmList") == null) {
            fTransaction.add(R.id.inventoryConfirmPlaceHolder, fragment, "confirmList");
        } else {
            fTransaction.replace(R.id.inventoryConfirmPlaceHolder, fragment, "confirmList");
        }
        fTransaction.commit();

        final InventoryCheck testing = inventoryChecks.get(0);
        Button updateButton = (Button) findViewById(R.id.inventoryCheckUpdateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);


                new AsyncTask<ArrayList<InventoryCheck>, Void, Void>() {
                    @Override
                    protected Void doInBackground(ArrayList<InventoryCheck>... params) {
                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        String username = pref.getString("username", "default");
                        InventoryCheck.UpdateInventoryChecks(params[0], username);
//                        InventoryCheck.UpdateInventory(params, username);

                        return null;
                    }
                }.execute(inventoryChecks);

//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String date = sdf.format(new Date());
//                NewRequest req = new NewRequest("01", "hengtiong", "reason", "Penging", date);
//
//                new AsyncTask<NewRequest, Void, Void>() {
//                    @Override
//                    protected Void doInBackground(NewRequest... params) {
//
//                        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                        String username = pref.getString("username", "default");
//                        InventoryCheck.InsertRequest(params[0], username);
//
//                        return null;
//                    }
//                }.execute(req);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onFragmentInteraction(InventoryCheck inventoryCheck, int index) {
        Toast.makeText(getApplicationContext(), inventoryCheck.getReason(), Toast.LENGTH_LONG).show();
    }
}
