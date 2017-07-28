package com.example.mayoolwin.ssisteam2_android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

public class YRetrieveActivity extends AppCompatActivity {

    ListView listview;
    Button btnConfrim;
    TextView textViewTest;
    int resCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yretrieve);

        listview = (ListView) findViewById(R.id.listView);
        textViewTest = (TextView) findViewById(R.id.textViewTest);

        new AsyncTask<Void, Void, List<YRetrieveModel>>() {
            @Override
            protected List<YRetrieveModel> doInBackground(Void... params) {
                return YRetrieveModel.listEachItemTotalQty();
            }

            @Override
            protected void onPostExecute(List<YRetrieveModel> result) {

                listview.setAdapter(
                        new SimpleAdapter
                                (YRetrieveActivity.this, result, R.layout.y_row,
                                        new String[]{"itemDes", "totalQty"},
                                        new int[]{R.id.textView2, R.id.textView3})
                );


            }
        }.execute();

        //To Webservice
        btnConfrim = (Button) findViewById(R.id.buttonConfirm);
        btnConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//                List<YRetrieveModel> list =YRetrieveModel.listEachItemTotalQty();
//                resCount = list.size();
//                textViewTest.setText(resCount);
            }
        });
    }
}