package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.net.Uri;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MonthlyCheckConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_check_confirm);

        Intent intent = getIntent();
        ArrayList<InventoryCheck> list = (ArrayList<InventoryCheck>) intent.getSerializableExtra("List");
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();

//        MonthlyCheckFragment fragment = MonthlyCheckFragment.newInstance(list);
//        fTransaction.replace(R.id.monthlyCheckPlaceHolder, fragment);
//        fTransaction.commit();
    }

}
