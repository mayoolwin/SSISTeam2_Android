package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MonthlyCheckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_check);

        Button mcNextButton = (Button)findViewById(R.id.mc_next_button);

        mcNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MonthlyCheckConfirmActivity.class);
                startActivity(intent);
            }
        });
    }
}
