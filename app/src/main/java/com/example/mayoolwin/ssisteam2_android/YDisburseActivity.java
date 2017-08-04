package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class YDisburseActivity extends AppCompatActivity {

    private Spinner spinner1;
    private ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_y_disburse);

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        listView1 = (ListView) findViewById(R.id.listView2);


        try
        {

        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... params) {

                    return YDisburseModel.listCollectP();


            }

            @Override
            protected void onPostExecute(List<String> result) {

                if(result.size()==0)
                {

                }
                else
                {
                    String[] ary = new String[result.size()];

                    for (int i = 0; i < ary.length; i++) {
                        ary[i] = result.get(i);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(YDisburseActivity.this,
                            android.R.layout.simple_spinner_item, ary);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(adapter);
                }

            }
        }.execute();

        } catch (NullPointerException e)
        {

        }


        //select dropdown
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                final String cpName = adapterView.getAdapter().getItem(i).toString();


                String cpid = Integer.toString(i + 1);

                new AsyncTask<String, Void, List<String>>() {
                    @Override
                    protected List<String> doInBackground(String... params) {
                        return YDisburseModel.listCollectDept(params[0]);
                    }

                    @Override
                    protected void onPostExecute(List<String> result) {

                            ArrayAdapter<String> aryAdapter = new ArrayAdapter<String>(YDisburseActivity.this,android.R.layout.simple_list_item_1,result);
                            listView1.setAdapter(aryAdapter);

                    }
                }.execute(cpid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String deptName = (String) adapterView.getItemAtPosition(i);
                if(deptName.equals("No department"))
                {

                    Toast.makeText(YDisburseActivity.this, "No department at this collection point!", Toast.LENGTH_SHORT).show();

                }
                else
                {Intent intent = new Intent(YDisburseActivity.this, YDisbDetailActivity.class);
                    intent.putExtra("deptName", deptName);
                    startActivity(intent);}


            }
        });

    }
}
