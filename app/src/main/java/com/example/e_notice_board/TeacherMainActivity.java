package com.example.e_notice_board;

import androidx.appcompat.app.AppCompatActivity;
import android.content.*;
import android.os.Bundle;
import android.widget.*;
import android.view.*;

public class TeacherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);

    }

    public void uploadFile(View v)
    {
        startActivity(new Intent(TeacherMainActivity.this,TeacherUploadFile.class));
    }

    public void deleteFile(View v)
    {
        startActivity(new Intent(TeacherMainActivity.this,TeacherDeleteFile.class));
    }


}
