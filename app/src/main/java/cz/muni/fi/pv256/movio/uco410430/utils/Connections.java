package cz.muni.fi.pv256.movio.uco410430.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Helper class for determining if there is Network Connection active on device.
 *
 * Created by dhanak on 12/13/15.
 */
public class Connections {

    private static final String TAG = ".Connections";
    /**
     * Determines if the device is able to connect to the internet.
     *
     * @return true when the connection is possible
     */
     public static boolean isOnline(final Context context) {
        //check network connections
        ConnectivityManager connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = connMan.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
     }
}
