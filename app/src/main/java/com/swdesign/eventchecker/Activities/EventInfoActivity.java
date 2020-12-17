package com.swdesign.eventchecker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.swdesign.eventchecker.DAO.DBApi;
import com.swdesign.eventchecker.DAO.DBCallback;
import com.swdesign.eventchecker.DAO.EventRepository;
import com.swdesign.eventchecker.DTO.EventInfo;
import com.swdesign.eventchecker.DTO.MyFavoriteInfo;
import com.swdesign.eventchecker.R;

import java.io.File;

public class EventInfoActivity extends AppCompatActivity implements DBCallback {
    EventInfo event;
    TextView title_view, content_view, date_view;
    ImageView banner, content_image;
    MenuItem add_f, delete_f;
    EventRepository repository;
    MyFavoriteInfo favoriteInfo;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        getSupportActionBar().setTitle("이벤트 정보");

        repository = new EventRepository("http://155.230.52.58:26287/", this, this);
        favoriteInfo = new MyFavoriteInfo();

        SharedPreferences sharedPref = getSharedPreferences("appData", MODE_PRIVATE);
        userid = sharedPref.getString("ID", "");

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
        if(!event.getContent().startsWith("http")) {
            content_view.setText(event.getContent());
            content_view.setVisibility(View.VISIBLE);
        }
        else{
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), event.getContent().substring(event.getContent().lastIndexOf("/")+1));
            if(file.exists()){
                content_image.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                content_image.setVisibility(View.VISIBLE);
            }
            else{
                content_image.setVisibility(View.GONE);
            }
            content_view.setVisibility(View.GONE);
        }
        date_view.setText(event.getDate());
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), event.getImageurl().substring(event.getImageurl().lastIndexOf("/")+1));
        if(file.exists()){
            banner.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            banner.setVisibility(View.VISIBLE);
        }
        else{
            banner.setVisibility(View.GONE);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void dbDone(int code) {
        if(code == EventRepository.FAVORITE_DONE){
            if(favoriteInfo.getId().equals("")){
                add_f.setVisible(true);
                delete_f.setVisible(false);
            }
            else{
                add_f.setVisible(false);
                delete_f.setVisible(true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        add_f = menu.findItem(R.id.m_add_favorite);
        delete_f = menu.findItem(R.id.m_delete_favorite);

        repository.getFavorite(userid, event.getId(), favoriteInfo);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyboard();
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.m_add_favorite:
                add_f.setVisible(false);
                delete_f.setVisible(true);
                repository.postFavoriteList(userid, event.getId(), favoriteInfo);
                return true;
            case R.id.m_delete_favorite:
                add_f.setVisible(true);
                delete_f.setVisible(false);
                repository.deleteFavorite(favoriteInfo.getId());
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private void hideKeyboard(){
        if(getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}