package com.example.mayoolwin.ssisteam2_android;

import android.os.AsyncTask;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mayoolwin.ssisteam2_android.MainActivity.dept_code;
import static com.example.mayoolwin.ssisteam2_android.MainActivity.name;

public class MakeNewRequestActivity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner itemspinner;

    int count=0;

    private Button btn;

    private TextView itemdesc;

    private TextView quantity;

    private List<Item> itemlist;

    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_new_request);
        spinner = (Spinner) findViewById(R.id.spinner2);
        itemspinner = (Spinner) findViewById(R.id.spinner1);
        btn = (Button) findViewById(R.id.add);
        listView=(ListView)findViewById(R.id.listView2);


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

        // Submit button
        Button b = (Button) findViewById(R.id.submit);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String currentDateandTime = sdf.format(new Date());
                EditText reason=(EditText) findViewById(R.id.reason);
                NewRequest req = new NewRequest(name,dept_code,reason.getText().toString(),"Pending",currentDateandTime);

                new AsyncTask<NewRequest, Void, Void>() {
                    @Override
                    protected Void doInBackground(NewRequest... params) {
                        NewRequest.InsertRequest(params[0]);
                        for (Item i:itemlist
                             ) {

                            Item.CreateRequestDetail(i);

                        }



                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {

                    }
                }.execute(req);




            }
        });



        // Spinner Event
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
                Button submit=(Button)findViewById(R.id.submit) ;
                boolean duplicate=false;
                Item item = new Item(item_desc.getSelectedItem().toString(), quantity.getText().toString());

                if(itemlist==null)
                {
                    itemlist= new ArrayList<Item>();
                    itemlist.add(item);
                    View(itemlist);
                    submit.setVisibility(View.VISIBLE);
                }

                else {


                    for (Item i: itemlist)
                    {
                        if (i.get("ItemDesc") == item.get("ItemDesc"))
                        {
                            duplicate = true;
                            break;
                        }

                    }

                    if (!duplicate)
                    {

                        itemlist.add(item);
                        View(itemlist);
                        submit.setVisibility(View.VISIBLE);

                    }
                    else {
                       DuplicateItem();
                        View(itemlist);
                        submit.setVisibility(View.VISIBLE);
                    }

                }

                //Testing Duplication








                }
            });

        }


        public void View(List<Item> itemList)
        {
             listView.setAdapter(new SimpleAdapter(getApplicationContext(),itemList,R.layout.request_item_row,new String[]{"ItemDesc","Quantity"},new int[]{R.id.itemName,R.id.quantity}));
            registerForContextMenu(listView);
        }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, v.getId(), 0, "Delete");//groupId, itemId, order, title

    }
    @Override
    public boolean onContextItemSelected(MenuItem item){

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        int pos = info.position;

        String name=itemlist.get(pos).get("ItemDesc");

        if(item.getTitle()=="Delete"){


            for (Item i:itemlist)
            {
                if(i.get("ItemDesc").equals(name))
                {

                    itemlist.remove(i);
                    View(itemlist);
                    break;
                }

            }
            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_LONG).show();

        }
        else{
            return false;
        }
        return true;
    }

    public void DuplicateItem()
    {

        new AlertDialog.Builder(this)
                .setTitle("Duplicate Item!")
                .setMessage("Duplicate Item!")
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }












}
