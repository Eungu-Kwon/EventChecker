package com.swdesign.eventchecker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swdesign.eventchecker.DTO.CompanyInfo;
import com.swdesign.eventchecker.DTO.EventInfo;

import java.util.ArrayList;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.ViewHolder> {
    private ArrayList<CompanyInfo> aData = null;
    Context context;
    AdapterCallback callback;
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_name;

        ViewHolder(View itemView) {
            super(itemView) ;

            text_name = itemView.findViewById(R.id.company_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.changeCompany(aData.get(getAdapterPosition()));
                }
            });
        }
    }

    public  CompanyListAdapter(Context context, ArrayList<CompanyInfo> list, AdapterCallback callback){
        this.context = context;
        aData = list;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CompanyListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_companys, viewGroup, false);
        CompanyListAdapter.ViewHolder vh = new CompanyListAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final CompanyListAdapter.ViewHolder viewHolder, int i) {
        final int idx = i;
        String name = aData.get(i).getName();
        if(name != null) viewHolder.text_name.setText(name);
        else viewHolder.text_name.setText("null");
    }

    @Override
    public int getItemCount() {
        return aData.size();
    }
}
