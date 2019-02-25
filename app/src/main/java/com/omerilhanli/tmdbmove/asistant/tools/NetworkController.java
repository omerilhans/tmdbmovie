package com.omerilhanli.tmdbmove.asistant.tools;

import android.content.Context;
import android.net.ConnectivityManager;

public class NetworkController {

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}
