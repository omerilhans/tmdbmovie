package com.omerilhanli.tmdbmove.DI.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omerilhanli.tmdbmove.BuildConfig;
import com.omerilhanli.tmdbmove.api.TmdbApi;
import com.omerilhanli.tmdbmove.api.constants.ApiConstant;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

        if (BuildConfig.DEBUG) {

            // Logging, Debug Mode
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        } else {

            // No logging, Release Mode
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(ApiConstant.KEY_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(ApiConstant.KEY_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(ApiConstant.KEY_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    JacksonConverterFactory provideJacksonConverterFactory() {
        ObjectMapper objectMapper = new ObjectMapper();
        return JacksonConverterFactory
                .create(objectMapper);
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(JacksonConverterFactory jacksonConverterFactory,
                                    OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(ApiConstant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(jacksonConverterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public TmdbApi provideApiService(Retrofit retrofit) {
        return retrofit.create(TmdbApi.class);
    }

}

