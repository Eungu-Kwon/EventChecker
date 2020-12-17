package com.swdesign.eventchecker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.swdesign.eventchecker.DAO.DBCallback;
import com.swdesign.eventchecker.DTO.UserInfo;
import com.swdesign.eventchecker.DAO.EventRepository;
import com.swdesign.eventchecker.R;

public class SignupActivity extends AppCompatActivity implements DBCallback {

    EventRepository repository;
    TextView edit_id, edit_pass;
    UserInfo user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("회원가입");

        repository = new EventRepository("http://155.230.52.58:26287/", this, this);
        edit_id = findViewById(R.id.signup_id);
        edit_pass = findViewById(R.id.signup_pass);
        edit_pass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    signUp(null);
                    hideKeyboard();
                    return true;
                }
                return false;
            }
        });
        user = new UserInfo();
    }

    public void signUp(View view) {
        if(edit_id.getText().toString().equals("") || edit_pass.getText().toString().equals("")){
            Toast.makeText(this, "ID와 Password를 체크해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        repository.getUser(edit_id.getText().toString(), user);
    }

    @Override
    public void dbDone(int code) {
        if(code == EventRepository.USER_DONE){
            if(user.getId().equals("")){
                repository.postUser(edit_id.getText().toString(), edit_pass.getText().toString());
                SharedPreferences sharedPref = getSharedPreferences("appData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("ID", edit_id.getText().toString());
                editor.apply();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(this, "이미 존재하는 ID입니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void hideKeyboard(){
        if(getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}