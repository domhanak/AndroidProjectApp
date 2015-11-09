package cz.muni.fi.pv256.movio.uco410430;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

/**
 * Created by dhanak on 11/1/15.
 */
public class MovieDetailFragment  extends Fragment{
    public static final String TAG = MovieDetailFragment.class.getSimpleName();


    private View mView = null;
    private Movie mMovie = null;

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    public void updateLayout() {
        Log.d("film detail", "refreshLayout");

        // set film info
        if(mView != null && mMovie != null) {
            TextView textViewTitle = (TextView) mView.findViewById(R.id.title);
            textViewTitle.setText(mMovie.getTitle());

            TextView textViewReleaseDate = (TextView) mView.findViewById(R.id.releaseDate);
            textViewReleaseDate.setText(Long.toString(mMovie.getReleaseDate()));

            ImageView imageViewCover = (ImageView) mView.findViewById(R.id.cover);
            //   imageViewCover.setImageResource();

            ImageView imageViewBackground = (ImageView) mView.findViewById(R.id.background);
            //   imageViewBackground.setImageResource();
        }
        else {
            Log.d("film detail", "could not refresh");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_detail_layout, container, false);
        updateLayout();
        return mView;
    }

}
