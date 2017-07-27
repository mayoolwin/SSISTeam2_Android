package com.example.mayoolwin.ssisteam2_android;

import android.net.Uri;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mayoolwin.ssisteam2_android.fragments.MonthlyCheckFragment;

import java.util.ArrayList;

public class MonthlyCheckConfirmActivity extends AppCompatActivity implements MonthlyCheckFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_check_confirm);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fTransaction = fm.beginTransaction();

        MonthlyCheckFragment fragment = MonthlyCheckFragment.newInstance(new ArrayList<String>());
        fTransaction.replace(R.id.monthlyCheckPlaceHolder, fragment);
        fTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
