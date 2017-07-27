package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.StringTokenizer;

public class YDisburseActivity extends Activity {

    private Spinner spinner;
    private TextView textView2;
    private ListView listView;
    private TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_disburse);

        spinner = (Spinner) findViewById(R.id.spinner);
        textView2 = (TextView) findViewById(R.id.textView2);
        listView = (ListView) findViewById(R.id.listView1);
        textView4 = (TextView) findViewById(R.id.textView4);

        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... params) {
                return YDisburseModel.listCollectP();
            }

            @Override
            protected void onPostExecute(List<String> result) {
                String[] ary = new String[result.size()];

                for (int i = 0; i < ary.length; i++) {
                    ary[i] = result.get(i);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(YDisburseActivity.this,
                        android.R.layout.simple_spinner_item, ary);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        }.execute();

        //select dropdown
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                textView2.setText(Integer.toString((i + 1)));
                String id = textView2.getText().toString();

                new AsyncTask<String, Void, List<String>>() {
                    @Override
                    protected List<String> doInBackground(String... params) {
                        return YDisburseModel.listCollectDept(params[0]);
                    }

                    @Override
                    protected void onPostExecute(List<String> result) {

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(YDisburseActivity.this,
                                android.R.layout.simple_list_item_1, result);

                        listView.setAdapter(adapter);

                    }
                }.execute(id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String depName = (String) adapterView.getItemAtPosition(i);
                textView4.setText(depName);
            }
        });

    }
}
