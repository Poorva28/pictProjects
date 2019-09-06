package com.example.e_notice_board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.*;
import com.google.firebase.database.FirebaseDatabase;

public class TeacherLogin extends AppCompatActivity {

    FirebaseAuth teacher;
    EditText email,phone,name,password,teach_id;
    ProgressDialog pd;
    FirebaseDatabase fd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phoneNo);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        teach_id=findViewById(R.id.teacherId);
        teacher =FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        fd=FirebaseDatabase.getInstance();
    }

    public void register_teacher(View v)
    {
        final String teach_email,teach_phone,teach_name,teach_pass,teacherid;
        teach_email=email.getText().toString();
        teach_phone=phone.getText().toString();
        teach_name=name.getText().toString();
        teach_pass=password.getText().toString();
        teacherid=teach_id.getText().toString();
        pd.setMessage("Wait");
        pd.show();
        if(TextUtils.isEmpty(teach_email)|| TextUtils.isEmpty(teach_phone)||TextUtils.isEmpty(teach_name)||TextUtils.isEmpty(teach_pass))//not verified teacher id field
        {
            Toast.makeText(this,"Please enter all the fields",Toast.LENGTH_SHORT).show();
            pd.dismiss();
            return;
        }
            pd.show();
        teacher.createUserWithEmailAndPassword(teach_email,teach_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            public void onComplete(Task<AuthResult> task)
            {
                    if(task.isSuccessful())
                    {
                        teacher.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>(){

                            public void onComplete(Task<Void> task){
                                    if(task.isSuccessful())
                                    {
                                        Teacher t=new Teacher(teach_name,teach_email,teach_phone,teacherid);
                                        fd.getReference("teacher").child(teacherid).setValue(t);
                                        pd.dismiss();
                                        Toast.makeText(TeacherLogin.this,"Registered successfully please check your email for verification",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(TeacherLogin.this,LoginTeacherpage.class));
                                    }
                                    else
                                    {
                                        pd.dismiss();
                                        Toast.makeText(TeacherLogin.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                        return;
                                    }
                            }
                        });
                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(TeacherLogin.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        return;
                    }
            }

        });

    }

    public void Login(View v)
    {
        startActivity(new Intent(this,LoginTeacherpage.class));
    }
}

