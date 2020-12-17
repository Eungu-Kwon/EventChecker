package com.swdesign.eventchecker;

import com.swdesign.eventchecker.DTO.CompanyInfo;
import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.DTO.MyFavoriteInfo;
import com.swdesign.eventchecker.DTO.UserInfo;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DBApi {
    @GET("/event")
    Call<List<EventInfo>> getEvent(@Query("company") String company);

    @GET("/event")
    Call<List<EventInfo>> getEvent();

    @GET("/company")
    Call<List<CompanyInfo>> getCompany();

    @GET("/favorite")
    Call<List<MyFavoriteInfo>> getFavorite(@Query("id") String userid);

    @FormUrlEncoded
    @POST("/favorite")
    Call<MyFavoriteInfo> postFavorite(@FieldMap HashMap<String, Object> params);

    @GET("/user")
    Call<List<UserInfo>> getUser(@Query("id") String userid);

    @POST("/user")
    Call<UserInfo> postUser(@Query("id") String id, @Query("passwd") String passwd);
}
