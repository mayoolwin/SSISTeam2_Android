package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
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
import android.widget.TextView;

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
    TextView textViewError;
    SharedPreferences pref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ydisb_detail);
        //
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        final String loginUserName= pref.getString("username", "default");
        final String deptCode= pref.getString("dept_code", "default");

        textView1 =(TextView) findViewById(R.id.textView1);
        listview1 =(ListView) findViewById(R.id.listView1);
        //textView2 =(TextView) findViewById(R.id.textView2);
        textViewError =(TextView) findViewById(R.id.textViewError);

         item = getIntent().getExtras().getString("deptName");

        new AsyncTask<String, Void, List<YDisburseDetailModel>>() {
            @Override
            protected List<YDisburseDetailModel> doInBackground(String... params) {
                return YDisburseDetailModel.listDisDeptDetail(loginUserName,deptCode);
            }
            @Override
            protected void onPostExecute(List<YDisburseDetailModel> result) {
                textView1.setText(item+" Department");

            listview1.setAdapter(
                    new SimpleAdapter(YDisbDetailActivity.this, result, R.layout.y_row,
                            new String[] {"itemName","retrievedQty"},
                            new int[]{R.id.textView2,R.id.textView3})
            );

            }
        }.execute(item);

        button = (Button) findViewById(R.id.btnSIgnOff);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resCount = listview1.getCount();

                //
                TextView textViewItem;
                TextView textViewReqQty;
                EditText editTextRetQty;
                //
                String []keys = new String[resCount];
                String []values = new String[resCount];
                String[] retriveQty = new String[resCount];
                List<YDisburseDetailModel> objList = new ArrayList<YDisburseDetailModel>();


                for(int i = 0; i < listview1.getAdapter().getCount(); i++)
                {
                    textViewItem =(TextView) listview1.getChildAt(i).findViewById(R.id.textView2);
                    keys[i] = textViewItem.getText().toString();

                    textViewReqQty =(TextView) listview1.getChildAt(i).findViewById(R.id.textView3);
                    retriveQty[i] = textViewReqQty.getText().toString();

                    editTextRetQty=(EditText) listview1.getChildAt(i).findViewById(R.id.editText1);
                    values[i] = editTextRetQty.getText().toString();
                }

                //Discrepency
                Integer []discrepenceyAry = new Integer[resCount];
                HashMap<String,Integer> discrepencyMap = new HashMap<String, Integer>();
                for(int j = 0; j < keys.length; j++)
                {
                    if(Integer.parseInt(values[j]) == Integer.parseInt(retriveQty[j]))
                    {
                        objList.add(new YDisburseDetailModel(keys[j],"",values[j]));
                        textViewError.setText("successfully disbursed!");

                    }
                    else if (Integer.parseInt(values[j]) < Integer.parseInt(retriveQty[j]))
                    {
                        objList.add(new YDisburseDetailModel(keys[j],"",values[j]));
                        textViewError.setText("successfully disbursed!");

                        //Discrepency
                        discrepenceyAry[j]   = Integer.parseInt(retriveQty[j]) - Integer.parseInt(values[j]);
                        discrepencyMap.put(keys[j] , discrepenceyAry [j]);


                    }
                    else if ((Integer.parseInt(values[j])>  Integer.parseInt(retriveQty[j])))
                    {
                        textViewError.setText("Cannot disburse more than retrieved quantity!");
                        break;
                    }

                }

                if (discrepencyMap.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), FileDiscrepancyActivity.class);
                    intent.putExtra("List", discrepencyMap);
                    startActivity(intent);
                }

                //Discrepency
                TextView textviewDiscre = (TextView) findViewById(R.id.textviewDiscre);
                textviewDiscre.setText((discrepencyMap.entrySet()).toString()+" / ");


                YDisburseDetailModel.UpdateDisburseQty(objList,loginUserName,deptCode);

            }
        });
    }
}
