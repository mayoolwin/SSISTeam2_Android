package com.example.mayoolwin.ssisteam2_android;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class YRetrieveActivity extends AppCompatActivity {

    SharedPreferences pref;
    String loginUserName;
    ListView listview;
    Button btnConfrim;
    int resCount = 0;
    TextView textViewItem;
    TextView textViewReqQty;
    EditText editTextRetQty;
    String[] keys = null;
    String[] values = null;
    String[] reqQtys = null;


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

                if(result.size()==0)
                {
                    Toast.makeText(YRetrieveActivity.this, "Don't have  Retrieve Item!!", Toast.LENGTH_SHORT).show();

                }
                else
                {
//                    listview.setAdapter(
//                            new SimpleAdapter
//                                    (YRetrieveActivity.this, result, R.layout.y_row,
//                                            new String[]{"itemCode","itemDes", "totalQty"},
//                                            new int[]{R.id.textView1,R.id.textView2, R.id.textView3})
//                    );

                    rCustomAdapter customAdapter = new rCustomAdapter(YRetrieveActivity.this,result);
                    listview.setAdapter(customAdapter);

                }
            }
        }.execute();


        //Update to webservice
        btnConfrim = (Button) findViewById(R.id.buttonConfirm);
        btnConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                resCount = listview.getCount();
                final List<YRetrieveModel> objList = new ArrayList<YRetrieveModel>();
                keys = new String[resCount];
                values = new String[resCount];
                reqQtys = new String[resCount];

                StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.LAX);

                try {


                    for (int i = 0; i < listview.getAdapter().getCount(); i++)
                    {
                    textViewItem = (TextView) listview.getChildAt(i).findViewById(R.id.textView1);
                    keys[i] = textViewItem.getText().toString();

                    textViewReqQty = (TextView) listview.getChildAt(i).findViewById(R.id.textView3);
                    reqQtys[i] = textViewReqQty.getText().toString();

                    editTextRetQty = (EditText) listview.getChildAt(i).findViewById(R.id.editText1);
                    values[i] = editTextRetQty.getText().toString();
                     }

                    if (values.length > 0)
                    {
                          for (int j = 0; j < keys.length; j++)
                          {
                                if (Integer.parseInt(values[j]) <= Integer.parseInt(reqQtys[j]))
                                {
                                    objList.add(new YRetrieveModel(keys[j],"", values[j], ""));
                                    Toast.makeText(YRetrieveActivity.this, "successfully retrieved!", Toast.LENGTH_SHORT).show();
                                } else if ((Integer.parseInt(values[j]) > Integer.parseInt(reqQtys[j]))) {
                                    Toast.makeText(YRetrieveActivity.this, "Cannot retrieve more than requested quantity!", Toast.LENGTH_SHORT).show();
                                }
                          }
                     } else {

                         Toast.makeText(YRetrieveActivity.this, "Please Fill Quantity!!", Toast.LENGTH_SHORT).show();
                     }

                new AsyncTask<List<YRetrieveModel>, Void, Void>() {
                    @Override
                    protected Void doInBackground(List<YRetrieveModel>... params) {
                        Log.v("GGG", "=====" + objList);
                        YRetrieveModel.updateRetrieveQty(params[0], loginUserName);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {

                        finish();
                    }
                }.execute(objList);


                } catch (NumberFormatException e) {
                    Toast.makeText(YRetrieveActivity.this, "Fill Quantity!", Toast.LENGTH_SHORT).show();

                } catch (NullPointerException e) {
                    Toast.makeText(YRetrieveActivity.this, "Fill Something!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
