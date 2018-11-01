package com.mysafetynet.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {
    public static final String FORMAT_YYYYMMDD = "yyyy/MM/dd";
    public static final String FORMAT_DDMMYYYY = "dd/MM/yyyy";
    public static final String FORMAT_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss a";

    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    public static String convertFormat(String dateStr, String currentFormat, String displayFormat) {


        SimpleDateFormat curFormater = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat postFormater = new SimpleDateFormat(displayFormat, Locale.getDefault());
        Date dateObj = null;
        try {
            dateObj = curFormater.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return postFormater.format(dateObj);
    }

    public static String convertFormat(Date dateStr, String currentFormat, String displayFormat) {
        SimpleDateFormat curFormater = new SimpleDateFormat(currentFormat, Locale.getDefault());
        SimpleDateFormat postFormater = new SimpleDateFormat(displayFormat, Locale.getDefault());
        return postFormater.format(dateStr);
    }

    public static String displayFormat(Date dateStr, String displayFormat) {
        SimpleDateFormat postFormater = new SimpleDateFormat(displayFormat, Locale.getDefault());
        return postFormater.format(dateStr);
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivityManager != null) {
                //noinspection deprecation
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidCardMonth(String month) {
        int monthNum = Integer.parseInt(month);
        return monthNum >= 1 && monthNum <= 12;
    }

    public static boolean isValidCardYear(String month) {
        return month.length() >= 2;
    }

    public static boolean isValidCardCvv(String cvv) {

        return cvv.length() == 3;
    }

}
