package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.R.id.listView;

public class ViewAllAdjustmentDetail extends AppCompatActivity {

    String []key = {"ItemDesc","QtyAdjust","PriceAdjust","Reason"};
    //String []key = {"CreatedDate","UserName","Reason","StartDate","EndDate","DeptCode","Deleted"};
    String voucherId;ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_adjustment_detail);

        listview = (ListView) findViewById(listView);
        Intent mIntent  = getIntent();
        voucherId = getIntent().getExtras().getString("voucherId");
        Log.e("Voucher ID",voucherId);

       new AsyncTask<String, Void, List<ViewAllAdjustmentDetailModel>>() {
            @Override
            protected List<ViewAllAdjustmentDetailModel> doInBackground(String... params) {
                return ViewAllAdjustmentDetailModel.ViewAllAdjustmentDetail(params[0]);
            }
            @Override
            protected void onPostExecute(List<ViewAllAdjustmentDetailModel> result) {
                Log.e("Result Size","Test"+result.size());
                listview.setAdapter(
                        new SimpleAdapter
                                (ViewAllAdjustmentDetail.this, result, R.layout.view_detail_adjustment_row,
                                        new String[]{"ItemDesc", "QtyAdjust", "PriceAdjust", "Reason"},
                                        new int[]{R.id.textView27, R.id.textView28, R.id.textView30, R.id.textView32})
                );

            }
        }.execute(voucherId);

        Button b = (Button) findViewById(R.id.button4);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        ViewAllAdjustmentDetailModel.updateInventoryAdjustment(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(String result) {
                        finish();
                        Log.e("ddd","Data"+result);
                    }
                }.execute(voucherId);
            }
        });

        Button c = (Button) findViewById(R.id.button5);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        ViewAllAdjustmentDetailModel.deleteInventoryAdjustment(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(String result) {
                        finish();
                        Log.e("ddd","Data"+result);
                    }
                }.execute(voucherId);
            }
        });
    }
}
