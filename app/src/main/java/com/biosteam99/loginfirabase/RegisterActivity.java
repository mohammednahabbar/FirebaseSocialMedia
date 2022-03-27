package com.biosteam99.loginfirabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText editTextName,editTextEmail,editTextPassword,editTextConfirmPassword;

    String name,email,password,passwordConfirm;

    FirebaseAuth firebaseAuth;
    DatabaseReference usersDatabase;

    Boolean isName,isEmail=false,isPassword=false,isPasswordConfirm=false;
    UsersModel usersModel;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister=findViewById(R.id.btnRegister);
        editTextName=findViewById(R.id.edtName);
        editTextEmail=findViewById(R.id.edtEmail);
        editTextPassword=findViewById(R.id.edtPassword);

        editTextConfirmPassword=findViewById(R.id.edtPasswordConfirm);


        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("") || charSequence.toString()==null|| charSequence.toString().length()==0){
                    isName=false;
                    Log.v("REGISTERACTIVITYTAG", "Is Name :" + isName);
                    editTextName.setError("The Name is Required");
                }else {
                    isName=true;
                    editTextName.setError(null);
                    Log.v("REGISTERACTIVITYTAG", "Is Name :" + isName);
                    name=charSequence.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("") || charSequence.toString()==null|| charSequence.toString().length()==0){
                    isEmail=false;
                    Log.v("REGISTERACTIVITYTAG", "Is Email :" + isEmail);
                    editTextEmail.setError("The Email is Required");
                }else {
                    isEmail=true;
                    editTextEmail.setError(null);
                    Log.v("REGISTERACTIVITYTAG", "Is Email :" + isEmail);
                    email=charSequence.toString();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("") || charSequence.toString()==null|| charSequence.toString().length()==0){
                    isPassword=false;
                    Log.v("REGISTERACTIVITYTAG", "Is Password :" + isPassword);
                    editTextPassword.setError("The Password is Required");
                }else {
                    isPassword=true;
                    editTextPassword.setError(null);
                    Log.v("REGISTERACTIVITYTAG", "Is Password :" + isPassword);
                    password=charSequence.toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("") || charSequence.toString()==null|| charSequence.toString().length()==0){
                    isPasswordConfirm=false;
                    Log.v("REGISTERACTIVITYTAG", "Is PasswordConfirm :" + isPasswordConfirm);
                    editTextConfirmPassword.setError("The PasswordConfirm is Required");
                }else {
                    passwordConfirm=charSequence.toString();
                    if (password.equals(passwordConfirm)){
                        isPasswordConfirm=true;
                        editTextConfirmPassword.setError(null);
                        Log.v("REGISTERACTIVITYTAG", "Is PasswordConfirm :" + isPasswordConfirm);
                    }else {
                        isPasswordConfirm=false;
                        editTextConfirmPassword.setError("The Password is Not Equal");
                        Log.v("REGISTERACTIVITYTAG", "Is PasswordConfirm :" + isPasswordConfirm);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        firebaseAuth=FirebaseAuth.getInstance();

        usersDatabase= FirebaseDatabase.getInstance().getReference();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if (isEmail && isPassword && isPasswordConfirm){
                registerUser(email,password);
            }

            }
        });

    }

    public void registerUser(String userEmail,String userPassword){
        progressDialog=new ProgressDialog(RegisterActivity.this);
        progressDialog.setTitle("يتم الآن إنشاء حساب جديد");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                progressDialog.dismiss();

                String uid=firebaseAuth.getCurrentUser().getUid().toString();


                usersModel=new UsersModel(name,"https://images.pexels.com/photos/220453/pexels-photo-220453.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"
                ,"Hi , I am Using WE SHARE");

                usersDatabase.child("Users").child(uid).setValue(usersModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.v("REGISTERACTIVITYTAG", "Successful");
                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


            }else {
                progressDialog.dismiss();
                Log.v("REGISTERACTIVITYTAG", "createUserWithEmail:failure", task.getException());
                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
            }
        });
    }


}