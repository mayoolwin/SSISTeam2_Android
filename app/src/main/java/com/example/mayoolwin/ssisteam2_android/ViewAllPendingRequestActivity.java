package com.example.mayoolwin.ssisteam2_android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class ViewAllPendingRequestActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new AsyncTask<Void,Void,List<String>>()
        {

            @Override
            protected List<String> doInBackground(Void... params) {
                List<String> r=Request.listRequest();
                return r;
            }

            @Override
            protected void onPostExecute(List<String> result) {
            }
        }.execute();
    }
    protected void onListItemClick(ListView l, View v, int postion, long id)
    {
        String request=(String)getListAdapter().getItem(postion);
        Intent i=new Intent(this,ApproveRequestActivity.class);
        i.putExtra("Id",request);
        startActivity(i);

    }
}
