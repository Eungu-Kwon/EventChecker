package com.swdesign.eventchecker;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.swdesign.eventchecker.DTO.CompanyInfo;
import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.StaticMethod.ImageFileManager;
import com.swdesign.eventchecker.StaticMethod.ImageOpener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// DAO
public class EventRepository {
    DBApi api;
    DBCallback dbCallback;
    Context c;
    public EventRepository(String url, DBCallback listener, Context c) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
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
                    dbCallback.dbDone();
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
                    dbCallback.companyDone();
                }
            }

            @Override
            public void onFailure(Call<List<CompanyInfo>> call, Throwable t) {
                Log.d("mTag", t.toString());
            }
        });
    }
}
