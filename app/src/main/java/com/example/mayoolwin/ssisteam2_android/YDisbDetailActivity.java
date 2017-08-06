package com.example.mayoolwin.ssisteam2_android;



import android.app.Dialog;
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

import android.widget.AdapterView;
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



public class YDisbDetailActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String item =null;
    TextView textView1;
    ListView listview1;
    TextView textView2;
    Button button;
    int resCount = 0;
    TextView textViewHead1;
    TextView textViewHead2;
    TextView textViewHead3;

    int data;
    int data2;


    SharedPreferences pref;
    String mydeptCode;


    String []keys = null;
    Integer []values = null;
    Integer[] retriveQty = null;
    //***
    String[] desc = null;
    String[] sDisburseAry = null;
    String[] sRetrieveAry = null;


    TextView textViewItem;
    TextView textViewReqQty;
    TextView editTextRetQty;

    //***
    TextView textViewDesc;


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

                String replaceString=params[0].replace(" ","%20");
                Dept dept = Dept.getDept(replaceString);
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
                                    new String[] {"itemCode","itemName","retrievedQty"},
                                    new int[]{R.id.textView1,R.id.textView2,R.id.textView3})



                    );
/*
                   DisburseDetailAdapter customAdapter = new DisburseDetailAdapter(YDisbDetailActivity.this,result);
                    listview1.setAdapter(customAdapter);*/


//                    dCustomAdapter adapter = new dCustomAdapter(YDisbDetailActivity.this,result);
//                    listview1.setAdapter(adapter);

                }
            }

        }.execute(item);

        listview1.setOnItemClickListener(this);

        button.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                resCount = listview1.getCount();
                keys = new String[resCount];
                values = new Integer[resCount];
                retriveQty = new Integer[resCount];
                final List<YDisburseDetailModel> objList = new ArrayList<YDisburseDetailModel>();
                //***
                 desc = new String[resCount];
                sRetrieveAry = new String[resCount];
                sDisburseAry = new String[resCount];


              //StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);


                try {

                    for (int i = 0; i < listview1.getAdapter().getCount(); i++) {

                        textViewItem = (TextView) getViewByPosition(i,listview1).findViewById(R.id.textView1);
                        keys[i] = textViewItem.getText().toString();


                        //***
                        textViewDesc = (TextView) getViewByPosition(i,listview1).findViewById(R.id.textView2);
                        desc[i] = textViewDesc.getText().toString();


                        textViewReqQty = (TextView)getViewByPosition(i,listview1).findViewById(R.id.textView3);
                        String s1 =textViewReqQty.getText().toString();
                        sRetrieveAry[i] = s1;
                        Integer i1 = Integer.parseInt(s1);
                        retriveQty[i] = i1;


                        editTextRetQty = (TextView) getViewByPosition(i,listview1).findViewById(R.id.textView4);
                        String s2 = editTextRetQty.getText().toString();
                        sDisburseAry[i] = s2;
                        Integer i2 = Integer.parseInt(s2);
                        values[i] = i2;
                    }
                    //Discrepency

                    //Integer[] discrepenceyAry = new Integer[resCount];
                    //HashMap<String, Integer> discrepencyMap = new HashMap<String, Integer>();
                    List<YDisburseDetailModel> discreAryList = new ArrayList<YDisburseDetailModel>();

                    if (values.length > 0) {

                        for (int j = 0; j < keys.length; j++) {

                                String s1 = Integer.toString(values[j]);
                                objList.add(new YDisburseDetailModel(s1, keys[j],desc[j], sRetrieveAry[j]));



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
                                Toast.makeText(YDisbDetailActivity.this, "successfully disbursed!", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }.execute(objList);

                    }


                } catch (NumberFormatException e) {

                    Toast.makeText(YDisbDetailActivity.this, "Fill Quantity!", Toast.LENGTH_SHORT).show();

                }


                    //***send to Discrepency
                Intent intent = new Intent(YDisbDetailActivity.this, FileDiscrepancyActivity.class);
                boolean discrepancyFound = false;
                ArrayList<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();
                for (YDisburseDetailModel eachDisburse : objList )
                {
                    int retrievedQty = Integer.parseInt(eachDisburse.get("retrievedQty"));
                    int disbursedQty = Integer.parseInt(eachDisburse.get("disbursedQty"));
                    if (retrievedQty != disbursedQty) {
                        HashMap<String, String> m = eachDisburse.toHashMap();
                        maps.add(m);
                        discrepancyFound = true;
                    }
                }
                if (discrepancyFound) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("List", maps);
                    intent.putExtra("Bundle", bundle);
                    startActivity(intent);
                    Log.v("ZZZ","discre" + maps.size());
                } else {
                    finish();
                }


            }

        });

    }

    // View list
    public static View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    //listview click
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {


        final Dialog d = new Dialog(this,R.style.AppTheme_Dark_Dialog);
        d.setTitle(getString(R.string.customdialogtitle));

        d.setContentView(R.layout.customdialog);
        d.setCancelable(false);
        editTextRetQty = (TextView)view.findViewById(R.id.textView4);
        textViewReqQty = (TextView)view.findViewById(R.id.textView3);
        final EditText qty=(EditText) d.findViewById(R.id.edit_qty);
        qty.setText(editTextRetQty.getText().toString());
        Button btn=(Button)d.findViewById(R.id.ok);
        Button cancel=(Button)d.findViewById(R.id.cancel);


        //TextView t = (TextView) d.findViewById(R.id.txtqty);




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(qty.getText().toString().isEmpty())
                {
                    qty.setError("Fill disbursed quantity!");

                }
                else
                {
                    data=Integer.parseInt(qty.getText().toString());
                    data2=Integer.parseInt(textViewReqQty.getText().toString());
                    if(data>data2)
                    {
                        Toast.makeText(YDisbDetailActivity.this, "Cannot disburse more than retrieved quantity!", Toast.LENGTH_SHORT).show();
                    }

                    else
                    {   editTextRetQty.setText(qty.getText().toString());
                        d.dismiss();}
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                d.dismiss();}

        });
        d.show();

    }

}