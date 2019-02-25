package com.omerilhanli.tmdbmove.api;


import com.omerilhanli.tmdbmove.api.constants.ApiConstant;
import com.omerilhanli.tmdbmove.api.constants.ApiEndPoint;
import com.omerilhanli.tmdbmove.data.model.Configuration;
import com.omerilhanli.tmdbmove.data.model.Genres;
import com.omerilhanli.tmdbmove.data.model.Movie;
import com.omerilhanli.tmdbmove.data.model.Movies;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface TmdbApi {

    @GET(ApiEndPoint.END_ALL)
    Observable<Movies> getMovies(@Query(ApiConstant.KEY_API) String apiKey,
                                 @Query(ApiConstant.KEY_RELEASE_DATE) String releaseDate,
                                 @Query(ApiConstant.KEY_SORT_BY) String sortBy, @Query(ApiConstant.KEY_PAGE) int page);

    @GET(ApiEndPoint.END_NOW_PLAYING)
    Observable<Movies> getMoviesNowPlaying(@Query(ApiConstant.KEY_API) String apiKey, @Query(ApiConstant.KEY_PAGE) int page);

    @GET(ApiEndPoint.END_POPULAR)
    Observable<Movies> getMoviesPopular(@Query(ApiConstant.KEY_API) String apiKey, @Query(ApiConstant.KEY_PAGE) int page);

    @GET(ApiEndPoint.END_TOP_RATED)
    Observable<Movies> getMoviesTopRated(@Query(ApiConstant.KEY_API) String apiKey, @Query(ApiConstant.KEY_PAGE) int page);

    @GET(ApiEndPoint.END_UP_COMING)
    Observable<Movies> getMoviesUpcoming(@Query(ApiConstant.KEY_API) String apiKey, @Query(ApiConstant.KEY_PAGE) int page);


    @Headers("Cache-Control: public, max-stale=2419200")
    @GET(ApiEndPoint.END_CONFIGURATION)
    Observable<Configuration> getConfiguration(@Query(ApiConstant.KEY_API) String apiKey);

    @GET(ApiEndPoint.END_GENRES)
    Observable<Genres> getGenres(@Query(ApiConstant.KEY_API) String apiKey);

    // ------------------------------------------------------------------------

    @GET(ApiEndPoint.END_DETAIL)
    Observable<Movie> getMovie(@Path(ApiConstant.KEY_MOVE_ID) int movieId, @Query(ApiConstant.KEY_API) String apiKey);

    @GET(ApiEndPoint.END_SEARCH)
    Observable<Movies> getSearchResult(@Query(ApiConstant.KEY_API) String apiKey,
                                       @Query(ApiConstant.KEY_QUERY) String query);

}
