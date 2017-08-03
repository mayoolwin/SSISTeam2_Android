package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.MainActivity.dept_code;
import static com.example.mayoolwin.ssisteam2_android.MainActivity.name;

public class ContactActivity extends Activity {

    private ListView listView;
    private List<WorkingPartnerModel> pList;

    // Progress Dialog
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        listView = (ListView) findViewById(R.id.listView1);



        new AsyncTask<String, Void, List<WorkingPartnerModel>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(ContactActivity.this);
                pDialog.setMessage("Loading Data ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }

            @Override
            protected List<WorkingPartnerModel> doInBackground(String... params) {
                List<WorkingPartnerModel> r = WorkingPartnerModel.getWorkingPartner(params[0],params[1]);
                return r;
            }

            @Override
            protected void onPostExecute(List<WorkingPartnerModel> result) {

                pList=result;
                // dismiss the dialog after getting all products
                pDialog.dismiss();
                // updating UI from Background Thread
                runOnUiThread(new Runnable() {
                    public void run() {
                        /**
                         * Updating parsed JSON data into ListView
                         * */
                        listView.setAdapter(new SimpleAdapter(getApplicationContext(), pList, R.layout.contact_info_row, new String[]{"UserName", "Role", "Email","PhNo"}, new int[]{R.id.name, R.id.txtrole, R.id.txtemail,R.id.txtphno}));
                    }
                });



            }
        }.execute(name,dept_code);
    }
}
