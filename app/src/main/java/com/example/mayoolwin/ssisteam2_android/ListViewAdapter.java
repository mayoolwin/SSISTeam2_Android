package com.example.mayoolwin.ssisteam2_android;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Htein Lin Aung on 8/4/2017.
 */

public class ListViewAdapter extends BaseAdapter {

    public List<YRetrieveModel> list;
    Activity activity;

    public ListViewAdapter(Activity activity,List<YRetrieveModel> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //Only of the convertView is null, Inflate it.
        if(convertView == null){
            LayoutInflater inflater=activity.getLayoutInflater();
            convertView=inflater.inflate(R.layout.y_row, parent, false);

            holder = new ViewHolder();
            holder.txtFirst=(TextView) convertView.findViewById(R.id.textView1);
            holder.txtSecond=(TextView) convertView.findViewById(R.id.textView2);
            holder.txtThird=(TextView) convertView.findViewById(R.id.textView3);
            holder.txtFourth=(TextView) convertView.findViewById(R.id.textView4);

            //Add the holder as a tag to the convertView
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        YRetrieveModel map=list.get(position);
        holder.txtFirst.setText(map.get("itemCode"));
        holder.txtSecond.setText(map.get("itemDes"));
        holder.txtThird.setText(map.get("totalQty"));
        //holder.txtFourth.setText(map.get("totalQty"));


        return convertView;
    }

    private static class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
        TextView txtThird;
        TextView txtFourth;

    }
}