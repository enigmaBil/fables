package com.example.fables.data.api;

import com.example.fables.data.model.Fable;
import com.example.fables.data.request.LoginRequest;
import com.example.fables.data.request.RegisterRequest;
import com.example.fables.data.response.LoginResponse;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FableApiService {

    @POST("auth/register")
    Call<LoginResponse> register(@Body RegisterRequest request);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("fables")
    Call<List<Fable>> getFables();

    @POST("fables")
    Call<ResponseBody> addFable(@Header("Authorization") String token, @Body Fable fable);

}
