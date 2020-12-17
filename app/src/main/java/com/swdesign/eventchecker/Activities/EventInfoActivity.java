package com.swdesign.eventchecker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.R;

import java.io.File;

public class EventInfoActivity extends AppCompatActivity {
    EventInfo event;
    TextView title_view, content_view, date_view;
    ImageView banner, content_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Intent intent = getIntent();
        event = (EventInfo) intent.getExtras().getSerializable("event");
        initView();
    }

    void initView(){
        title_view = findViewById(R.id.event_title);
        content_view = findViewById(R.id.event_content);
        date_view = findViewById(R.id.event_date);
        banner = findViewById(R.id.event_banner_image);
        content_image = findViewById(R.id.event_content_image);

        title_view.setText(event.getTitle());
        content_view.setText(event.getContent());
        date_view.setText(event.getDate());
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), event.getImageurl().substring(event.getImageurl().lastIndexOf("/")+1) + ".jpg");
        if(file.exists()){
            content_image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            content_image.setVisibility(View.VISIBLE);
        }
        else{
            content_image.setVisibility(View.GONE);
        }
    }
}