package com.example.mayoolwin.ssisteam2_android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ApproveRequestActivity extends AppCompatActivity{

    final static int []view={R.id.editText1,R.id.editText2,R.id.editText3};
    final static String [] key={"Name","Item Description","Quantity"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request);

        String item = getIntent().getExtras().getString("Id");



    }
}
