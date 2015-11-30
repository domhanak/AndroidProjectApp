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

import com.tonicartos.widget.stickygridheaders.StickyGridHeadersBaseAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import cz.muni.fi.pv256.movio.uco410430.R;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

/**
 * Adapter class for Movie view.
 *
 * Created by dhanak on 10/18/15.
 */
public class MovieAdapter extends BaseAdapter implements StickyGridHeadersBaseAdapter {

    private Context mContext;
    private List<Movie> mMovies;

    public MovieAdapter(Context context, List<Movie> movies) {
        mContext = context;
        if (movies != null) {
            mMovies = movies;
        } else {
            mMovies = new ArrayList<>();
        }
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
        return mMovies.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Log.i("", "Inflation of row " + position);

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.movieImageView);
            convertView.setTag(viewHolder);
        } else {
            Log.i("", "Recyclation of row " + position);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (mMovies.get(position).getCoverPath() == null) {
            holder.poster.setImageResource(R.mipmap.ic_launcher);
        } else  {
            holder.poster.setImageResource(R.mipmap.ic_launcher);
            // dl with Picasso
        }

        return convertView;
    }

    @Override
    public int getCountForHeader(int i) {
        return mMovies.size();
    }

    @Override
    public int getNumHeaders() {
        return 2;
    }

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_header, viewGroup, false);
        }

        TextView headerView = (TextView) view.findViewById(R.id.gridHeader);
        headerView.setText("Available movies");

        return view;
    }

    private static class ViewHolder {
        ImageView poster;
    }

}
