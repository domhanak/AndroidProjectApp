package cz.muni.fi.pv256.movio.uco410430;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.database.MovieContract;
import cz.muni.fi.pv256.movio.uco410430.database.MovieManager;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

/**
 * Created by dhanak on 12/13/15.
 */
public class MovieTest extends AndroidTestCase {

    private static final String TAG = MovieManager.class.getSimpleName();

    private MovieManager mManager;

    @Override
    protected void setUp() throws Exception {
        mManager = new MovieManager(mContext);
    }

    @Override
    public void tearDown() throws Exception {
        mContext.getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                null
        );
        super.tearDown();
    }

    public void testAllFilms() throws Exception {
        List<Movie> expectedFilm = new ArrayList<>(2);
        Movie movie1 = createMovie("f1", 1, "dddd", "ffff", "sdfsdfsdf", 2.3f);
        Movie movie2 = createMovie("f2", 2, "fgdfg", "fdgy", "erdfbfd", 4.1f);
        expectedFilm.add(movie1);
        expectedFilm.add(movie2);

        mManager.add(movie1);
        mManager.add(movie2);

        List<Movie> films = mManager.getAll();
        Log.d(TAG, films.toString());
        assertTrue(films.size() == 2);
        assertEquals(films, expectedFilm);

    }

    public void testFind() {
        Movie movie = createMovie("f1", 1, "dddd", "ffff", "sdfsdfsdf", 2.3f);
        mManager.add(movie);
        Movie movieFromDb = mManager.get(1);
        assertEquals(movie, movieFromDb);
    }


    public void testDelete() {
        Movie f1 = createMovie("f1", 1, "dddd", "ffff", "sdfsdfsdf", 2.3f);
        mManager.add(f1);
        mManager.delete(f1);
        Movie f3 = mManager.get(1);
        assertNotSame(f1, f3);

    }

    public void deleteAll() {
        List<Movie> movies =   mManager.getAll();
        for (Movie movie : movies){
            mManager.delete(movie);
        }

    }

    public void testUpdate() {
        Movie movie = createMovie("f1", 1, "dddd", "ffff", "sdfsdfsdf", 2.3f);
        mManager.add(movie);
        movie.setTitle("TestTitle");
        mManager.add(movie);
        Movie movieFromDb = mManager.get(1l);
        assertEquals(movieFromDb.getTitle(),"TestTitle");

    }

    private Movie createMovie(String name, int id, String poster, String bcg, String date, float rating){
        Movie f = new Movie();
      //  f.setRating(rating);
        f.setTitle(name);
        f.setCoverPath(poster);
        f.setBackground(bcg);
        f.setId(id);
        f.setReleaseDate(date);
        return f;
    }
}
