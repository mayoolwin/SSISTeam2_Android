package com.example.mayoolwin.ssisteam2_android;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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

public class MonthlyCheckActivity extends AppCompatActivity implements InventoryCheckList.OnFragmentInteractionListener, InventoryCheckDetail.OnFragmentInteractionListener {

    ArrayList<InventoryCheck> inventoryChecks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_check);

        try {
            new AsyncTask<Void, Void, String>(){

                @Override
                protected String doInBackground(Void... voids) {
                    final String host = InventoryCheck.host;
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
                    "Unable to retrieve catalogue list" , Toast.LENGTH_LONG).show();
            finish();
        }

        final Intent intent = new Intent(this, MonthlyCheckConfirmActivity.class);

        Button confirmButton = (Button) findViewById(R.id.inventoryCheckNextButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();
                for (InventoryCheck inventory: inventoryChecks
                     ) {
                    maps.add(inventory.toHashMap());
                }
                Bundle arg = new Bundle();
                arg.putSerializable("List", maps);
                intent.putExtra("Bundle", arg);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onFragmentInteraction(InventoryCheck inventoryCheck, int index) {
        Toast.makeText(getApplicationContext(), inventoryCheck.getItemDescription(), Toast.LENGTH_SHORT).show();

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
            Toast.makeText(getApplicationContext(), String.valueOf(index), Toast.LENGTH_LONG).show();
            fTransaction.commit();

        } else {
            // start new activity
            Intent intent = new Intent(this, MonthlyCheckConfirmActivity.class);
            intent.putExtra("Details", inventoryCheck);

            startActivity(intent);
        }
    }

    //to be called when confirmed
    public void UpdateList(InventoryCheck inventoryCheck, int index) {
        inventoryChecks.get(index).setActualQuantity(inventoryCheck.getActualQuantity());
        inventoryChecks.get(index).setReason(inventoryCheck.getReason());

        Toast.makeText(getApplicationContext(), inventoryChecks.get(index).getReason(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFragmentInteraction(String reason, int actualQty, int index) {
        inventoryChecks.get(index).setActualQuantity(actualQty);
        inventoryChecks.get(index).setReason(reason);

        Toast.makeText(getApplicationContext(), inventoryChecks.get(index).getReason().toString(), Toast.LENGTH_LONG).show();

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
}
