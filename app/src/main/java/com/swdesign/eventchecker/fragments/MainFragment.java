package com.swdesign.eventchecker.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swdesign.eventchecker.AdapterCallback;
import com.swdesign.eventchecker.CompanyListAdapter;
import com.swdesign.eventchecker.DBCallback;
import com.swdesign.eventchecker.DTO.CompanyInfo;
import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.EventListAdapter;
import com.swdesign.eventchecker.EventRepository;
import com.swdesign.eventchecker.R;

import java.util.ArrayList;

public class MainFragment extends Fragment implements DBCallback, AdapterCallback {
    View v;
    ArrayList<EventInfo> list;
    ArrayList<CompanyInfo> companyList;
    EventListAdapter listAdapter;
    CompanyListAdapter companyListAdapter;
    RecyclerView recyclerView;
    RecyclerView companyRecyclerView;
    EventRepository r;
    CompanyInfo cur_company;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frame_home, container, false);
        r = new EventRepository("http://155.230.52.58:26287/", this, getContext());
        cur_company = null;
        init_list();
        return v;
    }

    void init_list(){
        list = new ArrayList<>();
        companyList = new ArrayList<>();

        listAdapter = new EventListAdapter(v.getContext(), list);
        companyListAdapter = new CompanyListAdapter(v.getContext(), companyList, this);

        recyclerView = v.findViewById(R.id.main_event_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(listAdapter);

        companyRecyclerView = v.findViewById(R.id.main_company_list);
        companyRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false));
        companyRecyclerView.setAdapter(companyListAdapter);
        r.setCompanyList(companyList);
    }

    public final void setList(){
        list.clear();
        r.setEventList(cur_company.getEnglishname(), list);
    }

    @Override
    public void dbDone() {
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void companyDone() {
        cur_company = companyList.get(0);
        setList();
    }

    @Override
    public void changeCompany(CompanyInfo company) {
        cur_company = company;
        setList();
    }
}
