package com.example.e_notice_board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginTeacherpage extends AppCompatActivity {

    EditText email,password;
    FirebaseAuth verify;
    ProgressDialog pd;
    FirebaseUser fu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_teacherpage);

        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        verify=FirebaseAuth.getInstance();
        fu=verify.getCurrentUser();
        pd=new ProgressDialog(this);
    }

    public void LoginUser(View v)
    {
        String Email=email.getText().toString();
        String Password=password.getText().toString();
        pd.setMessage("Loading");
        pd.show();
        if(TextUtils.isEmpty(Email)||TextUtils.isEmpty(Password))
        {
            pd.dismiss();
            Toast.makeText(this,"Please enter both the fields",Toast.LENGTH_SHORT).show();
            return;
        }

        verify.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    if(verify.getCurrentUser().isEmailVerified())
                    {
                        pd.dismiss();
                        Toast.makeText(LoginTeacherpage.this,"Login successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginTeacherpage.this,TeacherMainActivity.class));

                    }
                    else
                    {
                        pd.dismiss();
                        Toast.makeText(LoginTeacherpage.this,"Please verify email first",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(LoginTeacherpage.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }

    public void forgotPassword(View v)
    {
        startActivity(new Intent(this,ResetPassword.class));

    }
}
