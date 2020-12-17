package com.swdesign.eventchecker.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swdesign.eventchecker.DAO.DBCallback;
import com.swdesign.eventchecker.DAO.EventRepository;
import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.DTO.MyFavoriteInfo;
import com.swdesign.eventchecker.ListAdapter.EventListAdapter;
import com.swdesign.eventchecker.R;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment implements DBCallback {
    View v;
    ArrayList<MyFavoriteInfo> favorite_list;
    ArrayList<EventInfo> list;
    EventListAdapter listAdapter;
    RecyclerView recyclerView;
    EventRepository r;
    String userid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frame_favorite, container, false);
        r = new EventRepository("http://155.230.52.58:26287/", this, getContext());
        SharedPreferences sharedPref = v.getContext().getSharedPreferences("appData", Context.MODE_PRIVATE);
        userid = sharedPref.getString("ID", "");

        init_list();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        setList();
    }

    void init_list(){
        list = new ArrayList<>();
        favorite_list = new ArrayList<>();
        listAdapter = new EventListAdapter(v.getContext(), list);

        recyclerView = v.findViewById(R.id.favorite_event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(listAdapter);

    }

    public final void setList(){
        list.clear();
        favorite_list.clear();
        r.setFavoriteList(userid, favorite_list);
    }

    @Override
    public void dbDone(int code) {
        if(code == EventRepository.FAVORITE_DONE){
            for(MyFavoriteInfo i : favorite_list){
                r.getEvent(i.getEventid(), list);
            }
        }
        else if(code == EventRepository.EVENT_LIST_DONE)
            listAdapter.notifyDataSetChanged();
    }
}
