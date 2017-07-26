package com.example.mayoolwin.ssisteam2_android;

/**
 * Created by Htein Lin Aung on 7/25/2017.
 */

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTrace {
    public static String trace(Exception ex) {
        StringWriter outStream = new StringWriter();
        ex.printStackTrace(new PrintWriter(outStream));
        return outStream.toString();
    }
}