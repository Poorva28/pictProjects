package com.example.e_notice_board;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.*;
import android.content.*;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Student_login(View v) {
        startActivity(new Intent(this, StudentLogin.class));

    }

    public void Teacher_login(View v) {
        startActivity(new Intent(this, TeacherLogin.class));
    }

}

