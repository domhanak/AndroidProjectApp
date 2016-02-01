package cz.muni.fi.pv256.movio.uco410430;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


import cz.muni.fi.pv256.movio.uco410430.database.MovieContract;
import cz.muni.fi.pv256.movio.uco410430.database.MovieManager;
import cz.muni.fi.pv256.movio.uco410430.domain.Cast;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.network.Responses;
import cz.muni.fi.pv256.movio.uco410430.service.MovieDownloadService;
import cz.muni.fi.pv256.movio.uco410430.synchronization.SyncAdapter;
import de.greenrobot.event.EventBus;

public class MainActivity  extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = ".MainActivity";

    private ArrayList<Movie> mMovies;
    private boolean isFavourite = false;

    private Bundle mBundle;
    private MovieManager mMovieManager;

    private MovieListFragment mListFragment;
    private MovieDetailFragment mDetailFragment;

    private List<Movie> mDiscoverData;
    private List<Cast> mCast;
    private ArrayList<Movie> mSavedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mMovies = savedInstanceState.getParcelableArrayList("movies");
            isFavourite = savedInstanceState.getBoolean("isFavourite");
        }


        mMovies = new ArrayList<>();
        mMovieManager = new MovieManager(this);
        init(mMovies);

        if (BuildConfig.LOGGING_ENABLED){
            Log.i("Logging", "PAID VERSION");
        }

        EventBus.getDefault().register(this);
        getLoaderManager().initLoader(1, null, this);
        SyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.getItem(R.id.menu_switch);
        if (isFavourite) {
            menuItem.setTitle("Discover");
        } else {
            menuItem.setTitle("Favourites");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Log.i(TAG, "onOptionsItemSelected");
        if (id == R.id.menu_switch) {
            if (item.getTitle().equals("Favorites")){
                Log.i("Selected", "Favourites");
                isFavourite = true;
                mMovies = mMovieManager.getAll();
                Log.d("Size of favourites is ", String.valueOf(mMovies.size()));
                setData();
                item.setTitle("Discover");
            }
            else {
                Log.i("Selected", "Discover");
                isFavourite = false;
                item.setTitle("Favourites");
                setData();
            }
            return true;
        }

        if (id == R.id.menu_refresh) {
            SyncAdapter.syncImmediately(this);
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
        bundle.putInt("position", App.getInstance().getSelectedMovie());

        mBundle = bundle;

        if(getResources().getBoolean(R.bool.isTablet)) {
            Log.i(TAG, "tablet");

            mListFragment = new MovieListFragment();
            mDetailFragment = new MovieDetailFragment();
            mListFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_list, mListFragment);
            fragmentTransaction.addToBackStack(null);

            mDetailFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fragment_detail, mDetailFragment, "detail");
            fragmentTransaction.commit();
        } else {
            Log.i(TAG, "phone");

            mListFragment = new MovieListFragment();
            mListFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.fragment_list, mListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void downloadData() {
        Log.i(TAG, "downloadData()");
        Intent downloadIntent = new Intent(this, MovieDownloadService.class);
        downloadIntent.putExtra("movie_id", -1);
        startService(downloadIntent);
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
        downloadData();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop()");
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFavourite", isFavourite);
        outState.putParcelable("detailed_movie", mListFragment.getDetailedMovie());
    }

    public void onEvent(final Responses.LoadMovieResponse response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "onEvent() - LoadMovieResponse");
                if (response.getMovies() == null) {
                    Log.i("Response null", "Showing notification");
                    //    showNotification();
                }
                Log.i("Response not null", "Initializing");
                mDiscoverData = response.getMovies();
                init((ArrayList<Movie>) mDiscoverData);
            }
        });
    }

    public void onEvent(final Responses.LoadCastResponse response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "onEvent() - LoadCastResponse");
                if (response.getCast() == null) {
                    Log.i("Response null", "Showing notification");
                    //    showNotification();
                    if (response.mCast == null) {
                        Log.d("lol", "lol");
                    }
                }
                Log.i("Response not null", "Setting cast");
                if (mListFragment.getDetailFragment() != null) {
                    mListFragment.getDetailFragment().setCast(response.getCast(), getApplicationContext());
                }
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader()");
        return new CursorLoader(this, MovieContract.MovieEntry.CONTENT_URI,
                MovieManager.MOVIE_COLS,
                null,
                null,
                MovieContract.MovieEntry.MOVIE_RELEASE_DATE + " ASC ");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(TAG, "onLoadFinished()");
        ArrayList<Movie> arrayList = new ArrayList<>();
        if (data != null) {
            while (data.moveToNext()) {
                arrayList.add(MovieManager.getMovieFromCursor(data));
            }
            data.close();
            mSavedData = arrayList;
            Log.i("mSavedData size is  ", String.valueOf(mSavedData.size()));

            setData();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // nop
    }

    public void addToFavorites(View view) {
        Log.i(TAG, "addToFavourites()");

        Movie movie = mListFragment.getDetailedMovie();
        boolean delete = false;
        ArrayList<Movie> tmp = mMovieManager.getAll();
        if (tmp != null) {
            for (Movie m : tmp) {
                if (m.getTitle().equals(movie.getTitle())) {
                    delete = true;
                }
            }
        }
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.addToFavButton);
        if (delete) {
            Log.i("MainActivity", "Deleting movie: " + movie.getTitle() + "With ID: " + movie.getId());
            mMovieManager.delete(movie);
            getLoaderManager().restartLoader(1, null, MainActivity.this);
            floatingActionButton.setImageResource(R.mipmap.ic_star_empty);
        } else {
            Log.i("MainActivity", "Adding movie: " + movie.getTitle() + "With ID: " + movie.getId());
            mMovieManager.add(movie);
            getLoaderManager().restartLoader(1, null, MainActivity.this);
            floatingActionButton.setImageResource(R.mipmap.ic_star_full);
        }
    }

    public void setData() {
        Log.i(TAG, "setData()");
        if (mListFragment != null) {
            mListFragment.setMovies((ArrayList<Movie>) getCurrentData());
        }
    }

    private List<Movie> getCurrentData() {
        return isFavourite ? mSavedData : mDiscoverData;
    }
}
