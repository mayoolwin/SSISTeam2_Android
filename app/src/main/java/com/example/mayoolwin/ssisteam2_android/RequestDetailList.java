package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.MainActivity.name;

public class RequestDetailList extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private TextView txtname;
    private  TextView txtstatus;
    private String req_id;
    private String user_name;
    private String status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_detail_list);
        listView=(ListView) findViewById(R.id.listView1);
         req_id = getIntent().getExtras().getString("Id");
         user_name = getIntent().getExtras().getString("Name");
         status = getIntent().getExtras().getString("Status");
        txtname=(TextView)findViewById(R.id.txtname);
        txtstatus=(TextView) findViewById(R.id.txtstatus);


        new AsyncTask<String,Void,List<RequestDetail>>()
        {

            @Override
            protected List<RequestDetail> doInBackground(String... params) {
                List<RequestDetail> r=RequestDetail.listRequestDetail(params[0]);
                return r;
            }

            @Override
            protected void onPostExecute(List<RequestDetail> result) {

                View(result);


            }
        }.execute(req_id);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {

        RequestDetail req=(RequestDetail) adapterView.getAdapter().getItem(postion);


        if(user_name.equals(name) && status.equals("Pending"))
        {Intent i=new Intent(this,UpdateRequestDetail.class);
            i.putExtra("ItemName",req.get("ItemName"));
            i.putExtra("Quantity",req.get("Quantity"));
            i.putExtra("RequestDetailId",req.get("RequestDetailId"));
            i.putExtra("ReqId",req_id);
            i.putExtra("Status",status);
            i.putExtra("UserName",user_name);
            startActivity(i);
            finish();
        }
        else if(!user_name.equals(name))
        {

            new AlertDialog.Builder(this)
                    .setTitle("You are not authorized to do this")
                    .setMessage("Opps! You are not authorized to do this!")
                    .setCancelable(true)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("You can change your items when status is pending!")
                    .setMessage("You can change your items when status is pending!")
                    .setCancelable(true)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }


    }

    public void View(List<RequestDetail> itemList)
    {
        txtname.setText(user_name);
        txtstatus.setText(status);

        listView.setAdapter(new SimpleAdapter(getApplicationContext(),itemList,R.layout.request_item_row,new String[]{"ItemName","Quantity"},new int[]{R.id.itemName,R.id.quantity}));
        listView.setOnItemClickListener(this);

    }
}
