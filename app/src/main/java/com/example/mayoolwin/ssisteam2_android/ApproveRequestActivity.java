package com.example.mayoolwin.ssisteam2_android;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import java.util.List;

public class ApproveRequestActivity extends AppCompatActivity{


    EditText editdate;
    EditText editname;
    EditText editreason;

    TableLayout tl;
    TableRow tr;
    TextView itemdesc,quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_request);
        String name = getIntent().getExtras().getString("Name");
        String req_id = getIntent().getExtras().getString("Id");
        String date = getIntent().getExtras().getString("Date");
        String reason = getIntent().getExtras().getString("Reason");
        tl = (TableLayout) findViewById(R.id.maintable);
        addHeaders();

        editdate= (EditText) findViewById(R.id.edit_date);
        editname= (EditText) findViewById(R.id.name);
        editreason=(EditText) findViewById(R.id.reason);

        editname.setText(name);
        editreason.setText(reason);
        editdate.setText(date);

        new AsyncTask<String,Void,List<RequestDetail>>()
        {

            @Override
            protected List<RequestDetail> doInBackground(String... params) {
                List<RequestDetail> r=RequestDetail.listRequestDetail(params[0]);
                return r;
            }

            @Override
            protected void onPostExecute(List<RequestDetail> result) {

                addData(result);


            }
        }.execute(req_id);
    }

    public void addHeaders(){

        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        TextView itemdesc = new TextView(this);
        itemdesc.setText("Item Description");
        itemdesc.setTextSize(19);


        itemdesc.setPadding(5, 5, 5, 0);
        tr.addView(itemdesc);  // Adding textView to tablerow.

        /** Creating another textview **/
        TextView quantity = new TextView(this);
        quantity.setText("Quantity");
        quantity.setTextSize(19);

        quantity.setPadding(5, 5, 5, 0);

        tr.addView(quantity); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating another textview **/
        TextView divider = new TextView(this);
        divider.setText("---------------------------");
        divider.setTextSize(17);
       // divider.setTextColor(Color.GREEN);
        divider.setPadding(5, 0, 0, 0);
        //divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider); // Adding textView to tablerow.

        TextView divider2 = new TextView(this);
        divider2.setText("--------------");
        divider2.setTextSize(17);
       // divider2.setTextColor(Color.GREEN);
        divider2.setPadding(5, 0, 0, 0);
       // divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider2); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    public void addData(List<RequestDetail> result){

        for (RequestDetail r:result)
        {

            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            itemdesc = new TextView(this);
            itemdesc.setText(r.get("ItemName"));

            itemdesc.setTextSize(17);
            itemdesc.setPadding(5, 5, 5, 5);
            tr.addView(itemdesc);  // Adding textView to tablerow.

            /** Creating another textview **/
            quantity = new TextView(this);
            quantity.setText(r.get("Quantity"));
            quantity.setTextSize(17);
            quantity.setPadding(5, 5, 5, 5);

            tr.addView(quantity); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));

        }



    }
}
