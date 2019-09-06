package com.example.e_notice_board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.view.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import android.content.*;

public class StudentLogin extends AppCompatActivity {
    Spinner year,section,lab;
    EditText email,password,name,rollno;
    FirebaseAuth student;
    FirebaseDatabase stud;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        year=findViewById(R.id.year);
        section=findViewById(R.id.section);
        lab=findViewById(R.id.lab);
        rollno=findViewById(R.id.rollno);

        ArrayAdapter<String> yearadapter=new ArrayAdapter<String>(StudentLogin.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.year));
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearadapter);

        ArrayAdapter<String> sectionadapter=new ArrayAdapter<String>(StudentLogin.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.section));
        sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        section.setAdapter(sectionadapter);

        ArrayAdapter<String> studlabs=new ArrayAdapter<>(StudentLogin.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.labs));
        studlabs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lab.setAdapter(studlabs);

        student=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        name=findViewById(R.id.name);
        stud=FirebaseDatabase.getInstance();
        pd=new ProgressDialog(this);
    }


    public void register_student(View v)
    {
        final String stud_email,stud_password;
        final String stud_name,stud_year,stud_lab,stud_rno;
        final String stud_section;

        stud_email=email.getText().toString();
        stud_password=password.getText().toString();
        stud_name=name.getText().toString();
        stud_year=year.getSelectedItem().toString();
        stud_section=section.getSelectedItem().toString();
        stud_lab=lab.getSelectedItem().toString();
        stud_rno=rollno.getText().toString();
            pd.setMessage("Loading");
            pd.show();
        if(TextUtils.isEmpty(stud_email)||TextUtils.isEmpty(stud_password))
        {
            pd.dismiss();
            Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();
            return;
        }
        student.createUserWithEmailAndPassword(stud_email,stud_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    student.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Student s=new Student(stud_name,stud_email,stud_year,stud_section,stud_lab,stud_rno);
                                stud.getReference("student").child(stud_year).child(stud_section).child(stud_lab).child(stud_rno).setValue(s);
                                pd.dismiss();
                                Toast.makeText(StudentLogin.this, "Registered successfully please check your email for verification", Toast.LENGTH_SHORT).show();
                                new Intent(StudentLogin.this,LoginPage.class);
                            }
                            else
                            {
                                pd.dismiss();
                                Toast.makeText(StudentLogin.this,task.getException().getMessage(),Toast.LENGTH_SHORT);
                                return;
                            }

                        }
                    });

                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(StudentLogin.this,task.getException().getMessage(),Toast.LENGTH_SHORT);
                    return;
                }
            }
        });
    }

    public void Login(View v){startActivity(new Intent(this,LoginPage.class));}
}
