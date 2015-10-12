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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Movie> dummyMovieList = new ArrayList<>(30);
        for (int i = 0; i < dummyMovieList.size(); ++i) {
            dummyMovieList.add(i, new Movie());
            dummyMovieList.get(i).setTitle("Movie " + i);
            dummyMovieList.get(i).setCoverPath("CoverPath  " + i);
            dummyMovieList.get(i).setReleaseDate(i);
        }

        ArrayList<String> stringArrayList = new ArrayList<>(30);
        for (int i = 0; i < dummyMovieList.size(); ++i) {
            stringArrayList.add(dummyMovieList.get(i).getTitle());
        }

        GridView gridview = (GridView) findViewById(R.id.gridView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.grid_item, stringArrayList);

        gridview.setAdapter(arrayAdapter);

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
}
