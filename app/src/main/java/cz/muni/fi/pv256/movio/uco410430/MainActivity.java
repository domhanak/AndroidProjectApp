package cz.muni.fi.pv256.movio.uco410430;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.network.MovieAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Movie> dummyMovieList = generateFakeMovieList(1000);

        GridView gridview = (GridView) findViewById(R.id.list);
        MovieAdapter arrayAdapter = new MovieAdapter(this, dummyMovieList);
        gridview.setEmptyView(findViewById( R.id.empty_list_view));
        gridview.setAdapter(arrayAdapter);

        TextView headerText = (TextView) findViewById(R.id.gridHeader);
        headerText.setText("List of available movies");

        //TODO: onClickListener with activity switch to movie detail - need info from next seminar :)))
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Movie> generateFakeMovieList(int count) {
        if (count < -1) return Collections.EMPTY_LIST;

        List<Movie> movieList = new ArrayList<Movie>(count);
        for (int i = 0; i < count; ++i) {
            movieList.add(i, new Movie());
            movieList.get(i).setTitle("Movie " + i);
            movieList.get(i).setCoverPath("test/path" + i);
            movieList.get(i).setReleaseDate(Calendar.DATE);
        }

        return movieList;
    }
}
