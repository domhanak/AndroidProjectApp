package cz.muni.fi.pv256.movio.uco410430;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.network.MovieAdapter;

/**
 * Movie fragment that shows list of available movies.
 *
 * Created by dhanak on 10/25/15.
 */
public class MovieListFragment  extends Fragment implements AdapterView.OnItemClickListener{
    public static final String TAG = MovieListFragment.class.getSimpleName();

    private MovieDetailFragment mMovieDetailFragment = null;
    private List<Movie> mMovieList = new ArrayList<>();

    public static MovieListFragment newInstance(ArrayList<Movie> movies) {
        MovieListFragment fragment = new MovieListFragment();
                Bundle args = new Bundle();
                args.putParcelableArrayList("movieList", movies);
                fragment.setArguments(args);
                return fragment;
    }

    public MovieListFragment() {
        // required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_layout, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.movie_list_grid);

        if (ableToConnect()) {
            gridView.setEmptyView(view.findViewById(R.id.empty_list_view));
        } else {
            gridView.setEmptyView(view.findViewById(R.id.empty_list_view));
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("film list", "ItemClick");

                if (mMovieDetailFragment != null) {
                    Log.d("Movie list", "Setting movie detail.");
                    mMovieDetailFragment.setMovie(mMovieList.get(position));
                    mMovieDetailFragment.updateLayout();
                } else {
                    Log.d("Movie list", "New detail fragment.");
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    MovieDetailFragment fragmentFilmDetail = new MovieDetailFragment();
                    fragmentFilmDetail.setMovie(mMovieList.get(position));

                    fragmentTransaction
                            .replace(R.id.fragment_detail, fragmentFilmDetail, MovieDetailFragment.TAG)
                            .addToBackStack(MovieDetailFragment.TAG)
                            .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }

            }
        });

        gridView.setAdapter(new MovieAdapter(getActivity(), mMovieList));
        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMovieList = savedInstanceState.getParcelableArrayList("movieList");
        }
        if (getArguments() != null) {
            mMovieList = getArguments().getParcelableArrayList("movieList");
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movieList", (ArrayList<? extends Parcelable>) mMovieList);
    }

    private boolean ableToConnect() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public MovieDetailFragment getMovieDetailFragment() {
        return mMovieDetailFragment;
    }

    public void setMovieDetailFragment(MovieDetailFragment movieDetailFragment) {
        mMovieDetailFragment = movieDetailFragment;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
