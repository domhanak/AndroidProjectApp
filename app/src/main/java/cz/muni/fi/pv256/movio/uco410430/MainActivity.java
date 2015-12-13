package cz.muni.fi.pv256.movio.uco410430;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
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

import cz.muni.fi.pv256.movio.uco410430.database.MovieContract;
import cz.muni.fi.pv256.movio.uco410430.database.MovieDatabaseHelper;
import cz.muni.fi.pv256.movio.uco410430.database.MovieManager;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.network.MovieAdapter;
import cz.muni.fi.pv256.movio.uco410430.network.Responses;
import cz.muni.fi.pv256.movio.uco410430.service.MovieDownloadService;
import cz.muni.fi.pv256.movio.uco410430.synchronization.SyncAdapter;
import de.greenrobot.event.EventBus;

public class MainActivity  extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ArrayList<Movie> mMovies;
    private Bundle mBundle;
    private MovieManager mMovieManager;
    private boolean savedData;
    private MovieListFragment mListFragment;
    private ArrayList<Movie> mDataDiscover;
    private ArrayList<Movie> mDataSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainActivity - onCreate", "Setting content view.");
        setContentView(R.layout.activity_main);

        mMovies = new ArrayList<>();
        mMovieManager = new MovieManager(this);

        if (BuildConfig.logging){
            Log.i("Logging", "PAID VERSION");
        }

        if (savedInstanceState == null) {
            downloadData();
        }

        mBundle = savedInstanceState;
        SyncAdapter.initializeSyncAdapter(this);
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
        if (id == R.id.menu_switch) {
            if (item.getTitle().equals("Favorites")){
                mMovies = (ArrayList<Movie>) mMovieManager.getAll();
                Log.d("Movies from db size: ", String.valueOf(mMovies.size()));
                init(mMovies);
                item.setTitle("Discover");
            }
            else {
                item.setTitle("Favorites");
                downloadData();
            }

            return true;
        }

        if (id == R.id.menu_refresh) {
            downloadData();
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

        for (Movie m : movies) {
            Log.i("MOVIE ID:", String.valueOf(m.getId()));
        }

        if(getResources().getBoolean(R.bool.isTablet)) {
            Log.d("MainActivity", "tablet");

            mListFragment = new MovieListFragment();
            mListFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_list, mListFragment);
            fragmentTransaction.addToBackStack(null);

            MovieDetailFragment movieDetailFragment =  new MovieDetailFragment();
            movieDetailFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fragment_detail, movieDetailFragment, "detail");
            fragmentTransaction.commit();
        } else {
            Log.d("MainActivity", "phone");

            mListFragment = new MovieListFragment();
            mListFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_list, mListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void downloadData(){
        Intent downloadIntent = new Intent(this, MovieDownloadService.class);
        startService(downloadIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        if (mBundle == null){
            downloadData();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void onEvent(final Responses.LoadMovieResponse response) {
        init((ArrayList<Movie>) response.mMovies);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MovieContract.MovieEntry.CONTENT_URI,
                MovieManager.MOVIE_COLS,
                null,
                null,
                MovieContract.MovieEntry.MOVIE_RELEASE_DATE + " ASC ");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i("MainActivity", "onLoadFinished()");
        if (data != null) {
            while (data.moveToNext()) {
                Movie movie = MovieManager.getMovieFromCursor(data);
            }
            data.close();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // nop
    }

    public void showNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(getString(R.string.no_connection_string));
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(0, mBuilder.build());
    }

    public void addToFavorites(View view) {
        Log.i("MainActivity", "Adding to favourites");

        Movie movie = mListFragment.getDetailedMovie();
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addToFavButton);
        if (mMovieManager.contains(mListFragment.getDetailedMovie().getId())) {
            mMovieManager.delete(movie);
            floatingActionButton.setImageResource(R.mipmap.ic_star_empty);
        } else {
            mMovieManager.add(movie);
            floatingActionButton.setImageResource(R.mipmap.ic_star_full);
        }
    }
}
