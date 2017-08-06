package com.example.mayoolwin.ssisteam2_android;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class YRetrieveActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    SharedPreferences pref;
    String loginUserName;
    ListView listview;
    Button btnConfrim;
    int resCount = 0;
    TextView textViewItem;
    TextView textViewReqQty;
    TextView editTextRetQty;
    String[] keys = null;
    String[] values = null;
    String[] reqQtys = null;
    int data;
    int data2;

    private String arrTemp[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yretrieve);



        pref = PreferenceManager.getDefaultSharedPreferences(this);
        loginUserName = pref.getString("username", "default");

        listview = (ListView) findViewById(R.id.listView);

        new AsyncTask<Void, Void, List<YRetrieveModel>>() {
            @Override
            protected List<YRetrieveModel> doInBackground(Void... params) {
                return YRetrieveModel.listEachItemTotalQty(loginUserName);
            }

            @Override
            protected void onPostExecute(List<YRetrieveModel> result) {

                arrTemp =  new String[result.size()];

                if(result.size()==0)
                {
                    Toast.makeText(YRetrieveActivity.this, "Don't have  Retrieve Item!!", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    listview.setAdapter(
                            new SimpleAdapter
                                    (YRetrieveActivity.this, result, R.layout.y_row,
                                            new String[]{"itemCode","itemDes", "totalQty"},
                                            new int[]{R.id.textView1,R.id.textView2, R.id.textView3})
                    );


                 // ListViewAdapter customAdapter = new ListViewAdapter(YRetrieveActivity.this,result);
                   // listview.setAdapter(customAdapter);


                }
            }
        }.execute();
        listview.setOnItemClickListener(this);


        //Update to webservice
        btnConfrim = (Button) findViewById(R.id.buttonConfirm);
        btnConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resCount = listview.getCount();
                List<YRetrieveModel> objList = new ArrayList<YRetrieveModel>();
                keys = new String[resCount];
                values = new String[resCount];
                reqQtys = new String[resCount];

               // StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

                try {


                    for (int i = 0; i < listview.getAdapter().getCount(); i++)
                    {
                    textViewItem = (TextView) YDisbDetailActivity.getViewByPosition(i,listview).findViewById(R.id.textView1);
                    keys[i] = textViewItem.getText().toString();

                    textViewReqQty = (TextView) YDisbDetailActivity.getViewByPosition(i,listview).findViewById(R.id.textView3);
                    reqQtys[i] = textViewReqQty.getText().toString();

                    editTextRetQty = (TextView) YDisbDetailActivity.getViewByPosition(i,listview).findViewById(R.id.textView4);
                    values[i] = editTextRetQty.getText().toString();
                     }
                    ArrayList<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();

                    if (values.length > 0)
                    {
                          for (int j = 0; j < keys.length; j++)
                          {

                                    objList.add(new YRetrieveModel(keys[j],"", values[j],reqQtys[j]));

                          }

                        new AsyncTask<List<YRetrieveModel>, Void, Void>() {

                            @Override

                            protected Void doInBackground(List<YRetrieveModel>... params) {


                                YRetrieveModel.updateRetrieveQty(params[0], loginUserName);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void result) {
                                Toast.makeText(YRetrieveActivity.this, "Successfully Retrieved", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }.execute(objList);


                     }

                } catch (NumberFormatException e) {
                    Toast.makeText(YRetrieveActivity.this, "Fill Quantity!", Toast.LENGTH_SHORT).show();

                }
                catch (NullPointerException e) {
                    Toast.makeText(YRetrieveActivity.this, "Fill Something!", Toast.LENGTH_SHORT).show();
                }



                    Intent intent = new Intent(YRetrieveActivity.this, FileDiscrepancyActivity.class);
                    ArrayList<HashMap<String, String>> maps = new ArrayList<HashMap<String, String>>();
                    for (YRetrieveModel model : objList )
                    {
                        int retrievedQty = Integer.parseInt(model.get("retrieveQty"));
                        int disbursedQty = Integer.parseInt(model.get("totalQty"));
                        if (retrievedQty != disbursedQty) {
                            HashMap<String, String> m = model.toHashMap();
                            maps.add(m);
                        }
                    }
                    if(maps.size()>0)
                    {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("List", maps);
                        intent.putExtra("Bundle", bundle);
                        startActivity(intent);
                        Log.v("ZZZ","discre" + maps.size());
                        finish();
                    }
                    else
                    {
                        finish();
                    }



            }
        });

    }



    //listview click
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {


        final Dialog d = new Dialog(this,R.style.AppTheme_Dark_Dialog);
        d.setTitle(getString(R.string.customdialogtitle));

        d.setContentView(R.layout.customdialog);
        d.setCancelable(false);
        editTextRetQty = (TextView) YDisbDetailActivity.getViewByPosition(i,listview).findViewById(R.id.textView4);
        textViewReqQty = (TextView)YDisbDetailActivity.getViewByPosition(i,listview).findViewById(R.id.textView3);
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
                    qty.setError("Fill retrieved quantity!");

                }
                else
                {
                    data=Integer.parseInt(qty.getText().toString());
                    data2=Integer.parseInt(textViewReqQty.getText().toString());
                    if(data>data2)
                    {
                        Toast.makeText(YRetrieveActivity.this, "Cannot retrieve more than requested quantity!", Toast.LENGTH_SHORT).show();
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
