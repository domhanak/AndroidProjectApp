package cz.muni.fi.pv256.movio.uco410430.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.domain.Cast;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

/**
 * Class defining responses for movies.
 *
 * Created by dhanak on 11/30/15.
 */
public class Responses {

    private Responses() {}

    public static class LoadMovieResponse {
        @SerializedName("results")
        public List<Movie> mMovies;

        public LoadMovieResponse(final List<Movie> movies) {
            mMovies = movies;
        }

        public List<Movie> getMovies() {
            return mMovies;
        }
    }

    public static class LoadCastResponse {
        @SerializedName("cast")
        public List<Cast> mCast;

        public LoadCastResponse(final List<Cast> cast) {
            mCast = cast;
        }
        public List<Cast> getCast() {
            return mCast;
        }
    }
}
