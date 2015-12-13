package cz.muni.fi.pv256.movio.uco410430.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.database.MovieContract.MovieEntry;

/**
 * Database helper
 *
 * Created by dhanak on 12/13/15.
 */
public class MovieDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                MovieEntry.MOVIE_NAME + " TEXT, " +
                MovieEntry.MOVIE_OVERVIEW + " TEXT, " +
                MovieEntry.MOVIE_COVER + " TEXT, " +
                MovieEntry.MOVIE_BACKGROUND + " TEXT, " +
                MovieEntry.MOVIE_RELEASE_DATE + " TEXT " +
                " );";
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
