package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateRequestDetail extends AppCompatActivity implements View.OnClickListener   {

    private TextView name;
    private String item_name;
    private EditText qty;
    private String req_id;
    private Button update;
    private Button delete;
    private String quantity;
    private String user_name;
    private String status;
    private String req_detail_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_request_detail);
        name=(TextView)findViewById(R.id.txtname);
        qty=(EditText)findViewById(R.id.txtquantity);
         item_name= getIntent().getExtras().getString("ItemName");
         quantity=getIntent().getExtras().getString("Quantity");
         req_id=getIntent().getExtras().getString("ReqId");
        user_name=getIntent().getExtras().getString("UserName");
        req_detail_id=getIntent().getExtras().getString("RequestDetailId");
        status=getIntent().getExtras().getString("Status");

        update=(Button)findViewById(R.id.update);
        delete=(Button) findViewById(R.id.delete);

        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        name.setText(item_name);
        qty.setText(quantity);


    }

    @Override
    public void onClick(View v) {
        String count= qty.getText().toString();
        switch (v.getId()) {
            case R.id.update:
                // update is clicked
                new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                        RequestDetail.Update(params[0],params[1]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        Toast t = Toast.makeText(getApplicationContext(), "Update Successful", Toast.LENGTH_SHORT);
                        t.show();
                        Intent i=new Intent(getApplicationContext(),RequestDetailList.class);
                        i.putExtra("Id",req_id);
                        i.putExtra("Status",status);
                        i.putExtra("Name", user_name);
                        startActivity(i);
                        finish();
                    }
                }.execute(req_detail_id,count);


                break;
            case R.id.delete:
                // delete is clicked
                new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                        RequestDetail.Delete(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        Toast t = Toast.makeText(getApplicationContext(), "Delete Successful", Toast.LENGTH_SHORT);
                        t.show();
                        Intent i=new Intent(getApplicationContext(),RequestDetailList.class);
                        i.putExtra("Id",req_id);
                        i.putExtra("Status",status);
                        i.putExtra("Name", user_name);
                        startActivity(i);
                        finish();
                    }
                }.execute(req_detail_id);
                break;
        }
    }
}
