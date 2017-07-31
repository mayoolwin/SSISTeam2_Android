package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

public class ViewAllPendingRequestActivity extends Activity implements AdapterView.OnItemClickListener {


    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pending_request_list);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String dept_code= pref.getString("dept_code", "default");

        final ListView listView=(ListView)findViewById(R.id.listView2);



        new AsyncTask<String,Void,List<Request>>()
        {

            @Override
            protected List<Request> doInBackground(String... params) {
                List<Request> r=Request.listRequest(params[0]);
                return r;
            }

            @Override
            protected void onPostExecute(List<Request> result) {



                listView.setAdapter(new SimpleAdapter(getApplicationContext(),result,R.layout.pending_request_row,new String[]{"Name","Date"},new int[]{R.id.name,R.id.date}));




            }
        }.execute(dept_code);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {

        Request req=(Request)adapterView.getAdapter().getItem(postion);

        Intent i=new Intent(this,ApproveRequestActivity.class);

        i.putExtra("Id",req.get("Id"));
        i.putExtra("Date",req.get("Date"));
        i.putExtra("Name",req.get("Name"));
        i.putExtra("Reason",req.get("Reason"));

        startActivity(i);
    }
}
