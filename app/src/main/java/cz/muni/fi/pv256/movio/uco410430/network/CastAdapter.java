package cz.muni.fi.pv256.movio.uco410430.network;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.R;
import cz.muni.fi.pv256.movio.uco410430.domain.Cast;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.utils.CircleTransformer;

/**
 * Adapter class for Cast list.
 *
 * Created by dhanak on 1.2.2016.
 */
public class CastAdapter extends BaseAdapter {

    private static final String TAG = ".CastAdapter";

    private Context mContext;
    private List<Cast> mCast;

    public CastAdapter(final Context context, final List<Cast> cast) {
        mContext = context;
        if (cast != null) {
            mCast = cast;
        } else {
            mCast = new ArrayList<>();
        }
    }

    public void setMovies(List<Cast> cast) {
        Log.i(TAG, "setCast()");
        if (cast != null) {
            mCast = cast;
        }
    }

    @Override
    public int getCount() {
        return mCast.size();
    }

    @Override
    public Object getItem(int position) {
        return mCast.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, "getView() for position " + position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.movie_cast_item, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.poster = (ImageView) convertView.findViewById(R.id.cast_item_image);
            viewHolder.name = (TextView) convertView.findViewById(R.id.cast_item_name);
            convertView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.name.setText(mCast.get(position).getName());
        Glide.with(mContext)
                .load("http://image.tmdb.org/t/p/w185" + mCast.get(position).getImage())
                .transform(new CircleTransformer(mContext))
                .into(holder.poster);


        return convertView;
    }

    private static class ViewHolder {
        ImageView poster;
        TextView name;
    }
}
