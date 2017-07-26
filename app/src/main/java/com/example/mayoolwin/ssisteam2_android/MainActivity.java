package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.PreferenceChangeEvent;


public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;

    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Testing git again
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name= pref.getString("username", "default");
        String role= pref.getString("role", "default");
        String dept_code= pref.getString("dept_code", "default");

        TextView roleTextView = (TextView)findViewById(R.id.roleTextView);

        Button logoutButton = (Button)findViewById(R.id.logoutButton);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }


        });

        roleTextView.setText(role);
        if(dept_code.equals("default") || role.equals("default"))
        {
            Intent intent = new Intent(this, Login.class);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clerk_monthly:
                startActivity(new Intent(this, MonthlyCheckActivity.class));
                return true;
            case R.id.logout:
                Logout();
        }
        return true;
    }

    public void Logout() {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username","default");
        editor.putString("role", "default");
        editor.putString("dept_code", "default");
        editor.commit();
        startActivity(intent);
        finish();
    }
}
