package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

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
    }
}
