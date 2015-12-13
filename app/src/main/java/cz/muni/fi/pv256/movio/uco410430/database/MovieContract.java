package cz.muni.fi.pv256.movio.uco410430.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Definition of MovieContract.
 *
 * Created by dhanak on 12/13/15.
 */
public class MovieContract {

    public MovieContract() {
    }

    public static final String CONTENT_AUTHORITY = "cz.muni.fi.pv256.movio.uco410430";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_NAME = "title";
        public static final String MOVIE_COVER = "coverPath";
        public static final String MOVIE_OVERVIEW = "overview";
        public static final String MOVIE_BACKGROUND = "background";
        public static final String MOVIE_RELEASE_DATE = "releaseDate";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
