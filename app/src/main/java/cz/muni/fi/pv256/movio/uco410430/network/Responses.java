package cz.muni.fi.pv256.movio.uco410430.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;

/**
 * Class defining response for movies.
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
    }
}
