package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;

    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Testing git again
        String name= pref.getString("username", "default");
        String role= pref.getString("role", "default");
        String dept_code= pref.getString("dept_code", "default");

        if(dept_code.equals("default") || role.equals("default"))
        {

            Intent intent = new Intent(this, Login.class);
            startActivity(intent);

        }
        else
        {


        }



    }
}
