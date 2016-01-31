package cz.muni.fi.pv256.movio.uco410430.network;

import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.utils.Constants;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Interface for rest api.
 *
 * Created by dhanak on 11/30/15.
 */
public interface Api {
    @GET(Constants.POPULAR_URL + Constants.API_KEY)
    void getMovies(Callback<Responses.LoadMovieResponse> response);

    @GET("/movie/{id}/credits" + Constants.API_KEY)
    void getCast(@Path("id") int id, Callback<Responses.LoadCastResponse> response);
}
