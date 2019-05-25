package com.ez08.trade.tools;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonUtils {

    public static String deleteAllCRLF(String input) {
        return input.replaceAll("((\r\n)|\n)[\\s\t ]*", "").replaceAll(
                "^((\r\n)|\n)", "");
    }

    private static Toast toast = null;

    public static void show(Context context, String text) {
        if(context != null) {
            if (toast == null) {
                toast = Toast.makeText(context.getApplicationContext(),
                        text, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
            }

            toast.setText(text);
            toast.show();
        }
    }

    public static String getCurrentDate(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(date);
    }
}
