package com.swdesign.eventchecker.DAO;

import com.swdesign.eventchecker.DTO.CompanyInfo;
import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.DTO.MyFavoriteInfo;
import com.swdesign.eventchecker.DTO.UserInfo;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
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

    @GET("/favorite")
    Call<List<MyFavoriteInfo>> getFavorite(@Query("id") String userid, @Query("eventid") String eventid);

    @POST("/favorite")
    Call<MyFavoriteInfo> postFavorite(@Query("id") String user, @Query("eventid") String eventid);

    @DELETE("/favorite/{fid}")
    Call<MyFavoriteInfo> deleteFavorite(@Path("fid") String favorite_id);

    @GET("/user")
    Call<List<UserInfo>> getUser(@Query("id") String userid);

    @POST("/user")
    Call<UserInfo> postUser(@Query("id") String id, @Query("passwd") String passwd);
}
