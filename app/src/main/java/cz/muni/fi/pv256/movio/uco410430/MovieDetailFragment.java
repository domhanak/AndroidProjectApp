package cz.muni.fi.pv256.movio.uco410430;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

/**
 * Fragment showing detail for one Movie.
 *
 * Created by dhanak on 11/1/15.
 */
public class MovieDetailFragment  extends Fragment{
    public static final String TAG = MovieDetailFragment.class.getSimpleName();


    private View mView = null;
    private Movie mMovie = null;

    private OnFragmentInteractionListener mListener;

    public void setMovie(Movie movie) {
        mMovie = movie;
    }

    public void updateLayout() {
        Log.d("MovieDetailFragment", "RefreshingLayout");

        // set film info
        if(mView != null && mMovie != null) {
            TextView textViewTitle = (TextView) mView.findViewById(R.id.title);
            textViewTitle.setText(mMovie.getTitle());

            TextView textViewReleaseDate = (TextView) mView.findViewById(R.id.releaseDate);
            textViewReleaseDate.setText(Long.toString(mMovie.getReleaseDate()));

            ImageView imageViewCover = (ImageView) mView.findViewById(R.id.cover);
            imageViewCover.setImageResource(R.mipmap.ic_launcher);

            ImageView imageViewBackground = (ImageView) mView.findViewById(R.id.background);
            imageViewBackground.setImageResource(R.color.blue_primary);
        }
        else {
            Log.d("MovieDetailFragment", "Couldn't refresh.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("MovieDetailFragment", "onCreateView()");
        mView = inflater.inflate(R.layout.fragment_detail_layout, container, false);
        if (savedInstanceState != null) {
            mMovie = savedInstanceState.getParcelable("movie");
        }
        updateLayout();
        return mView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MovieDetailFragment", "Putting parcable to outState.");
        outState.putParcelable("movie", mMovie);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnFragmentInteractionListener) context;
                    } catch (ClassCastException e) {
                        throw new ClassCastException(context.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}
