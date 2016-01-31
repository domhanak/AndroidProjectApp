package cz.muni.fi.pv256.movio.uco410430.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import cz.muni.fi.pv256.movio.uco410430.App;
import cz.muni.fi.pv256.movio.uco410430.domain.Movie;
import cz.muni.fi.pv256.movio.uco410430.network.Api;
import cz.muni.fi.pv256.movio.uco410430.network.Responses;
import cz.muni.fi.pv256.movio.uco410430.utils.Constants;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

/**
 * Service for downloading Movies.
 *
 * Created by dhanak on 11/30/15.
 */
public class MovieDownloadService extends IntentService {

    public static final int DOWNLOAD_MOVIE = -1;

    public MovieDownloadService() {
        super("MovieDownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("MovieDownloadService" , "onHandleIntent");

        int movieId = intent.getIntExtra("movie_id", -1);
        App app = (App) getApplicationContext();
        RestAdapter adapter =
                new RestAdapter.Builder()
                        .setEndpoint(Constants.BASE_URL)
                        .setClient(new OkClient())
                        .setLogLevel(RestAdapter.LogLevel.FULL)
                        .setLog(new RestAdapter.Log() {
                            @Override
                            public void log(String message) {
                                Log.d("RETROFIT", message);
                            }
                        }).setRequestInterceptor(new RequestInterceptor() {
                            @Override
                        public void intercept(RequestFacade request) {
                                request.addHeader("Accept", "application/json");
                            }
                        }).build();


        Api api = adapter.create(Api.class);
        if ( movieId == DOWNLOAD_MOVIE) {
            api.getMovies(new Callback<Responses.LoadMovieResponse>() {
                @Override
                public void success(final Responses.LoadMovieResponse loadFilmsResponse, final Response response) {
                    Log.d("Downloaded movie:", loadFilmsResponse.mMovies.get(0).getTitle());
                    EventBus.getDefault().post(new Responses.LoadMovieResponse(loadFilmsResponse.mMovies));
                }

                @Override
                public void failure(final RetrofitError error) {
                    Log.d("DownloadService", error.toString());
                }
            });
        } else {
            api.getCast(movieId, new Callback<Responses.LoadCastResponse>() {
                @Override
                public void success(final Responses.LoadCastResponse loadCastResponse, final Response response) {
                    EventBus.getDefault().post(new Responses.LoadCastResponse(loadCastResponse.cast));
                }

                @Override
                public void failure(final RetrofitError error) {
                    Log.d("DownloadService", error.toString());
                }
            });

        }
    }
}
