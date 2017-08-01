package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YDisbDetailActivity extends AppCompatActivity {

    String item =null;
    TextView textView1;
    ListView listview1;
    TextView textView2;
    Button button;
    int resCount = 0;
    TextView textViewHead1;
    TextView textViewHead2;
    TextView textViewHead3;

    SharedPreferences pref;
    String mydeptCode;

    String []keys = null;
    Integer []values = null;
    Integer[] retriveQty = null;

    TextView textViewItem;
    TextView textViewReqQty;
    EditText editTextRetQty;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ydisb_detail);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        final String loginUserName= pref.getString("username", "default");


        textView1 =(TextView) findViewById(R.id.textView1);
        listview1 =(ListView) findViewById(R.id.listView1);
        button = (Button) findViewById(R.id.btnSIgnOff);
        //textView2 =(TextView) findViewById(R.id.textView2);
        textViewHead1 = (TextView) findViewById(R.id.textViewHead1);
        textViewHead2 = (TextView) findViewById(R.id.textViewHead2);
        textViewHead3 = (TextView) findViewById(R.id.textViewHead3);

        item = getIntent().getExtras().getString("deptName");


        new AsyncTask<String, Void, List<YDisburseDetailModel>>() {
            @Override
            protected List<YDisburseDetailModel> doInBackground(String... params) {
                Dept dept = Dept.getDept(item);
                mydeptCode = dept.get("DeptCode");
                Log.v("QQQ","***"+mydeptCode);
                return YDisburseDetailModel.listDisDeptDetail(loginUserName,mydeptCode);
            }
            @Override
            protected void onPostExecute(List<YDisburseDetailModel> result) {
                textView1.setText(item+" Department");

                if(result.size() ==  0 )
                {
                    textViewHead1.setText("");
                    textViewHead2.setText("");
                    textViewHead3.setText("");
                    button.setEnabled(false);
                    Toast.makeText(YDisbDetailActivity.this, "No Disburment Form For "+item+" Department",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    listview1.setAdapter(
                            new SimpleAdapter(YDisbDetailActivity.this, result, R.layout.y_row,
                                    new String[] {"itemName","retrievedQty"},
                                    new int[]{R.id.textView2,R.id.textView3})
                    );
                }

            }
        }.execute(item);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                resCount = listview1.getCount();
                keys = new String[resCount];
                values = new Integer[resCount];
                retriveQty = new Integer[resCount];
                final List<YDisburseDetailModel> objList = new ArrayList<YDisburseDetailModel>();

                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

                try {


                    for (int i = 0; i < listview1.getAdapter().getCount(); i++) {
                        textViewItem = (TextView) listview1.getChildAt(i).findViewById(R.id.textView2);
                        keys[i] = textViewItem.getText().toString();

                        textViewReqQty = (TextView) listview1.getChildAt(i).findViewById(R.id.textView3);
                        Integer i1 = Integer.parseInt(textViewReqQty.getText().toString());
                        retriveQty[i] = i1;

                        editTextRetQty = (EditText) listview1.getChildAt(i).findViewById(R.id.editText1);
                        String s = editTextRetQty.getText().toString();
                        Integer i2 = Integer.parseInt(s);
                        values[i] = i2;
                    }


                    //Discrepency
                    Integer[] discrepenceyAry = new Integer[resCount];
                    HashMap<String, Integer> discrepencyMap = new HashMap<String, Integer>();

                    if (values.length > 0) {
                        for (int j = 0; j < keys.length; j++) {
                            if (values[j] == retriveQty[j]) {
                                String s1 = Integer.toString(values[j]);
                                objList.add(new YDisburseDetailModel(s1, keys[j], ""));
                                Toast.makeText(YDisbDetailActivity.this, "successfully disbursed!", Toast.LENGTH_SHORT).show();

                            } else if (values[j] < retriveQty[j]) {
                                String s2 = Integer.toString(values[j]);
                                objList.add(new YDisburseDetailModel(s2, keys[j], ""));
                                Toast.makeText(YDisbDetailActivity.this, "successfully disbursed!", Toast.LENGTH_SHORT).show();

                                //Discrepency
                                discrepenceyAry[j] = retriveQty[j] - values[j];
                                discrepencyMap.put(keys[j], discrepenceyAry[j]);

                            } else if (values[j] > retriveQty[j]) {
                                Toast.makeText(YDisbDetailActivity.this, "Cannot disburse more than retrieved quantity!", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {

                        Toast.makeText(YDisbDetailActivity.this, "Please Fill Quantity!!", Toast.LENGTH_SHORT).show();
                    }


                    new AsyncTask<List<YDisburseDetailModel>, Void, Void>() {

                        @Override
                        protected Void doInBackground(List<YDisburseDetailModel>... params) {

                            Log.v("GGG", "=====" + objList);
                            Log.v("PPP", "My Depart Code" + mydeptCode);
                            YDisburseDetailModel.UpdateDisburseQty(params[0], loginUserName, mydeptCode);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void result) {

                            finish();
                        }
                    }.execute(objList);

                    YDisburseDetailModel.UpdateDisburseQty(objList, loginUserName, mydeptCode);

                } catch (NumberFormatException e) {
                    Toast.makeText(YDisbDetailActivity.this, "Fill Quantity!", Toast.LENGTH_SHORT).show();

                } catch (NullPointerException e) {
                    Toast.makeText(YDisbDetailActivity.this, "Fill Something!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}


