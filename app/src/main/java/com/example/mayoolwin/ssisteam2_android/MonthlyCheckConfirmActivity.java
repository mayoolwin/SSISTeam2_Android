package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.net.Uri;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mayoolwin.ssisteam2_android.fragment.InventoryCheckList;

import java.util.ArrayList;
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

        ArrayList<InventoryCheck> confirmList = new ArrayList<>();

        for (HashMap<String, String> hash: maps
             ) {
            InventoryCheck inventorycheck = InventoryCheck.fromHashMap(hash);
            confirmList.add(inventorycheck);
        }
//        for (InventoryCheck inventory: inventoryChecks
//             ) {
//            if (inventory.getActualQuantity() == inventory.getCurrentQuantity()) {
//                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
//                confirmList.add(inventory);
//            }
//        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();

        InventoryCheckList fragment = InventoryCheckList.newInstance(confirmList);
        if (fm.findFragmentByTag("confirmList") == null) {
            fTransaction.add(R.id.inventoryConfirmPlaceHolder, fragment, "confirmList");
        } else {
            fTransaction.replace(R.id.inventoryConfirmPlaceHolder, fragment, "confirmList");
        }
        fTransaction.commit();

    }

    @Override
    public void onFragmentInteraction(InventoryCheck inventoryCheck, int index) {
        Toast.makeText(getApplicationContext(), inventoryCheck.getReason(), Toast.LENGTH_LONG).show();
    }
}
