package com.example.mayoolwin.ssisteam2_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondAcitvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_acitvity);
        String name = getIntent().getExtras().getString("UserName");
        TextView view=(TextView) findViewById(R.id.welcome);
        view.setText(name);
    }
}
