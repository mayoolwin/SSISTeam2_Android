package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by May Oo Lwin on 7/30/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context context;
    List<ViewAllAdjustmentModel> customVos;
    DateFormatConvert df = new DateFormatConvert();
    public CustomAdapter(Context context, List<ViewAllAdjustmentModel> customVos){
        this.context = context;
        this.customVos = customVos;
        Log.e("Betten CustomAdapter","CustomV"+customVos);
    }

    @Override
    public int getCount() {
        return customVos.size();
    }

    @Override
    public ViewAllAdjustmentModel getItem(int position) {
        return customVos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        TextView id1,id2,id3,id4,id5;
        Button b1;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.view_adjustment_row,null);
        }

        id1 = (TextView)convertView.findViewById(R.id.textView2) ;
        id2 = (TextView)convertView.findViewById(R.id.textView3) ;
        id3 = (TextView)convertView.findViewById(R.id.textView15) ;
        id4 = (TextView)convertView.findViewById(R.id.textView13) ;
        id5 = (TextView)convertView.findViewById(R.id.textView20) ;
        id1.setText(customVos.get(position).getVocherId());
        id2.setText(customVos.get(position).getClerk());
        id3.setText(df.changeDateFormat(customVos.get(position).getStatus()));
        id4.setText(customVos.get(position).getDate());
       // id4.setText(customVos.get(position).getDate());
        id5.setText(customVos.get(position).getHighestCost());
        Log.e("GetView Method","GetView"+customVos);
        b1 = (Button)convertView.findViewById(R.id.button3);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(context, "Click"+customVos.get(position).getVocherId(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,ViewAllAdjustmentDetail.class);
                i.putExtra("voucherId", customVos.get(position).getVocherId());
                context.startActivity(i);
            }
        });
        return convertView;
    }


}