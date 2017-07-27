package com.example.mayoolwin.ssisteam2_android;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.R.attr.key;

public class DelegateAuthorityActivity extends AppCompatActivity implements
        View.OnClickListener {
    SharedPreferences pref;
    Button btnDatePicker, btnTimePicker,btnDatePicker2;
    EditText txtDate,txtDate2;
    private int mYear, mMonth, mDay;
    //final static int []view = {R.id.textView2, R.id.spinner2, R.id.textView4, R.id.in_date,R.id.in_date2};
    //final static String []key = {"UserName", "DeptCode", "StartDate", "EndDate","CreatedDate","Deleted","Reason"};
    String []key = {"CreatedDate","UserName","Reason","StartDate","EndDate","DeptCode","Deleted"};
    String dept_code = "REGR";

    TextView createdDate;Spinner userName;EditText reason,startDate,endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_authority);

        /*pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name= pref.getString("username", "default");
        String role= pref.getString("role", "default");
        final String dept_code= pref.getString("dept_code", "default");*/


        createdDate = (TextView) findViewById(R.id.textView2);
        userName = (Spinner) findViewById(R.id.spinner2);
        reason = (EditText) findViewById(R.id.textView4);
        startDate = (EditText) findViewById(R.id.in_date);
        endDate = (EditText) findViewById(R.id.in_date2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        // String ct = DateFormat.getDateInstance().format(new Date());
        createdDate.setText(currentDateandTime);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        //txtDate=(EditText)findViewById(R.id.in_date);

        btnDatePicker2=(Button)findViewById(R.id.btn_date2);
        //txtDate2=(EditText)findViewById(R.id.in_date2);

        btnDatePicker.setOnClickListener(this);
        btnDatePicker2.setOnClickListener(this);

        new AsyncTask<String, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(String... params) {
                return ApprovalDuties.listEmployeeName(params[0]);
            }
            @Override
            protected void onPostExecute(List<String> result) {

                final Spinner sp1= (Spinner) findViewById(R.id.spinner2);
                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1,result);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp1.setAdapter(adapter);
            }
        }.execute(dept_code);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApprovalDuties c = new ApprovalDuties();

                Log.e("Test",userName.getSelectedItem().toString()+"Date"+createdDate.getText().toString());

                Log.e("TESTING",userName.getSelectedItem().toString()+createdDate.getText().toString()+reason.getText().toString()+startDate.getText().toString()+endDate.getText().toString()+dept_code.toString());
                c.put(key[0],createdDate.getText().toString()+"");
                c.put(key[1],userName.getSelectedItem().toString()+" ");
                c.put(key[2],reason.getText().toString()+" ");
                c.put(key[3],startDate.getText().toString()+"");
                c.put(key[4],endDate.getText().toString()+"");
                c.put(key[5],dept_code);
                c.put(key[6],"N");
/*
               c.put(key[0],"2017-01-01");
                c.put(key[1],"Jerry");
                c.put(key[2],"Testing");
                c.put(key[3],"2017-01-01");
                c.put(key[4],"2017-01-01");
                c.put(key[5],"REGR");
                c.put(key[6],"N");
*/

                new AsyncTask<ApprovalDuties, Void, Void>() {
                    @Override
                    protected Void doInBackground(ApprovalDuties... params) {
                        ApprovalDuties.createCustomer(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                    }
                }.execute(c);
            }
        });

    }
    @Override
    public void onClick(final View v) {
        if (v == btnDatePicker||v== btnDatePicker2) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            if(v==btnDatePicker){
                                startDate.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                            }
                            if(v==btnDatePicker2){
                                endDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }


                        }
                    }, mYear, mMonth, mDay);

            datePickerDialog.show();

        }
    }


}
