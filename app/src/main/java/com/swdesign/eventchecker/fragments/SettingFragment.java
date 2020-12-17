package com.swdesign.eventchecker.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.swdesign.eventchecker.DAO.DBCallback;
import com.swdesign.eventchecker.DAO.EventRepository;
import com.swdesign.eventchecker.DTO.CompanyInfo;
import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.DTO.MyFavoriteInfo;
import com.swdesign.eventchecker.DTO.UserInfo;
import com.swdesign.eventchecker.ListAdapter.CompanyListAdapter;
import com.swdesign.eventchecker.ListAdapter.EventListAdapter;
import com.swdesign.eventchecker.R;

import java.io.File;
import java.util.ArrayList;

public class SettingFragment extends Fragment implements DBCallback {
    View v;
    EventRepository r;
    Button logout_b, sec_b;
    ArrayList<MyFavoriteInfo> list;
    String user;
    UserInfo userInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frame_setting, container, false);
        r = new EventRepository("http://155.230.52.58:26287/", this, getContext());
        logout_b = v.findViewById(R.id.logout_b);
        sec_b = v.findViewById(R.id.sec_b);

        logout_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder urlDialog = new AlertDialog.Builder(v.getContext());
                urlDialog.setTitle("로그아웃 하시겠습니까?")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPref = v.getContext().getSharedPreferences("appData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove("ID");
                                editor.apply();
                                getActivity().finish();
                            }
                        })
                        .setPositiveButton("취소", null);
                urlDialog.show();
            }
        });

        sec_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder urlDialog = new AlertDialog.Builder(v.getContext());
                urlDialog.setTitle("정말로 탈퇴하시겠습니까?")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sharedPref = v.getContext().getSharedPreferences("appData", Context.MODE_PRIVATE);
                                user = sharedPref.getString("ID", "");
                                list = new ArrayList<>();
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.remove("ID");
                                editor.apply();

                                r.setFavoriteList(user, list);
                            }
                        })
                        .setPositiveButton("취소", null);
                urlDialog.show();
            }
        });
        return v;
    }

    @Override
    public void dbDone(int code) {
        if(code == EventRepository.FAVORITE_DONE){

            for(MyFavoriteInfo info : list){
                r.deleteFavorite(info.getId());
            }
            userInfo = new UserInfo();
            r.getUser(user, userInfo);
        }
        else if(code == EventRepository.USER_DONE){
            r.deleteUser(userInfo.getDbid());
            getActivity().finish();
        }
    }
}
