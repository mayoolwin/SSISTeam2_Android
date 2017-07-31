package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.R.id.listView;

public class ViewAllAdjustment extends AppCompatActivity{

    SharedPreferences pref;
    String name,role,dept_code;
    ListView listview;
    Button buttonview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_adjustment);


        listview = (ListView) findViewById(R.id.listView);


       callData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        callData();
    }

    public void callData(){
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name = pref.getString("username", "default");
        role = pref.getString("role", "default");
        dept_code = pref.getString("dept_code", "default");

        new AsyncTask<String, Void, List<ViewAllAdjustmentModel>>() {
        @Override
        protected List<ViewAllAdjustmentModel> doInBackground(String... params) {
            return ViewAllAdjustmentModel.ViewAllAdjustment(params[0]);
        }

        @Override
        protected void onPostExecute(List<ViewAllAdjustmentModel> result) {

                /*listview.setAdapter(
                        new SimpleAdapter
                                (ViewAllAdjustment.this, result, R.layout.view_adjustment_row,
                                        new String[]{"VoucherID", "Clerk", "Date", "Status", "HighestCost"},
                                        new int[]{R.id.textView2, R.id.textView3, R.id.textView13, R.id.textView15, R.id.textView20})
                );*/
            Log.e("Result","Data"+result.size());
            Log.e("Before Delete","Test"+result);
            CustomAdapter adapter = new CustomAdapter(ViewAllAdjustment.this,result);
            listview.setAdapter(adapter);
        }
    }.execute(role);
}
        /*for(int i=0;i<listview.getAdapter().getCount();i++)
        {
            buttonview = (Button) findViewById(R.id.button3);
            Button buttonview = (Button) listview.getChildAt(i).findViewById(R.id.button3);
            buttonview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(),ViewAllAdjustmentDetail.class);
                    startActivity(i);
                }
            });
            }*/

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_adjustment);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name = pref.getString("username", "default");
        role = pref.getString("role", "default");
        dept_code = pref.getString("dept_code", "default");

        listview = (ListView) findViewById(listView);


        new AsyncTask<String, Void, List<ViewAllAdjustmentModel>>() {
            @Override
            protected List<ViewAllAdjustmentModel> doInBackground(String... params) {
                return ViewAllAdjustmentModel.ViewAllAdjustment(params[0]);
            }
            @Override
            protected void onPostExecute(List<ViewAllAdjustmentModel> result) {

                listview.setAdapter(
                        new SimpleAdapter
                                (ViewAllAdjustment.this, result, R.layout.view_adjustment_row,
                                        new String[]{"VoucherID", "Clerk", "Date", "Status", "HighestCost"},
                                        new int[]{R.id.textView2, R.id.textView3, R.id.textView13, R.id.textView15, R.id.textView20})

                );
            }
        }.execute(role);
        }*/
        }



