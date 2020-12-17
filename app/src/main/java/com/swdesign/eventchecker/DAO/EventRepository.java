package com.swdesign.eventchecker.DAO;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.swdesign.eventchecker.DTO.CompanyInfo;
import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.DTO.MyFavoriteInfo;
import com.swdesign.eventchecker.DTO.UserInfo;
import com.swdesign.eventchecker.StaticMethod.ImageFileManager;
import com.swdesign.eventchecker.StaticMethod.ImageOpener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// DAO
public class EventRepository {
    public static final int EVENT_DONE = 384;
    public static final int COMPANY_DONE = 246;
    public static final int USER_DONE = 546;
    DBApi api;
    DBCallback dbCallback;
    Context c;
    public EventRepository(String url, DBCallback listener, Context c) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();
        dbCallback = listener;
        api = retrofit.create(DBApi.class);

        this.c = c;
    }

    public void setEventList(String company, ArrayList<EventInfo> list){
        list.clear();
        api.getEvent(company).enqueue(new Callback<List<EventInfo>>() {
            @Override
            public void onResponse(Call<List<EventInfo>> call, Response<List<EventInfo>> response) {
                if(response.isSuccessful()){
                    List<EventInfo> event = response.body();
                    for(EventInfo i : event){
                        File file = new File(c.getExternalFilesDir(Environment.DIRECTORY_PICTURES), i.getImageurl().substring(i.getImageurl().lastIndexOf("/")+1) + ".jpg_icon");
                        if(!file.exists()){
                            Thread thread = new Thread(){
                                @Override
                                public void run() {
                                    File image = ImageOpener.openImage(c, i.getImageurl());

                                    if(image != null){
                                        ImageFileManager.saveImageIcon(c, image);
                                    }
                                }
                            };
                            thread.start();
                            try {
                                thread.join();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        list.add(i);
                    }
                    if(dbCallback != null) dbCallback.dbDone(EVENT_DONE);
                }
            }

            @Override
            public void onFailure(Call<List<EventInfo>> call, Throwable t) {
                Log.d("mTag", t.toString());
            }
        });
    }

    public void setCompanyList(ArrayList<CompanyInfo> list){
        list.clear();
        api.getCompany().enqueue(new Callback<List<CompanyInfo>>() {
            @Override
            public void onResponse(Call<List<CompanyInfo>> call, Response<List<CompanyInfo>> response) {
                if(response.isSuccessful()){
                    List<CompanyInfo> event = response.body();
                    for(CompanyInfo i : event){
                        list.add(i);
                    }
                    if(dbCallback != null) dbCallback.dbDone(COMPANY_DONE);
                }
            }

            @Override
            public void onFailure(Call<List<CompanyInfo>> call, Throwable t) {
                Log.d("mTag", t.toString());
            }
        });
    }

    public void setFavoriteList(String userid, ArrayList<MyFavoriteInfo> list){
        api.getFavorite(userid).enqueue((new Callback<List<MyFavoriteInfo>>() {
            @Override
            public void onResponse(Call<List<MyFavoriteInfo>> call, Response<List<MyFavoriteInfo>> response) {
                List<MyFavoriteInfo> event = response.body();
                for(MyFavoriteInfo i : event){
                    list.add(i);
                }
                if(dbCallback != null) dbCallback.dbDone(COMPANY_DONE);
            }

            @Override
            public void onFailure(Call<List<MyFavoriteInfo>> call, Throwable t) {
                Log.d("mTag", t.toString());
            }
        }));
    }

    public void postFavoriteList(String userid, String eventid){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", userid);
        hashMap.put("eventid", eventid);
        api.postFavorite(hashMap).enqueue(new Callback<MyFavoriteInfo>() {
            @Override
            public void onResponse(Call<MyFavoriteInfo> call, Response<MyFavoriteInfo> response) {
                if(response.isSuccessful()){
                    Log.d("mTag", "post success");
                }
            }

            @Override
            public void onFailure(Call<MyFavoriteInfo> call, Throwable t) {
                Log.d("mTag", t.toString());
            }
        });
    }

    public void getUser(String userid, UserInfo userInfo){
        api.getUser(userid).enqueue(new Callback<List<UserInfo>>() {
            @Override
            public void onResponse(Call<List<UserInfo>> call, Response<List<UserInfo>> response) {
                if(response.body().size() != 0){
                    UserInfo temp = response.body().get(0);
                    userInfo.setId(temp.getId());
                    userInfo.setPasswd(temp.getPasswd());
                }
                else {
                    userInfo.setId("");
                }
                if(dbCallback != null) dbCallback.dbDone(USER_DONE);
            }

            @Override
            public void onFailure(Call<List<UserInfo>> call, Throwable t) {
                Log.d("mTag", t.toString());
            }
        });
    }

    public void postUser(String userid, String passwd){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", userid);
        hashMap.put("passwd", passwd);

        api.postUser(userid, passwd).enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if(response.isSuccessful()){
                    Log.d("mTag", "post success " + response.body().getId() + "/" + response.body().getPasswd());
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                Log.d("mTag", t.toString());
            }
        });
    }
}
