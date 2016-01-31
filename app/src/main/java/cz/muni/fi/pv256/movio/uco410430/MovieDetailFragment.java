package cz.muni.fi.pv256.movio.uco410430;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.support.design.widget.FloatingActionButton;
import android.widget.Toast;

import cz.muni.fi.pv256.movio.uco410430.database.MovieManager;
import cz.muni.fi.pv256.movio.uco410430.domain.Cast;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.network.CastAdapter;
import cz.muni.fi.pv256.movio.uco410430.network.Responses;
import cz.muni.fi.pv256.movio.uco410430.service.MovieDownloadService;
import cz.muni.fi.pv256.movio.uco410430.utils.CircleTransformer;
import de.greenrobot.event.EventBus;

/**
 * Fragment showing detail for one Movie.
 *
 * Created by dhanak on 11/1/15.
 */
public class MovieDetailFragment  extends Fragment {
    public static final String TAG = ".MovieDetailFragment";

    private Movie mMovie = null;
    private ArrayList<Movie> mMovies;
    private int mPosition = -1;
    private MovieManager mMovieManager;
    private Context mContext;

    private ImageView mBackground;
    private ImageView mCover;
    private TextView mName;
    private TextView mOverview;
    private TextView mRelease;
    private TextView mDirector;
    private FloatingActionButton mFbFav;
    private LinearLayout mCastHolder;
    private View mView;

    public Movie getMovie() {
        return mMovie;
    }

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        mView = inflater.inflate(R.layout.fragment_detail_layout, container, false);
        mMovies = getArguments().getParcelableArrayList("movies");
        mMovieManager = new MovieManager(getContext());
        mPosition = getArguments().getInt("position");
        mContext = getActivity();
        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
        if (mPosition != -1) {
            mBackground = (ImageView) view.findViewById(R.id.background);
            mCover = (ImageView) view.findViewById(R.id.cover);
            mName = (TextView) view.findViewById(R.id.movieTitle);
            mOverview = (TextView) view.findViewById(R.id.overview);
            mRelease = (TextView) view.findViewById(R.id.releaseDate);
            mDirector = (TextView) view.findViewById(R.id.movieDirector);

            mFbFav = (FloatingActionButton) view.findViewById(R.id.addToFavButton);
            populateWithData();
        } else {
            ViewStub viewStub = (ViewStub) view.findViewById(R.id.unselected);
            viewStub.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        return super.getLayoutInflater(savedInstanceState);
    }

    private void populateWithData(){
        if (mPosition != -1){
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/original" + mMovies.get(mPosition).getBackground()).into(mBackground);
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w396" + mMovies.get(mPosition).getCoverPath()).into(mCover);
            mName.setText(mMovies.get(mPosition).getTitle());
            mOverview.setText(mMovies.get(mPosition).getOverview());
            mRelease.setText(mMovies.get(mPosition).getReleaseDate());

            if (mMovieManager.contains(mMovies.get(mPosition).getId())) {
                mFbFav.setImageResource(R.mipmap.ic_star_full);
            } else {
                mFbFav.setImageResource(R.mipmap.ic_star_empty);
            }
        }
    }

    public void setCast(List<Cast> cast, Context context) {
        Log.d(TAG, "setCast()");
        ListView view = (ListView) mView.findViewById(R.id.cast_list);
        view.setAdapter(new CastAdapter(context, cast));
    }
}


