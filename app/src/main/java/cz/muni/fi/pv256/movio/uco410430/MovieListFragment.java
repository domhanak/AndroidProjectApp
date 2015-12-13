package cz.muni.fi.pv256.movio.uco410430;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.network.MovieAdapter;
import cz.muni.fi.pv256.movio.uco410430.utils.Connections;

/**
 * Movie fragment that shows list of available movies.
 *
 * Created by dhanak on 10/25/15.
 */
public class MovieListFragment extends Fragment {
    public static final String TAG = MovieListFragment.class.getSimpleName();

    private View mView;
    private ArrayList<Movie> mMovies;
    private GridView mGridView;
    private MovieAdapter mMovieAdapter;

    public MovieListFragment() {
        // required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_list_layout,container,false);
        mMovies = new ArrayList<Movie>();
        mMovies = getArguments().getParcelableArrayList("movies");

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mGridView = (GridView) view.findViewById(R.id.movieListGrid);
        mMovieAdapter = new MovieAdapter(getActivity(), mMovies);
        mGridView.setAdapter(mMovieAdapter);

        initialize();
        insertData();
    }

    private void initialize() {
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                Toast.makeText(getActivity(), mMovies.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
                MovieDetailFragment detailFilmFrag = new MovieDetailFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putParcelableArrayList("movies", mMovies);
                detailFilmFrag.setArguments(bundle);
                if (getActivity().getResources().getBoolean(R.bool.isTablet)) {
                    fragmentTransaction.replace(R.id.fragment_detail, detailFilmFrag);
                } else {
                    fragmentTransaction.replace(R.id.fragment_list, detailFilmFrag);
                }

                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        ViewStub empty = (ViewStub) mView.findViewById(R.id.empty);
        mGridView.setEmptyView(empty);
    }

    private void insertData(){
        if (!Connections.isOnline(getActivity())){
            ViewStub empty = (ViewStub) mView.findViewById(R.id.empty);
            empty.setLayoutResource(R.layout.no_connection_view);
            empty.inflate();
        }

    }

    public void updateAdapter(List<Movie> movies) {
        mMovieAdapter.setMovies(movies);
        mMovieAdapter.notifyDataSetChanged();
    }



}
