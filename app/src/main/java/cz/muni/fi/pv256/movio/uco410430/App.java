package cz.muni.fi.pv256.movio.uco410430;

import android.app.Application;
import android.os.Build;
import android.os.StrictMode;

import cz.muni.fi.pv256.movio.uco410430.network.Api;

public class App extends Application {

    public Api getApi() {
        return api;
    }

    private Api api;
    private static App sInstance = null;
    public int selectedMovie = -1;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            initStrictMode();
        }
    }

    private void initStrictMode() {
        StrictMode.ThreadPolicy.Builder tpb = new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            tpb.penaltyFlashScreen();
        }
        StrictMode.setThreadPolicy(tpb.build());

        StrictMode.VmPolicy.Builder vmpb = new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            vmpb.detectLeakedClosableObjects();
        }
        StrictMode.setVmPolicy(vmpb.build());
    }
    public static App getInstance(){

        if (sInstance == null){
            sInstance = new App();
        }
        return sInstance;
    }

    public int getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(int filmSelected) {
        this.selectedMovie = filmSelected;
    }
}
