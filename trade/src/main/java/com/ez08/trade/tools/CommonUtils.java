package com.ez08.trade.tools;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

    public static String deleteAllCRLF(String input) {
        return input.replaceAll("((\r\n)|\n)[\\s\t ]*", " ").replaceAll(
                "^((\r\n)|\n)", "");
    }

    public static String getCurrentDate(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }
}
