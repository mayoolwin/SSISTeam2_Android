package com.example.mayoolwin.ssisteam2_android;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

import static com.example.mayoolwin.ssisteam2_android.R.color.base;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<WorkingPartnerModel> pList;

    // Progress Dialog
    private ProgressDialog pDialog;
    SharedPreferences pref;
    public static String dept_code;
    public static String name;
    Button b;

    private static final String PROFILE_SPEC = "Profile";
    // TabSpec Names
    private static final String INBOX_SPEC = "Contact";
    private static final String OUTBOX_SPEC = "Outbox";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        name= pref.getString("username", "default");
        String role= pref.getString("role", "default");
        String flag = pref.getString("flag", "N");
        dept_code= pref.getString("dept_code", "default");

        TextView txtname=(TextView)findViewById(R.id.name);
        TextView txtrole=(TextView)findViewById(R.id.txtrole);


        txtname.setText(name);
        txtrole.setText(role + flag);



        if(dept_code.equals("default") || role.equals("default"))
        {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        listView = (ListView) findViewById(R.id.listView1);



        new AsyncTask<String, Void, List<WorkingPartnerModel>>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(MainActivity.this);
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
                        registerForContextMenu(listView);
                    }
                });



            }
        }.execute(name,dept_code);


        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("Colleagues");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Colleagues");
        host.addTab(spec);

        //Tab 2
        /*spec = host.newTabSpec("Recent Activity");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Inbox");
        host.addTab(spec);*/

        //Tab 3
        spec = host.newTabSpec("Profile");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Profile");
        host.addTab(spec);




    }

    @Override
    public void onBackPressed()
    {
        //do nothing;
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String test = pref.getString("role", "employee");
//        if (test.equals("Clerk"))
//            getMenuInflater().inflate(R.menu.clerk_menu, menu);
//        else if (test.equals("Employee"))
//            getMenuInflater().inflate(R.menu.employee_menu, menu);
//        else if (test.equals("DeptHead"))
//            getMenuInflater().inflate(R.menu.departmenthead_menu,menu);
//        else if (test.equals("Supervisor"))
//            getMenuInflater().inflate(R.menu.supervisor_menu,menu);
//        return true;
        String flag = pref.getString("flag","Y");
        if (test.equals("Clerk")&&flag.equals("N"))
            getMenuInflater().inflate(R.menu.clerk_menu, menu);
        else if (test.equals("Employee")&&flag.equals("N"))
            getMenuInflater().inflate(R.menu.employee_menu, menu);
        else if (test.equals("DeptHead")&&flag.equals("N"))
            getMenuInflater().inflate(R.menu.departmenthead_menu,menu);
        else if (test.equals("Manager")&&flag.equals("Y"))
            getMenuInflater().inflate(R.menu.store_manager_menu,menu);
        else if (test.equals("DeptHead")&&flag.equals("Y"))
            getMenuInflater().inflate(R.menu.delegateauthority_menu,menu);
        else if (test.equals("Supervisor")&&flag.equals("N"))
            getMenuInflater().inflate(R.menu.supervisor_menu,menu);
        return true;
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clerk_monthly:
                startActivity(new Intent(this, MonthlyCheckActivity.class));
                return true;

            case R.id.dept_authority:
               // startActivity(new Intent(this, DelegateAuthorityActivity.class));
                checkApprovalDutiesEixstence(dept_code);
                return true;

            case R.id.view_request:
                startActivity(new Intent(this, ViewAllPendingRequestActivity.class));
                return true;

            case R.id.make_request:
                startActivity(new Intent(this,MakeNewRequestActivity.class));
                return true;

            case R.id.logout:
                Logout();
        }
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.clerk_monthly:
                startActivity(new Intent(this, MonthlyCheckActivity.class));
                return true;

            case R.id.dept_authority:
               // startActivity(new Intent(this, DelegateAuthorityActivity.class));
                checkApprovalDutiesEixstence(dept_code);
                return true;

            case R.id.make_request:
                startActivity(new Intent(this, MakeNewRequestActivity.class));
                return true;

            case R.id.view_request:
                startActivity(new Intent(this, ViewAllPendingRequestActivity.class));
                return true;

            case R.id.clerk_retrieval:
                startActivity(new Intent(this, YRetrieveActivity.class));
                return true;

            case R.id.clerk_disbursement:
                startActivity(new Intent(this, YDisburseActivity.class));
                return true;

            case R.id.request_history:
                startActivity(new Intent(this, RequestHistory.class));
                return true;



            case R.id.view_adjustment:
                startActivity(new Intent(this, ViewAllAdjustment.class));
                return true;
            case R.id.supervisor_view_adjustment:
                startActivity(new Intent(this, ViewAllAdjustment.class));
                return true;
            case R.id.logout:
                Logout();
        }
        return super.onOptionsItemSelected(item);
    }



    public void Logout() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("username","default");
        editor.putString("role", "default");
        editor.putString("dept_code", "default");
        editor.commit();
        startActivity(intent);
        finish();
    }

    public void checkApprovalDutiesEixstence(String dept_code){

        new AsyncTask<String, Void, ApprovalDuties>() {
            @Override
            protected ApprovalDuties doInBackground(String... params) {
                Log.e("Param",params[0]);
                return ApprovalDuties.checkApprovalDuties(params[0]);
            }
            @Override
            protected void onPostExecute(ApprovalDuties result) {
                Log.e("OnPostExecute", "Execute" + result);
                if (result == null) {
                    Intent i = new Intent(MainActivity.this, DelegateAuthorityActivity.class);
                    startActivity(i);
                } else {
                    if (result.get("Deleted").equals("Y")) {
                        Intent i = new Intent(MainActivity.this, DelegateAuthorityActivity.class);
                        startActivity(i);
                    }
                    if (result.get("Deleted").equals("N")) {
                        Intent i = new Intent(MainActivity.this, DeleteAuthorityCheck.class);
                        i.putExtra("createddate", result.get("CreatedDate"));
                        i.putExtra("username", result.get("UserName"));
                        i.putExtra("reason", result.get("Reason"));
                        i.putExtra("startdate", result.get("StartDate"));
                        i.putExtra("enddate", result.get("EndDate"));
                        i.putExtra("deptcode", result.get("DeptCode"));
                        i.putExtra("deleted", result.get("Deleted"));
                        startActivity(i);
                    }
                }
            }
        }.execute(dept_code);
    }



    // Content Menu

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title

    }
    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int pos = info.position;

        String ph_no=pList.get(pos).get("PhNo");
        Uri uri;
        Intent i;

        if(item.getTitle()=="Call"){
            String tel="tel:"+ph_no;
            uri = Uri.parse(tel);
            i = new Intent(Intent.ACTION_DIAL, uri);
            startActivity(i);
        }
        else{
            return false;
        }
        return true;
    }

}
