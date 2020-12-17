package com.swdesign.eventchecker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.swdesign.eventchecker.R;
import com.swdesign.eventchecker.fragments.FavoriteFragment;
import com.swdesign.eventchecker.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {
    final long FINISH_INTERVAL_TIME = 2000;
    long backPressedTime = 0;
    MainFragment mainFragment;
    FavoriteFragment favoriteFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_bar);

        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        mainFragment = new MainFragment();
        favoriteFragment = new FavoriteFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.bottom_home:
                        replaceFragment(mainFragment);
                        break;
                    case R.id.bottom_list:
                        replaceFragment(favoriteFragment);
                        break;
                    case R.id.bottom_setting:
                        fm.popBackStack();

                }
                return true;
            }
        });

        fragmentTransaction.add(R.id.main_frame, mainFragment).commit();
    }

    void replaceFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            moveTaskToBack(true);
            finish();
        }
        else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }
}