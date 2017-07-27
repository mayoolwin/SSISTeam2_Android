package com.example.mayoolwin.ssisteam2_android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

public class YRetrieveActivity extends AppCompatActivity {

     ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yretrieve);

        listview =(ListView) findViewById(R.id.listView);
        new AsyncTask<Void, Void, List<YRetrieveModel>>() {
            @Override
            protected List<YRetrieveModel> doInBackground(Void... params) {
                return YRetrieveModel.listEachItemTotalQty();
            }
            @Override
            protected void onPostExecute(List<YRetrieveModel> result) {

        listview.setAdapter(
                new SimpleAdapter
                        (YRetrieveActivity.this, result , R.layout.y_retrive_row,
                        new String[] {"itemDes","totalQty"},
                        new int[]{R.id.textView2,R.id.textView3})
        );


            }
        }.execute();
    }
}
