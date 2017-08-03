package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MonthlyCheckDetailActivity extends AppCompatActivity {

    InventoryCheck inventoryCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_check_detail);

        Intent intent = getIntent();
        final int index = intent.getIntExtra("Index", -1);
        final HashMap<String, String> inventoryHash = (HashMap<String, String>) intent.getSerializableExtra("Details");
        inventoryCheck = InventoryCheck.fromHashMap(inventoryHash);

        final NumberPicker actualQtyText = (NumberPicker) findViewById(R.id.inventoryCheckDetailNumPicker);
        actualQtyText.setMinValue(1);
        actualQtyText.setMaxValue(10000);
        actualQtyText.setValue(inventoryCheck.getActualQuantity());
        actualQtyText.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (i != i1) {
                    inventoryCheck.setActualQuantity(i1);
                }
            }
        });
//        final EditText actualQtyEditText = (EditText) findViewById(R.id.inventoryCheckDetailActualQtyEditText);
        final EditText reasonEditText = (EditText) findViewById(R.id.inventoryCheckDetailReasonEditText);
        TextView itemNameText = (TextView) findViewById(R.id.inventoryCheckRowText1);
        itemNameText.setText(inventoryCheck.getItemDescription());
        TextView itemCatText = (TextView) findViewById(R.id.inventoryCheckRowText2);
        itemCatText.setText(inventoryCheck.getCategoryName());
        TextView recordedQtyText = (TextView) findViewById(R.id.inventoryCheckRowText3);
        recordedQtyText.setText(String.valueOf(inventoryCheck.getCurrentQuantity()));

//        actualQtyEditText.setText(String.valueOf(inventoryCheck.getActualQuantity()));
        reasonEditText.setText(inventoryCheck.getReason());

        Button confirmButton = (Button) findViewById(R.id.inventoryCheckDetailConfirmButton);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String actual = actualQtyEditText.getText().toString();
                String actual = String.valueOf(actualQtyText.getValue());
                String reason = reasonEditText.getText().toString();

                inventoryCheck.setActualQuantity(Integer.parseInt(actual));
                inventoryCheck.setReason(reason);
                HashMap<String, String> resultHash = inventoryCheck.toHashMap();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("ResultInventory", resultHash);
                returnIntent.putExtra("ResultIndex", index);
                setResult(Activity.RESULT_OK, returnIntent);
                Toast.makeText(getApplicationContext(), actual + " " + reason, Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
