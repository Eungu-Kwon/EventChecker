package com.swdesign.eventchecker;

import android.util.Log;

import com.swdesign.eventchecker.DTO.CompanyInfo;
import com.swdesign.eventchecker.DTO.EventInfo;

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
    public EventRepository(String url, DBCallback listener) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        dbCallback = listener;
        api = retrofit.create(DBApi.class);
    }

    public void setEventList(String company, ArrayList<EventInfo> list){
        list.clear();
        api.getEvent(company).enqueue(new Callback<List<EventInfo>>() {
            @Override
            public void onResponse(Call<List<EventInfo>> call, Response<List<EventInfo>> response) {
                if(response.isSuccessful()){
                    List<EventInfo> event = response.body();
                    for(EventInfo i : event){
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
