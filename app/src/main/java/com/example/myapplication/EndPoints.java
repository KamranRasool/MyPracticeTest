package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EndPoints {

    @GET("/posts")
    Call<List<RetroPosts>> getAllPosts();
    @GET("posts/{id}/comments")
    Call<List<RetroComments>> getComments(@Path(value = "id", encoded = true) String Id);
}
