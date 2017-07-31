package com.example.mayoolwin.ssisteam2_android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;

public class YDisbDetailActivity extends AppCompatActivity {

    String item =null;
    TextView textView1;
    ListView listview1;
    TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ydisb_detail);

        textView1 =(TextView) findViewById(R.id.textView1);
        listview1 =(ListView) findViewById(R.id.listView1);
        //textView2 =(TextView) findViewById(R.id.textView2);

         item = getIntent().getExtras().getString("deptName");

        new AsyncTask<String, Void, List<YDisburseDetailModel>>() {
            @Override
            protected List<YDisburseDetailModel> doInBackground(String... params) {
                return YDisburseDetailModel.listDisDeptDetail(params[0]);
            }
            @Override
            protected void onPostExecute(List<YDisburseDetailModel> result) {
                textView1.setText(item+" Department");

            listview1.setAdapter(
//                    new SimpleAdapter(YDisbDetailActivity.this, result, android.R.layout.simple_list_item_2,
//                            new String[] {"itemDes","totalQty"},
//                            new int[]{android.R.id.text1,android.R.id.text2})
                    new SimpleAdapter(YDisbDetailActivity.this, result, R.layout.y_row,
                            new String[] {"itemName","reqQty"},
                            new int[]{R.id.textView2,R.id.textView3})
            );

            }
        }.execute(item);
    }
}
