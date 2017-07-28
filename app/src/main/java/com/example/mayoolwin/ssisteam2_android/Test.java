package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Test extends AppCompatActivity {

    //TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

       //String item = getIntent().getExtras().getString("username");
        //Log.e("Test",item);
        Intent mIntent  = getIntent();
        /*ApprovalDuties mEmployee  = (ApprovalDuties )mIntent.getParcelableExtra("result");
        for(int i=0;i<mEmployee.size();i++){
            Log.e("My Test",mEmployee.get("UserName"));
        }*/
        //Log.e("Test",mEmployee.toString());
        String username = getIntent().getExtras().getString("username");
        String createddate = getIntent().getExtras().getString("createddate");

        TextView usrName=(TextView)findViewById(R.id.textView9);
        usrName.setText(username);

    }
}
