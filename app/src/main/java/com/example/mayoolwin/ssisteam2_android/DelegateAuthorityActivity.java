package com.example.mayoolwin.ssisteam2_android;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.R.attr.end;
import static android.R.attr.key;

public class DelegateAuthorityActivity extends AppCompatActivity implements
        View.OnClickListener {
    SharedPreferences pref;
    Button btnDatePicker, btnTimePicker,btnDatePicker2;
    EditText txtDate,txtDate2;
    private int mYear, mMonth, mDay;
    int flag = 0;

    String []key = {"CreatedDate","UserName","Reason","StartDate","EndDate","DeptCode","Deleted"};
    TextView createdDate;Spinner userName;EditText reason,startDate,endDate;

    private EditText fromDateEtxt;
    private EditText toDateEtxt;
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_authority);

        //For getting user information
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name= pref.getString("username", "default");
        String role= pref.getString("role", "default");
        final String dept_code= pref.getString("dept_code", "default");


        createdDate = (TextView) findViewById(R.id.textView2);
        userName = (Spinner) findViewById(R.id.spinner2);
        reason = (EditText) findViewById(R.id.textView4);
        startDate = (EditText) findViewById(R.id.etxt_fromdate);
        endDate = (EditText) findViewById(R.id.etxt_todate);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateandTime = sdf.format(new Date());
        createdDate.setText(currentDateandTime);

        //For Spinner getting Employee Full Name
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


        //For New Delegate Authority
        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApprovalDuties c = new ApprovalDuties();

                Log.e("Test",userName.getSelectedItem().toString()+"Date"+createdDate.getText().toString());
                Log.e("TESTING",userName.getSelectedItem().toString()+createdDate.getText().toString()+reason.getText().toString()+startDate.getText().toString()+endDate.getText().toString()+dept_code.toString());

                /*c.put(key[0],createdDate.getText().toString()+"");
                c.put(key[1],userName.getSelectedItem().toString()+" ");
                c.put(key[2],reason.getText().toString()+" ");
                c.put(key[3],startDate.getText().toString()+"");
                c.put(key[4],endDate.getText().toString()+"");
                c.put(key[5],dept_code);
                c.put(key[6],"N");*/


                String date1=startDate.getText().toString().trim();
                String date2=endDate.getText().toString().trim();
                if((date1.equals("") || date1==null)||(date2.equals("")||date2==null)) {
                    Log.e("Testing reason","reason"+reason.getText().toString());
                    flag=1;
                }
                else if(date2.compareTo(date1) == -1){
                    flag=2;
                }
                if(flag==0){
                    c.put(key[0],createdDate.getText().toString()+"");
                    c.put(key[1],userName.getSelectedItem().toString()+" ");
                    c.put(key[2],reason.getText().toString()+" ");
                    c.put(key[3],startDate.getText().toString()+"");
                    c.put(key[4],endDate.getText().toString()+"");
                    c.put(key[5],dept_code);
                    c.put(key[6],"N");
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
                }else if(flag==1){
                    Toast.makeText(getBaseContext(), "Please choose the Date", Toast.LENGTH_LONG).show();
                    flag=0;
                }else if(flag==2){
                    Toast.makeText(getBaseContext(), "Start Date should before End Date!", Toast.LENGTH_LONG).show();
                    flag=0;
                }


            }
        });

        //For Calendar
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        findViewsById();
        setDateTimeField();

    }
    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.etxt_fromdate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();

        toDateEtxt = (EditText) findViewById(R.id.etxt_todate);
        toDateEtxt.setInputType(InputType.TYPE_NULL);
    }
    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);
        toDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                toDateEtxt.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            fromDatePickerDialog.show();
        } else if(view == toDateEtxt) {
            toDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            toDatePickerDialog.show();
        }
    }


}
