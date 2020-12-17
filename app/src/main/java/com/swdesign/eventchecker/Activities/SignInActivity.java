package com.swdesign.eventchecker.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.swdesign.eventchecker.DAO.DBCallback;
import com.swdesign.eventchecker.DTO.UserInfo;
import com.swdesign.eventchecker.DAO.EventRepository;
import com.swdesign.eventchecker.R;

public class SignInActivity extends AppCompatActivity implements DBCallback {

    EventRepository repository;
    TextView edit_id, edit_pass;
    UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        repository = new EventRepository("http://155.230.52.58:26287/", this, this);
        edit_id = findViewById(R.id.signin_id);
        edit_pass = findViewById(R.id.signin_pass);
        user = new UserInfo();
    }

    public void signIn(View view) {
        repository.getUser(edit_id.getText().toString(), user);
    }

    @Override
    public void dbDone(int code) {
        if(code == EventRepository.USER_DONE){
            if(user.getId().equals("")){
                Toast.makeText(this, "존재하지 않는 사용자입니다.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(user.getPasswd().equals(edit_pass.getText().toString())){
                Toast.makeText(this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("ID", edit_id.getText().toString());
            editor.commit();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}