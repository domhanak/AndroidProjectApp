package cz.muni.fi.pv256.movio.uco410430.network;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import cz.muni.fi.pv256.movio.uco410430.MovieListFragment;
import cz.muni.fi.pv256.movio.uco410430.R;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.utils.Constants;

import static cz.muni.fi.pv256.movio.uco410430.utils.Constants.BASE_URL;

/**
 * Adapter class for Movie view.
 *
 * Created by dhanak on 10/18/15.
 */
public class MovieAdapter extends BaseAdapter {

    private Context mContext;
    private List<Movie> mMovies;

    public MovieAdapter(final Context context, final List<Movie> movies) {
        mContext = context;
        if (movies != null) {
            mMovies = movies;
        } else {
            mMovies = new ArrayList<>();
        }
    }

    public void setMovies(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mMovies.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Log.i("", "Inflation of row " + position);

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.movieCover);
            convertView.setTag(viewHolder);
        } else {
            Log.i("", "Recyclation of row " + position);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        Picasso.with(mContext)
                .load("https://image.tmdb.org/t/p/w396" + mMovies.get(position).getCoverPath()).into(holder.poster);

        return convertView;
    }

    private static class ViewHolder {
        ImageView poster;
    }

}
