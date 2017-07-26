package com.example.mayoolwin.ssisteam2_android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ApproveRequestActivity extends AppCompatActivity{

    final static int []view={R.id.editText1,R.id.editText2,R.id.editText3};
    final static String [] key={"Name","Item Description","Quantity"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request);

        String item=getIntent().getExtras().getString("Id");

        new AsyncTask<String, Void, Request>() {
            @Override
            protected Request doInBackground(String... params) {
                return Request.getRequest(params[0]);
            }

            @Override
            protected void onPostExecute(Request result) {
                for (int i = 0; i < view.length; i++) {
                    EditText t = (EditText) findViewById(view[i]);
                    t.setText(result.get(key[i]));
                }
            }
        }.execute(item);

        /*Button b = (Button) findViewById(R.id.reject);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request r = new Request();
                for (int i=0; i<view.length; i++) {
                    EditText t = (EditText) findViewById(view[i]);
                    r.put(key[i], t.getText().toString());
                }
                new AsyncTask<Request, Void, Void>() {
                    @Override
                    protected Void doInBackground(Request... params) {
                        Request.rejectRequest(params[0]);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {
                        finish();
                    }
                }.execute(r);
            }
        });*/
    }
}
