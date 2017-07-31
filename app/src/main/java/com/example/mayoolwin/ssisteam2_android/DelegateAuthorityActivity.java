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
import android.widget.Toast;

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
    int flag = 0;
    //final static int []view = {R.id.textView2, R.id.spinner2, R.id.textView4, R.id.in_date,R.id.in_date2};
    //final static String []key = {"UserName", "DeptCode", "StartDate", "EndDate","CreatedDate","Deleted","Reason"};
    //String []key = {"CreatedDate","UserName","Reason","StartDate","EndDate","DeptCode","Deleted"};
    String []key = {"CreatedDate","UserName","Reason","StartDate","EndDate","DeptCode","Deleted"};
    // String dept_code = "REGR";

    TextView createdDate;Spinner userName;EditText reason,startDate,endDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delegate_authority);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String name= pref.getString("username", "default");
        String role= pref.getString("role", "default");
        final String dept_code= pref.getString("dept_code", "default");


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


               String cDate= createdDate.getText().toString();
               String user= userName.getSelectedItem().toString();
               String why= reason.getText().toString();
                String sDate=startDate.getText().toString();
                String eDate=endDate.getText().toString();
                ApprovalDuties c = new ApprovalDuties(user,sDate,eDate,dept_code,cDate,"N",why);



                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // Make sure user insert date into edittext in this format.

                Date strDate,enDate;

                try{
                    String dob_var1=(startDate.getText().toString());
                    String dob_var2=(endDate.getText().toString());

                    strDate = formatter.parse(dob_var1);
                    enDate = formatter.parse(dob_var2);
                    if(strDate.before(enDate)){
                        flag = 0;
                    }else{
                        flag = 1;
                    }

                }catch (java.text.ParseException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    Log.i("Not Correct Date Format", e.toString());
                }

/*
               c.put(key[0],"2017-01-01");
                c.put(key[1],"Jerry");
                c.put(key[2],"Testing");
                c.put(key[3],"2017-01-01");
                c.put(key[4],"2017-01-01");
                c.put(key[5],"REGR");
                c.put(key[6],"N");
*/
                if(flag==0){
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
                }else{
                    Toast.makeText(getBaseContext(), "Start Date should before End Date", Toast.LENGTH_LONG).show();
                }

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
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();

        }
    }


}
