package cz.muni.fi.pv256.movio.uco410430.network;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.R;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

/**
 * Adapter class for Movie view.
 *
 * Created by dhanak on 10/18/15.
 */
public class MovieAdapter extends BaseAdapter {

    private Context mContext;
    private List<Movie> mMovies;

    public MovieAdapter(Context context, List<Movie> movies) {
        mContext = context;
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
        return mMovies.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            Log.i("", "Inflation of row " + position);

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.mView = (TextView) convertView.findViewById(R.id.textView);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.movieImageView);
            convertView.setTag(viewHolder);
        } else {
            Log.i("", "Recyclation of row " + position);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mView.setText(mMovies.get(position).getTitle());
        holder.mImageView.setImageResource(R.mipmap.ic_launcher);

        return convertView;
    }

    private static class ViewHolder {
        TextView mView;
        ImageView mImageView;

    }

}
