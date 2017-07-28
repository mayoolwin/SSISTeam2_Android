package com.example.mayoolwin.ssisteam2_android;

import android.os.AsyncTask;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.R.id.listView;
import static com.example.mayoolwin.ssisteam2_android.R.id.textView2;

public class MakeNewRequestActivity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner itemspinner;

    int count=0;

    private Button btn;

    private TextView itemdesc;

    private TextView quantity;

    ArrayList<Item> itemlist = new ArrayList<Item>();

    TableLayout tl;
    TableRow tr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_request);
        spinner = (Spinner) findViewById(R.id.spinner2);
        itemspinner = (Spinner) findViewById(R.id.spinner1);
        btn = (Button) findViewById(R.id.add);
        tl = (TableLayout) findViewById(R.id.maintable);
        addHeaders();

        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... params) {
                return Category.listCategory();
            }

            @Override
            protected void onPostExecute(List<String> result) {


                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, result);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
            }
        }.execute();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                String catname = (String) adapterView.getAdapter().getItem(i);

                new AsyncTask<String, Void, List<Category>>() {
                    @Override
                    protected List<Category> doInBackground(String... params) {
                        return Category.listItem(params[0]);
                    }

                    @Override
                    protected void onPostExecute(List<Category> result) {

                        List<String> itemList = new ArrayList<String>();

                        for (Category cat : result) {


                            String uom = cat.get("Unit");
                            String item = cat.get("ItemDesc");

                            itemList.add(item);
                        }

                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_list_item_1, itemList);

                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        itemspinner.setAdapter(adapter);


                    }
                }.execute(catname);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Add Button event

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner cat_name = (Spinner) findViewById(R.id.spinner2);
                Spinner item_desc = (Spinner) findViewById(R.id.spinner1);
                EditText quantity = (EditText) findViewById(R.id.edit_qty);


                Item item = new Item(item_desc.getSelectedItem().toString(), quantity.getText().toString());

                itemlist.add(item);


                addData(item);


            }
        });

    }


    public void addHeaders() {

        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

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

        quantity.setPadding(200, 20, 20, 0);


        tr.addView(quantity); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

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
        divider2.setPadding(200, 0, 0, 0);
        // divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider2); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
    }


    public void addData(Item r) {



            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            itemdesc = new TextView(this);
            itemdesc.setText(r.get("ItemDesc"));

            itemdesc.setTextSize(17);
            itemdesc.setPadding(5, 5, 5, 5);
            tr.addView(itemdesc);  // Adding textView to tablerow.

            /** Creating another textview **/
            quantity = new TextView(this);
            quantity.setText(r.get("Quantity"));
            quantity.setTextSize(17);
            quantity.setPadding(200, 20, 20, 20);


            tr.addView(quantity); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));




    }

}
