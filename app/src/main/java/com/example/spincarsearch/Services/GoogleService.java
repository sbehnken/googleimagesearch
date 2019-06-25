package com.example.spincarsearch.Services;

import com.example.spincarsearch.BuildConfig;
import com.example.spincarsearch.Models.GImageSearch;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleService {

    private GoogleInterface googleInterface;

    public GoogleService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        googleInterface = retrofit.create(GoogleInterface.class);
    }

    public Call<GImageSearch> googleSearchCall(String q, int startIndex, int num) {
        return googleInterface.googleSearchCall(BuildConfig.ApiKey, BuildConfig.CustomSearchEngine, q, "image", startIndex, num);
    }
}
