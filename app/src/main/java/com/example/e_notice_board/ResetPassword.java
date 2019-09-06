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
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    ProgressDialog pd;
    EditText email;
    FirebaseAuth reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        email=findViewById(R.id.email);
        reset=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
    }

    public void resetPassword(View v)
    {
        pd.setMessage("Loading");
        String get_email=email.getText().toString();
        pd.show();
        if(TextUtils.isEmpty(get_email))
        {
            pd.show();
            Toast.makeText(this, "Please enter email id", Toast.LENGTH_SHORT).show();
            return;
        }
        reset.sendPasswordResetEmail(get_email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    pd.dismiss();
                    Toast.makeText(ResetPassword.this,"Reset link sent to your email",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    pd.dismiss();
                    Toast.makeText(ResetPassword.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
