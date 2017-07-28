package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import static com.example.mayoolwin.ssisteam2_android.R.color.base;


public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;
    String dept_code;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Testing git again


        //Yin Test
        Button btntest = (Button) findViewById(R.id.btnStationary);
        btntest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent yintent = new Intent(getApplicationContext(),YDisburseActivity.class);
                startActivity(yintent);
            }
        });
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name= pref.getString("username", "default");
        String role= pref.getString("role", "default");
        dept_code= pref.getString("dept_code", "default");
        TextView roleTextView = (TextView)findViewById(R.id.roleTextView);
        roleTextView.setText(role);
        if(dept_code.equals("default") || role.equals("default"))
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String test = pref.getString("role", "employee");
        if (test.equals("Clerk"))
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
        else if (test.equals("Employee"))
            getMenuInflater().inflate(R.menu.employee_menu, menu);
        else if (test.equals("DeptHead"))
            getMenuInflater().inflate(R.menu.departmenthead_menu,menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clerk_monthly:
                startActivity(new Intent(this, MonthlyCheckActivity.class));
                return true;

            case R.id.dept_authority:
               // startActivity(new Intent(this, DelegateAuthorityActivity.class));
                checkApprovalDutiesEixstence(dept_code);
                return true;

            case R.id.view_request:
                startActivity(new Intent(this, ViewAllPendingRequestActivity.class));
                return true;

            case R.id.make_request:
                startActivity(new Intent(this,MakeNewRequestActivity.class));
                return true;

            case R.id.logout:
                Logout();
        }
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clerk_monthly:
                startActivity(new Intent(this, MonthlyCheckActivity.class));
                return true;

            case R.id.dept_authority:
               // startActivity(new Intent(this, DelegateAuthorityActivity.class));
                checkApprovalDutiesEixstence(dept_code);
                return true;

            case R.id.view_request:
                startActivity(new Intent(this, ViewAllPendingRequestActivity.class));
                return true;
            case R.id.logout:
                Logout();
        }
        return super.onOptionsItemSelected(item);
    }



    public void Logout() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username","default");
        editor.putString("role", "default");
        editor.putString("dept_code", "default");
        editor.commit();
        startActivity(intent);
        finish();
    }

    public void checkApprovalDutiesEixstence(String dept_code){

        new AsyncTask<String, Void, ApprovalDuties>() {
            @Override
            protected ApprovalDuties doInBackground(String... params) {
                Log.e("Param",params[0]);
                return ApprovalDuties.checkApprovalDuties(params[0]);
            }
            @Override
            protected void onPostExecute(ApprovalDuties result) {
                if(result.get("Deleted").equals("Y")){
                    Intent i = new Intent(MainActivity.this,DelegateAuthorityActivity.class);
                    startActivity(i);
                }if(result.get("Deleted").equals("N")){
                    Intent i = new Intent(MainActivity.this,DeleteAuthorityCheck.class);
                    i.putExtra("createddate", result.get("CreatedDate"));
                    i.putExtra("username", result.get("UserName"));
                    i.putExtra("reason", result.get("Reason"));
                    i.putExtra("startdate", result.get("StartDate"));
                    i.putExtra("enddate", result.get("EndDate"));
                    i.putExtra("deptcode",result.get("DeptCode"));
                    i.putExtra("deleted",result.get("Deleted"));
                    startActivity(i);
                }
            }
        }.execute(dept_code);
    }
}
