package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DeleteAuthorityCheck extends AppCompatActivity {

    String []key = {"CreatedDate","UserName","Reason","StartDate","EndDate","DeptCode","Deleted"};
    String username,createddate,reason,startdate,enddate,deptcode,deleted;
    TextView viewUserName,viewCreatedDate,viewReason,viewStartDate,viewEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_authority_check);
        Intent mIntent  = getIntent();
        username = getIntent().getExtras().getString("username");
        createddate = getIntent().getExtras().getString("createddate");
        reason = getIntent().getExtras().getString("reason");
        startdate = getIntent().getExtras().getString("startdate");
        enddate = getIntent().getExtras().getString("enddate");
        deptcode = getIntent().getExtras().getString("deptcode");
        deleted = getIntent().getExtras().getString("deleted");


        viewUserName=(TextView)findViewById(R.id.textView9);
        viewUserName.setText(username);

        viewCreatedDate=(TextView)findViewById(R.id.textView11);
        viewCreatedDate.setText(createddate);

        viewReason=(TextView)findViewById(R.id.textView14);
        viewReason.setText(reason);

        viewStartDate=(TextView)findViewById(R.id.textView16);
        viewStartDate.setText(startdate);

        viewEndDate=(TextView)findViewById(R.id.textView18);
        viewEndDate.setText(enddate);


        Button b = (Button) findViewById(R.id.button2);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApprovalDuties c = new ApprovalDuties();
                c.put(key[0],createddate);
                c.put(key[1],username);
                c.put(key[2],reason);
                c.put(key[3],startdate);
                c.put(key[4],enddate);
                c.put(key[5],deptcode);
                c.put(key[6],deleted);


                new AsyncTask<ApprovalDuties, Void, String>() {
                    @Override
                    protected String doInBackground(ApprovalDuties... params) {
                        ApprovalDuties.updateApprovalDutiesAssign(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(String result) {
                        finish();
                        Log.e("ddd","Data"+result);
                    }
                }.execute(c);
            }
        });
    }
}
