package com.example.mayoolwin.ssisteam2_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import static android.R.attr.author;

public class YRetrieveActivity extends AppCompatActivity {

    SharedPreferences pref;
    String loginUserName;
    ListView listview;
    Button btnConfrim;
    TextView textViewTest;
    TextView textViewTest1;
    int resCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yretrieve);


        pref = PreferenceManager.getDefaultSharedPreferences(this);
        loginUserName= pref.getString("username", "default");

        listview = (ListView) findViewById(R.id.listView);
        textViewTest = (TextView) findViewById(R.id.textViewTest);
        textViewTest1 = (TextView) findViewById(R.id.textViewTest1);

        new AsyncTask<Void, Void, List<YRetrieveModel>>() {
            @Override
            protected List<YRetrieveModel> doInBackground(Void... params) {
                return YRetrieveModel.listEachItemTotalQty(loginUserName);
            }

            @Override
            protected void onPostExecute(List<YRetrieveModel> result) {

                listview.setAdapter(
                        new SimpleAdapter
                                (YRetrieveActivity.this, result, R.layout.y_row,
                                        new String[]{"itemDes", "totalQty"},
                                        new int[]{R.id.textView2, R.id.textView3})
                );

            }
        }.execute();


        //Update to webservice
        btnConfrim = (Button) findViewById(R.id.buttonConfirm);
        btnConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //
                resCount = listview.getCount();
                final List<YRetrieveModel> objList = new ArrayList<YRetrieveModel>();

                TextView textViewItem;
                TextView textViewReqQty;
                EditText editTextRetQty;
                //
                String []keys = new String[resCount];
                String []values = new String[resCount];
                String[] reqQtys = new String[resCount];


                for(int i = 0; i < listview.getAdapter().getCount(); i++)
                {
                    textViewItem =(TextView) listview.getChildAt(i).findViewById(R.id.textView2);
                    keys[i] = textViewItem.getText().toString();

                    textViewReqQty =(TextView) listview.getChildAt(i).findViewById(R.id.textView3);
                    reqQtys[i] = textViewReqQty.getText().toString();

                    editTextRetQty=(EditText) listview.getChildAt(i).findViewById(R.id.editText1);
                    values[i] = editTextRetQty.getText().toString();
                }
                for(int j = 0; j < keys.length; j++)
                {

                    if(Integer.parseInt(values[j]) <= Integer.parseInt(reqQtys[j]))
                    {
                        objList.add(new YRetrieveModel(keys[j], values[j],""));
                        textViewTest1.setText("successfully retrieved!");
                    }
                    else if ((Integer.parseInt(values[j])>  Integer.parseInt(reqQtys[j])))
                    {
                        textViewTest1.setText("Cannot retrieve more than requested quantity!");
                        break;
                    }
                }


                new AsyncTask<List<YRetrieveModel>, Void, Void>() {
                    @Override
                    protected Void doInBackground(List<YRetrieveModel>... params) {
                        Log.v("GGG","====="+objList);
                        YRetrieveModel.updateRetrieveQty(params[0],loginUserName);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {

                        finish();
                    }
                }.execute(objList);

            }
        });
    }
}