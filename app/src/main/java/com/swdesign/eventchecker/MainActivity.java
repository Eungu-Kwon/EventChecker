package com.swdesign.eventchecker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.swdesign.eventchecker.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_bar);

        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        mainFragment = new MainFragment();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.bottom_home:
                        replaceFragment(mainFragment);
                        break;
                    case R.id.bottom_list:
                        //replaceFragment(fragment);
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
}