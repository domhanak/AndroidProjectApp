package cz.muni.fi.pv256.movio.uco410430;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Movie detail activity used for phones.
 *
 * Created by dhanak on 11/1/15.
 */
public class MovieDetailActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_layout);
        Intent intent = getIntent();

        TextView textViewTitle = (TextView) findViewById(R.id.title);
        textViewTitle.setText(intent.getStringExtra("title"));

        TextView textViewReleaseDate = (TextView) findViewById(R.id.releaseDate);
        textViewReleaseDate.setText(String.format(String.valueOf(intent.getLongExtra("releaseDate", R.mipmap.ic_launcher))));

        ImageView imageViewCover = (ImageView) findViewById(R.id.cover);
        imageViewCover.setImageResource(R.mipmap.ic_launcher);
    }
}
