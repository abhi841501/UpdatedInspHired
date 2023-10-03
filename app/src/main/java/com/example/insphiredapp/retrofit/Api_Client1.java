package com.example.insphiredapp.retrofit;

import com.example.insphiredapp.Util.LenientGsonConverterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Api_Client1 {

    public static final String BASE_URL = "http://192.168.1.17/insphire/api/";

    private static Retrofit retrofit = null;
    private static Api api;
    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Api getClient() {
        if (api == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    //.addConverterFactory(GsonConverterFactory.create(gson))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(LenientGsonConverterFactory.create(gson))
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            Api api = retrofit.create(Api.class);

            return api;
        } else
            return api;



    }

}
