package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayoolwin.ssisteam2_android.fragment.InventoryCheckDetail;
import com.example.mayoolwin.ssisteam2_android.fragment.InventoryCheckList;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.InventoryCheck.listInventoryCheck;
import static com.example.mayoolwin.ssisteam2_android.User.host;

public class MonthlyCheckActivity extends AppCompatActivity implements InventoryCheckList.OnFragmentInteractionListener, InventoryCheckDetail.OnFragmentInteractionListener {

    ArrayList<InventoryCheck> inventoryChecks = new ArrayList<>();
    Button confirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_check);

        confirmButton = (Button) findViewById(R.id.inventoryCheckNextButton);

        if (savedInstanceState == null){
            try {
                new AsyncTask<Void, Void, String>(){

                    @Override
                    protected String doInBackground(Void... voids) {
                        final String service = "/InventoryCheck/";
                        try {
                            return InventoryCheck.getJsonStringFromUrl(host + service);
                        } catch (Exception e) {
                            Toast toast = new Toast(getApplicationContext());
                            toast.makeText(getApplicationContext(),
                                    "Unable to retrieve catalogue list" , Toast.LENGTH_LONG).show();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(String a) {
                        super.onPostExecute(a);

                        confirmButton.setEnabled(true);
                        String JSONString = a;

                        try {
                            if (JSONString == null || JSONString.isEmpty()) {
                                Toast.makeText(getApplicationContext(), "No images found", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            inventoryChecks = InventoryCheck.fromJSONString(JSONString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (inventoryChecks == null) {
                            Toast.makeText(getApplicationContext(), "No images found", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fTransaction = fm.beginTransaction();

                        InventoryCheckList fragment = InventoryCheckList.newInstance(inventoryChecks);
                        if (fm.findFragmentByTag("InventoryList") == null) {
                            fTransaction.add(R.id.inventoryCheckListPlaceHolder, fragment, "InventoryList");
                        } else {
                            fTransaction.replace(R.id.inventoryCheckListPlaceHolder, fragment, "InventoryList");
                        }
                        fTransaction.commit();
                    }

                }.execute();
            } catch (Exception e) {
                Toast toast = new Toast(getApplicationContext());
                toast.makeText(getApplicationContext(),
                        "Unable to retrieve catalogue list", Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            confirmButton = (Button) findViewById(R.id.inventoryCheckNextButton);
            confirmButton.setEnabled(true);
            Bundle arg = savedInstanceState.getBundle("Bundle");
            ArrayList<HashMap<String, String>> maps = (ArrayList<HashMap<String, String>>) arg.getSerializable("List");
            for (HashMap<String, String> map: maps
                 ) {
                InventoryCheck i = InventoryCheck.fromHashMap(map);
                inventoryChecks.add(i);
            }
        }



//        final Intent intent = new Intent(this, MonthlyCheckConfirmActivity.class);
//
//        confirmButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                ArrayList<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();
//                for (InventoryCheck inventory: inventoryChecks
//                     ) {
//                    maps.add(inventory.toHashMap());
//                }
//                Bundle arg = new Bundle();
//                arg.putSerializable("List", maps);
//                intent.putExtra("Bundle", arg);
//
//                startActivity(intent);
//            }
//        });
    }

    //Portrait orientation
    @Override
    public void onFragmentInteraction(InventoryCheck inventoryCheck, int index) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();
        if (findViewById(R.id.inventoryCheckDetailPlaceHolder) != null) {
            // in landscape
            InventoryCheckDetail fragmentDetail = InventoryCheckDetail.newInstance(
                    inventoryCheck, index);
            if (fm.findFragmentByTag("InventoryDetail") == null) {
                fTransaction.add(R.id.inventoryCheckDetailPlaceHolder, fragmentDetail, "InventoryDetail");
            } else {
                fTransaction.replace(R.id.inventoryCheckDetailPlaceHolder, fragmentDetail, "InventoryDetail");
            }
            fTransaction.commit();

        } else {
            // start new activity
            HashMap<String, String> inventoryHash = inventoryCheck.toHashMap();
            Intent intent = new Intent(this, MonthlyCheckDetailActivity.class);
            intent.putExtra("Details", inventoryHash);
            intent.putExtra("Index", index);

            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                HashMap<String, String> inventoryHash = (HashMap<String, String>) data.getSerializableExtra("ResultInventory");
                int index = data.getIntExtra("ResultIndex", -1);

                InventoryCheck inventoryCheck = InventoryCheck.fromHashMap(inventoryHash);
                inventoryChecks.get(index).setActualQuantity(inventoryCheck.getActualQuantity());
                inventoryChecks.get(index).setReason(inventoryCheck.getReason());

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fTransaction = fm.beginTransaction();

                InventoryCheckList fragment = InventoryCheckList.newInstance(inventoryChecks);
                if (fm.findFragmentByTag("InventoryList") == null) {
                    fTransaction.add(R.id.inventoryCheckListPlaceHolder, fragment, "InventoryList");
                } else {
                    fTransaction.replace(R.id.inventoryCheckListPlaceHolder, fragment, "InventoryList");
                }
                fTransaction.commit();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    //to be called when confirmed
    public void UpdateList(InventoryCheck inventoryCheck, int index) {
        inventoryChecks.get(index).setActualQuantity(inventoryCheck.getActualQuantity());
        inventoryChecks.get(index).setReason(inventoryCheck.getReason());
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
//            setContentView(R.layout.activity_monthly_check);
//            confirmButton = (Button)findViewById(R.id.inventoryCheckNextButton);
//            confirmButton.setEnabled(true);
//            confirmButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    confirmMonthlyCheck(view);
//                }
//            });
//        }else if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
//            setContentView(R.layout.activity_monthly_check);
//            confirmButton = (Button)findViewById(R.id.inventoryCheckNextButton);
//            confirmButton.setEnabled(true);
//            confirmButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    confirmMonthlyCheck(view);
//                }
//            });
//        }
//    }

    //Landscape orientation
    @Override
    public void onFragmentInteraction(String reason, int actualQty, int index) {
        inventoryChecks.get(index).setActualQuantity(actualQty);
        inventoryChecks.get(index).setReason(reason);


        FragmentManager fm = getFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();

        InventoryCheckList fragment = InventoryCheckList.newInstance(inventoryChecks);
        if (fm.findFragmentByTag("InventoryList") == null) {
            fTransaction.add(R.id.inventoryCheckListPlaceHolder, fragment, "InventoryList");
        } else {
            fTransaction.replace(R.id.inventoryCheckListPlaceHolder, fragment, "InventoryList");
        }
        fTransaction.commit();
    }

    public void confirmMonthlyCheck(View view) {
        Intent intent = new Intent(this, MonthlyCheckConfirmActivity.class);

        ArrayList<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();
        for (InventoryCheck inventory: inventoryChecks
                ) {
            maps.add(inventory.toHashMap());
        }
        Bundle arg = new Bundle();
        arg.putSerializable("List", maps);
        intent.putExtra("Bundle", arg);

        startActivity(intent);    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Button confirmButton = (Button) findViewById(R.id.inventoryCheckNextButton);
        ArrayList<HashMap<String, String>> map = new ArrayList<>();
        Bundle arg = new Bundle();
        for (InventoryCheck i: inventoryChecks
             ) {
            HashMap<String, String> m = i.toHashMap();
            map.add(m);
        }
        arg.putSerializable("List", map);

        savedInstanceState.putBundle("Bundle", arg);
    }
}
