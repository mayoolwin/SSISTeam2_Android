package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RequestHistory extends Activity implements AdapterView.OnItemClickListener {


    private DatePickerDialog fromDatePickerDialog;
    private Spinner userspinner;
    SharedPreferences pref;
    List<Request> ReqList;
    Button search;
    private ListView listView;
    private String  dept_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_history);


        search=(Button)findViewById(R.id.btnserarch);
        userspinner = (Spinner) findViewById(R.id.spinner2);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        dept_code = pref.getString("dept_code", "default");



        //For Spinner getting Employee Full Name
        new AsyncTask<String, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(String... params) {
                return ApprovalDuties.listEmployeeName(params[0]);
            }

            @Override
            protected void onPostExecute(List<String> result) {


                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, result);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                userspinner.setAdapter(adapter);
            }
        }.execute(dept_code);


        listView = (ListView) findViewById(R.id.listView1);

        new AsyncTask<String, Void, List<Request>>() {

            @Override
            protected List<Request> doInBackground(String... params) {
                List<Request> r = Request.GetRequestByDeptCode(params[0]);
                return r;
            }

            @Override
            protected void onPostExecute(List<Request> result) {


                listView.setAdapter(new SimpleAdapter(getApplicationContext(), result, R.layout.request_history_row, new String[]{"Name", "Date", "Status"}, new int[]{R.id.name, R.id.date, R.id.status}));


            }
        }.execute(dept_code);

        listView.setOnItemClickListener(this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int i=userspinner.getSelectedItemPosition();
                String username=(String) userspinner.getAdapter().getItem(i);

                //Search by userName
                new AsyncTask<String, Void, List<Request>>() {
                    @Override
                    protected List<Request> doInBackground(String... params) {

                        String replaceString=params[1].replace(" ","%20");
                        return Request.GetRequestByUserName(params[0],replaceString);
                    }

                    @Override
                    protected void onPostExecute(List<Request> result) {

                        if(result.isEmpty())
                        {
                            ShowDialog();
                        }
                        ReqList=result;
                        listView.setAdapter(new SimpleAdapter(getApplicationContext(), result, R.layout.request_history_row, new String[]{"Name", "Date", "Status"}, new int[]{R.id.name, R.id.date, R.id.status}));


                    }
                }.execute(dept_code,username);

            }
        });

        // Spinner Event
        /*userspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                String username = (String) adapterView.getAdapter().getItem(i);

                new AsyncTask<String, Void, List<Request>>() {
                    @Override
                    protected List<Request> doInBackground(String... params) {

                        String replaceString=params[1].replace(" ","%20");
                        return Request.GetRequestByUserName(params[0],replaceString);
                    }

                    @Override
                    protected void onPostExecute(List<Request> result) {

                        if(result.isEmpty())
                        {
                            ShowDialog();
                        }
                        ReqList=result;
                        listView.setAdapter(new SimpleAdapter(getApplicationContext(), result, R.layout.request_history_row, new String[]{"Name", "Date", "Status"}, new int[]{R.id.name, R.id.date, R.id.status}));


                    }
                }.execute(dept_code,username);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
*/



    }

    protected void ShowDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("Nothing found!")
                .setMessage("Nothing found!")
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


        @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {

        Request req = (Request) adapterView.getAdapter().getItem(postion);

        Intent i = new Intent(this, RequestDetailList.class);

        i.putExtra("Id", req.get("Id"));
        i.putExtra("Status",req.get("Status"));
        i.putExtra("Name", req.get("Name"));


        startActivity(i);
    }




}
