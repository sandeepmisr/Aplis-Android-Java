package com.edu.webservice;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Utility {

    public static String getCurrent() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    public static String readResponce(InputStream stream, String stren) {
        StringBuffer builder = new StringBuffer();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, stren));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
