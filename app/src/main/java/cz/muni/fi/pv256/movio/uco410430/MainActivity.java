package cz.muni.fi.pv256.movio.uco410430;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.network.MovieAdapter;

public class MainActivity extends AppCompatActivity {
    private List<Movie> mMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        ArrayList<Movie> dummyList= (ArrayList<Movie>) generateFakeMovieList(30);
        mMovies = dummyList;


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        MovieListFragment fragmentFilmList = MovieListFragment.newInstance(dummyList);

        fragmentTransaction.add(R.id.fragment_list, fragmentFilmList, MovieListFragment.TAG);

        if(getResources().getBoolean(R.bool.isTablet)) {
            Log.d("MainActivity", "tablet");
            MovieDetailFragment fragmentFilmDetail = new MovieDetailFragment();
            fragmentFilmList.setMovieDetailFragment(fragmentFilmDetail);

            fragmentTransaction.add(R.id.fragment_detail, fragmentFilmDetail, MovieDetailFragment.TAG);
            fragmentTransaction.commit();
        } else {
            GridView gridview = (GridView) findViewById(R.id.movie_list_grid);
            MovieAdapter arrayAdapter = new MovieAdapter(this, dummyList);

            // Distinquish if we will show no-connection message or empty-list message
            if (ableToConnect()) {
                gridview.setEmptyView(findViewById(R.id.no_connection_view));
            } else {
                gridview.setEmptyView(findViewById(R.id.empty_list_view));
            }
            gridview.setAdapter(arrayAdapter);

            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = (Intent) new Intent(getApplicationContext(), MovieDetailActivity.class);
                    intent.putExtra("title", mMovies.get(position).getTitle());
                    intent.putExtra("releaseDate", mMovies.get(position).getReleaseDate());
                    intent.putExtra("coverPath", mMovies.get(position).getCoverPath());
                    startActivity(intent);
                }
            });
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
     * Fake movie data generator.
     */
    private List<Movie> generateFakeMovieList(int count) {
        if (count < -1) return Collections.EMPTY_LIST;

        List<Movie> movieList = new ArrayList<Movie>(count);
        for (int i = 0; i < count; ++i) {
            movieList.add(i, new Movie());
            movieList.get(i).setTitle("Movie " + i);
            movieList.get(i).setCoverPath("test/path" + i);
            movieList.get(i).setReleaseDate(Calendar.DATE);
        }

        return movieList;
    }

    /**
     * Determines if the device is able to connecto to internet.
     *
     * @return true when the connection is possible
     */
    private boolean ableToConnect() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
