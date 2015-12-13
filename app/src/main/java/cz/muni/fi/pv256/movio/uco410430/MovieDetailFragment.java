package cz.muni.fi.pv256.movio.uco410430;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import cz.muni.fi.pv256.movio.uco410430.database.MovieDatabaseHelper;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.utils.Constants;

/**
 * Fragment showing detail for one Movie.
 *
 * Created by dhanak on 11/1/15.
 */
public class MovieDetailFragment  extends Fragment {
    public static final String TAG = MovieDetailFragment.class.getSimpleName();
    private static boolean isFavorite = false;

    private View mView = null;
    private Movie mMovie = null;
    private ArrayList<Movie> mMovies;
    private int position;
    private MovieDatabaseHelper mDatabaseMovies;

    ImageView background;
    ImageView cover;
    TextView name;
    TextView overview;
    TextView release;
    TextView director;
    FloatingActionButton fbFav;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MovieDetailFragment", "onCreateView()");
        mView = inflater.inflate(R.layout.fragment_detail_layout, container, false);
        mMovies = new ArrayList<>();
        mMovies = getArguments().getParcelableArrayList("movies");
        position = getArguments().getInt("position");
        setRetainInstance(true);
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        background = (ImageView) view.findViewById(R.id.background);
        cover = (ImageView) view.findViewById(R.id.cover);
        name = (TextView) view.findViewById(R.id.movieTitle);
        overview = (TextView) view.findViewById(R.id.overview);
        release = (TextView) view.findViewById(R.id.releaseDate);
        director = (TextView) view.findViewById(R.id.movieDirector);
        fbFav = (FloatingActionButton) view.findViewById(R.id.addToFavButton);

        ImageView cast = (ImageView) view.findViewById(R.id.movieCast);
        populateWithData();
    }

    private void populateWithData(){
        Context mContext = getActivity();
        if (position != -1){
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/original" + mMovies.get(position).getBackground()).into(background);
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w396" + mMovies.get(position).getCoverPath()).into(cover);
            name.setText(mMovies.get(position).getTitle());
            overview.setText(mMovies.get(position).getOverview());
            release.setText(mMovies.get(position).getReleaseDate());

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("Fragment", "MovieDetailFragment");
    }
}
