package cz.muni.fi.pv256.movio.uco410430;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.network.MovieAdapter;
import cz.muni.fi.pv256.movio.uco410430.network.Responses;
import cz.muni.fi.pv256.movio.uco410430.service.MovieDownloadService;
import de.greenrobot.event.EventBus;

public class MainActivity  extends AppCompatActivity  {

    private ArrayList<Movie> mMovies;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovies = new ArrayList<>();

        if (BuildConfig.logging){
            Log.i("Logging", "PAID VERSION");
        }

        EventBus.getDefault().register(this);
        if (savedInstanceState == null){
            Log.i("MainActivity - onCreate", "Downloading data");
            downloadData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes the application logic.
     *
     * @param movies
     */
    private void init(ArrayList<Movie> movies) {
        FragmentTransaction fragmentTransaction = null;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("movies", movies);
        bundle.putInt("position", -1);

        if(getResources().getBoolean(R.bool.isTablet)) {
            Log.d("MainActivity", "tablet");

            MovieListFragment movieListFragment = new MovieListFragment();
            movieListFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_list, movieListFragment);
            fragmentTransaction.addToBackStack(null);

            MovieDetailFragment movieDetailFragment =  new MovieDetailFragment();
            movieDetailFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fragment_detail, movieDetailFragment, "detail");
            fragmentTransaction.commit();
        } else {
            Log.d("MainActivity", "phone");

            MovieListFragment movieListFragment = new MovieListFragment();
            movieListFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_list, movieListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void downloadData(){
        Intent downloadIntent = new Intent(this, MovieDownloadService.class);
        startService(downloadIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final Responses.LoadMovieResponse response){
        init((ArrayList<Movie>) response.mMovies);
    }

}
