package com.example.sampleapp.interfaces;

import com.example.sampleapp.model.MyData;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ViewData {

    public static final String baseDataUrl="https://dl.dropboxusercontent.com";

     @GET("/s/2iodh4vg0eortkl/facts.json")
     Call<MyData>getData();
}
