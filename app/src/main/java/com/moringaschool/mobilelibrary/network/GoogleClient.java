package com.moringaschool.mobilelibrary.network;

import com.moringaschool.mobilelibrary.Constants;
import com.moringaschool.mobilelibrary.model.GoogleBooksSearchResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.moringaschool.mobilelibrary.Constants.GOOGLE_BASE_URL;

public class GoogleClient {


    private static Retrofit retrofit = null;

    public static GoogleApi getClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        if (retrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(httpLoggingInterceptor);
            okHttpClient.build();
        }
        retrofit = new Retrofit
                .Builder()
                .baseUrl(Constants.GOOGLE_BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(GoogleApi.class);
    }
}

