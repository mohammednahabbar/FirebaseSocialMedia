package com.biosteam99.loginfirabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail,editTextPassword;
    AppCompatButton btnLogin;
    ProgressBar progressBar;
    TextView txtSignUp;

    String email,password;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth=FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.edtEmail);
        editTextPassword=findViewById(R.id.edtPassword);
        progressBar=findViewById(R.id.progress);


        btnLogin=findViewById(R.id.btnLogin);
        txtSignUp=findViewById(R.id.txtSignUp);


        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=editTextEmail.getText().toString();
                password=editTextPassword.getText().toString();

                loginFirebase(email,password);
            //    Log.v("LOGINACTIVITYTAG",email + " - " + password);


            }
        });


    }


    public void loginFirebase(String mEmail,String mPassword){
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(mEmail,mPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}