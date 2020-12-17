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

import com.swdesign.eventchecker.DTO.EventInfo;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private ArrayList<EventInfo> aData = null;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_title, text_time, text_content;
        Switch sw;
        CheckBox delete_check;
        ViewHolder(View itemView) {
            super(itemView) ;

            text_title = itemView.findViewById(R.id.l_item_title);
            text_time = itemView.findViewById(R.id.l_item_date);
            text_content = itemView.findViewById(R.id.l_item_content);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mTag", text_title.getText().toString());
//                    Intent modiIntent = new Intent(v.getContext(), AlarmSettingActivity.class);
//                    modiIntent.putExtra("isNew", false);
//                    modiIntent.putExtra("modi_idx", getAdapterPosition());
//                    v.getContext().startActivity(modiIntent);
                }
            });
        }
    }

    public  EventListAdapter(Context context, ArrayList<EventInfo> list){
        this.context = context;
        aData = list;
    }

    @NonNull
    @Override
    public EventListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_events, viewGroup, false);
        EventListAdapter.ViewHolder vh = new EventListAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final int idx = i;
        String title = aData.get(i).getTitle();
        String time = aData.get(i).getDate();
        viewHolder.text_title.setText(title);
        viewHolder.text_time.setText(time);
    }

    @Override
    public int getItemCount() {
        return aData.size();
    }
}
