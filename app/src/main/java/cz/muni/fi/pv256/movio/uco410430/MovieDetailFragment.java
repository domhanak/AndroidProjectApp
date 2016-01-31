package cz.muni.fi.pv256.movio.uco410430;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import cz.muni.fi.pv256.movio.uco410430.database.MovieManager;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

/**
 * Fragment showing detail for one Movie.
 *
 * Created by dhanak on 11/1/15.
 */
public class MovieDetailFragment  extends Fragment {
    public static final String TAG = ".MovieDetailFragment";

    private View mView = null;
    private Movie mMovie = null;
    private ArrayList<Movie> mMovies;
    private int position = -1;
    private MovieManager mMovieManager;

    private ImageView background;
    private ImageView cover;
    private TextView name;
    private TextView overview;
    private TextView release;
    private TextView director;
    private FloatingActionButton fbFav;

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MovieDetailFragment", "onCreateView()");
        mView = inflater.inflate(R.layout.fragment_detail_layout, container, false);
        mMovies = getArguments().getParcelableArrayList("movies");
        mMovieManager = new MovieManager(getContext());
        position = getArguments().getInt("position");
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (position != -1) {
            background = (ImageView) view.findViewById(R.id.background);
            cover = (ImageView) view.findViewById(R.id.cover);
            name = (TextView) view.findViewById(R.id.movieTitle);
            overview = (TextView) view.findViewById(R.id.overview);
            release = (TextView) view.findViewById(R.id.releaseDate);
            director = (TextView) view.findViewById(R.id.movieDirector);
            fbFav = (FloatingActionButton) view.findViewById(R.id.addToFavButton);

            ImageView cast = (ImageView) view.findViewById(R.id.movieCast);

            populateWithData();
        } else {
            ViewStub viewStub = (ViewStub) view.findViewById(R.id.unselected);
            viewStub.setVisibility(View.VISIBLE);
        }
    }

    private void populateWithData(){
        Context mContext = getActivity();
        if (position != -1){
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/original" + mMovies.get(position).getBackground()).into(background);
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w396" + mMovies.get(position).getCoverPath()).into(cover);
            name.setText(mMovies.get(position).getTitle());
            overview.setText(mMovies.get(position).getOverview());
            release.setText(mMovies.get(position).getReleaseDate());

            if (mMovieManager.contains(mMovies.get(position).getId())) {
                fbFav.setImageResource(R.mipmap.ic_star_full);
            } else {
                fbFav.setImageResource(R.mipmap.ic_star_empty);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("Fragment", "MovieDetailFragment");
    }
}
