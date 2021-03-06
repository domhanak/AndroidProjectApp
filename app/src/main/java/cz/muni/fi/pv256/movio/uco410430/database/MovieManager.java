package cz.muni.fi.pv256.movio.uco410430.database;


import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import cz.muni.fi.pv256.movio.uco410430.database.MovieContract.MovieEntry;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

/**
 * Manager for movies in database.
 *
 * Created by dhanak on 12/13/15.
 */
public class MovieManager {

    private static final String TAG = ".MovieManager";

    private static final String WHERE_ID = MovieEntry._ID + " = ?";

    public static final String[] MOVIE_COLS = {
            MovieEntry._ID,
            MovieEntry.MOVIE_NAME,
            MovieEntry.MOVIE_OVERVIEW,
            MovieEntry.MOVIE_COVER,
            MovieEntry.MOVIE_BACKGROUND,
            MovieEntry.MOVIE_RELEASE_DATE
    };

    private Context mContext;

    public MovieManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public boolean contains(long id) {
        Log.i(TAG, "contains() for ID: " + id);
        Cursor cursor = mContext.getContentResolver()
                .query(MovieEntry.CONTENT_URI, new String[]{MovieEntry._ID}, WHERE_ID,
                        new String[]{String.valueOf(id)}, null);

        if (cursor == null) {
            return  false;
        }

        boolean hasObject = false;
        if (cursor.moveToFirst()) {
            hasObject = true;
        }
        cursor.close();          // CLOSEEEEEEEEEEEEEEEEEE IT!

        return hasObject;

    }

    public Movie get(long id) {
        Log.d(TAG, "Getting movie with id" + id);
        Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI,
                MOVIE_COLS, WHERE_ID, new String[]{String.valueOf(id)}, null);

        if (cursor == null) {
            return null;
        }

        if (cursor.getCount() == 0){
            cursor.close();
            return  null;
        }

        cursor.moveToFirst();
        Movie movie = getMovieFromCursor(cursor);
        cursor.close();
        return movie;
    }

    public ArrayList<Movie> getAll() {

        ArrayList<Movie> list = new ArrayList<>();

        Cursor cursor =
                mContext.getContentResolver().query(MovieEntry.CONTENT_URI, MOVIE_COLS, null, null, null);

        if (cursor == null) {
            return list;
        }

        while (cursor.moveToNext()) {
            list.add(getMovieFromCursor(cursor));
        }
        cursor.close();

        Log.d(TAG, "Getting all movies. Size is" + list.size());
        return list;
    }

    public long add(Movie movie) {
        Log.d(TAG, "Adding movie: " + "movie = [" + movie.getTitle() + "]");
        return ContentUris.parseId(mContext.getContentResolver()
                .insert(MovieEntry.CONTENT_URI, getContentValuesFromMovie(movie)));
    }

    public void set(Movie movie) {
        Log.d(TAG, "Updating movie: " + "movie = [" + movie.getTitle() + "]");
        mContext.getContentResolver()
                .update(MovieEntry.CONTENT_URI, getContentValuesFromMovie(movie), WHERE_ID,
                        new String[]{String.valueOf(movie.getId())});
    }

    public void delete(Movie movie) {
        Log.d(TAG, "Deleting movie: " + "movie = [" + movie.getTitle() + "]");
        mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, WHERE_ID, new String[]{String.valueOf(movie.getId())});
    }

    public void delete(long id) {
        Log.d(TAG, "Deleting movie by id" + id);
        mContext.getContentResolver().delete(MovieEntry.CONTENT_URI, WHERE_ID,
                new String[]{String.valueOf(id)});
    }

    public static Movie getMovieFromCursor(Cursor openedCursor) {
        Movie movie = new Movie();
        movie.setId(openedCursor.getLong(0));
        movie.setTitle(openedCursor.getString(1));
        movie.setOverview(openedCursor.getString(2));
        movie.setCoverPath(openedCursor.getString(3));
        movie.setBackground(openedCursor.getString(4));
        movie.setReleaseDate(openedCursor.getString(5));
        return movie;
    }

    public static ContentValues getContentValuesFromMovie(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.MOVIE_NAME, movie.getTitle());
        contentValues.put(MovieEntry.MOVIE_OVERVIEW, movie.getOverview());
        contentValues.put(MovieEntry.MOVIE_COVER, movie.getCoverPath());
        contentValues.put(MovieEntry.MOVIE_BACKGROUND, movie.getBackground());
        contentValues.put(MovieEntry.MOVIE_RELEASE_DATE, movie.getReleaseDate());
        return contentValues;
    }
}
