package com.example.spincarsearch.Services;

import com.example.spincarsearch.Models.GImageSearch;
import com.example.spincarsearch.Models.Image;
import com.example.spincarsearch.Models.Item;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleInterface {

    @GET("customsearch/v1")
    Call<GImageSearch> googleSearchCall(@Query("key") String key, @Query("cx") String cx, @Query("q") String q, @Query("searchType") String searchType,
            @Query("start") int startIndex, @Query("num") int num);

    }

