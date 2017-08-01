package com.example.mayoolwin.ssisteam2_android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by May Oo Lwin on 8/1/2017.
 */

public class DateFormatConvert {
    public String changeDateFormat(String date){
        String fmtDate=null;
        SimpleDateFormat input = new SimpleDateFormat("M/d/yyyy HH:mm:ss");
        //SimpleDateFormat output = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat output = new SimpleDateFormat("MMM-d-yyyy");
        try {
            Date chDate = input.parse(date);
            fmtDate = output.format(chDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return fmtDate;
    }
}
