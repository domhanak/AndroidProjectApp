package cz.muni.fi.pv256.movio.uco410430.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by dhanak on 12/13/15.
 */
public class Connections {

    /**
     * Determines if the device is able to connect to the internet.
     *
     * @return true when the connection is possible
     */
     public static boolean isOnline(final Context context) {
        //check network connections
        ConnectivityManager connMan = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = connMan.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
     }
}
