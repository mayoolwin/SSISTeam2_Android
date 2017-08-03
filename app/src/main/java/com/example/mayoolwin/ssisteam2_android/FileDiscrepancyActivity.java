package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileDiscrepancyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_discrepancy);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("Bundle");
        ArrayList<HashMap<String, String>> mapsList = (ArrayList<HashMap<String, String>>) args.get("List");
        final ArrayList<FileDiscrepancy> fileDiscrepancies = FileDiscrepancy.FromDisbursement(mapsList);
        final ListView fileDiscrepancyList = (ListView) findViewById(R.id.fileDiscrepancyList);
        fileDiscrepancyList.setAdapter(new SimpleAdapter(getApplicationContext(), fileDiscrepancies, R.layout.row_file_discrepancy,
                new String[]{"ItemName", "AdjustedQty"}, new int[]{R.id.fileDiscrepancyNameLabel, R.id.fileDiscrepancyAdjustedQty}));


        Button confirmButton = (Button) findViewById(R.id.fileDiscrepancyConfirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = "";
                for (int i = 0; i < fileDiscrepancyList.getAdapter().getCount(); i++) {
                    EditText reasonEditText = (EditText) fileDiscrepancyList.getChildAt(i).findViewById(R.id.fileDiscrepancyReasonEdit);
                    fileDiscrepancies.get(i).put("Reason", reasonEditText.getText().toString());
                    result += fileDiscrepancies.get(i).get("Reason");
                }
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                final String username = pref.getString("username", "default");

                new AsyncTask<ArrayList<FileDiscrepancy>, Void, Void>() {
                    @Override
                    protected Void doInBackground(ArrayList<FileDiscrepancy>... params) {
                        FileDiscrepancy.UpdateFileDiscrepancies(params[0], username);

                        return null;
                    }
                }.execute(fileDiscrepancies);
            }
        });

    }
}
