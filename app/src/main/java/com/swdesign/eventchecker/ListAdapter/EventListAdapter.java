package com.swdesign.eventchecker.ListAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swdesign.eventchecker.Activities.EventInfoActivity;
import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.R;

import java.io.File;
import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private ArrayList<EventInfo> aData = null;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView text_title, text_time, text_content;
        ImageView banner;
        Switch sw;
        CheckBox delete_check;
        ViewHolder(View itemView) {
            super(itemView) ;

            text_title = itemView.findViewById(R.id.l_item_title);
            text_time = itemView.findViewById(R.id.l_item_date);
            text_content = itemView.findViewById(R.id.l_item_content);
            banner = itemView.findViewById(R.id.l_item_banner);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent newintent = new Intent(v.getContext(), EventInfoActivity.class);
                    newintent.putExtra("event", aData.get(getAdapterPosition()));
                    v.getContext().startActivity(newintent);
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
        String content = aData.get(i).getContent();
        String time = aData.get(i).getDate();
        viewHolder.text_title.setText(title);
        viewHolder.text_content.setText(content);
        viewHolder.text_time.setText(time);

        File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), aData.get(i).getImageurl().substring(aData.get(i).getImageurl().lastIndexOf("/")+1) + "_icon");
        if(file.exists()){
            viewHolder.banner.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            viewHolder.banner.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.banner.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return aData.size();
    }
}
