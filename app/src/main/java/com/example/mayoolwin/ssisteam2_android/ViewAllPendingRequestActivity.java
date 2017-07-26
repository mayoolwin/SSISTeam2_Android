package com.example.mayoolwin.ssisteam2_android;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

public class ViewAllPendingRequestActivity extends ListActivity {

    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String dept_code= pref.getString("dept_code", "default");

        new AsyncTask<String,Void,List<Request>>()
        {

            @Override
            protected List<Request> doInBackground(String... params) {
                List<Request> r=Request.listRequest(params[0]);
                return r;
            }

            @Override
            protected void onPostExecute(List<Request> result) {

               setListAdapter(new SimpleAdapter(getApplicationContext(), result,android.R.layout.simple_list_item_2,new String[]{"Name","Date"},new int[]{android.R.id.text1,android.R.id.text2}));


            }
        }.execute(dept_code);
    }
    protected void onListItemClick(ListView l, View v, int postion, long id)
    {
        String request=(String)getListAdapter().getItem(postion);
        Intent i=new Intent(this,ApproveRequestActivity.class);
        i.putExtra("Id",request);
        startActivity(i);

    }
}
