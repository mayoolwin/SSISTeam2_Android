package com.example.mayoolwin.ssisteam2_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateRequestDetail extends AppCompatActivity {

    private TextView name;
    private String item_name;
    private EditText qty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_request_detail);
        name=(TextView)findViewById(R.id.txtname);
        qty=(EditText)findViewById(R.id.txtquantity);
         item_name= getIntent().getExtras().getString("ItemName");
        String quantity=getIntent().getExtras().getString("Quantity");
        String req_id=getIntent().getExtras().getString("ReqId");

        name.setText(item_name);
        qty.setText(quantity);
    }
}
