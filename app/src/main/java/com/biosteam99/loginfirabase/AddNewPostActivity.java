package com.biosteam99.loginfirabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNewPostActivity extends AppCompatActivity {

    EditText editTextTitle,editTextEdsc;
    Toolbar toolbarPost;
    Button btnNewPost;

    String title,desc;

    DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_post);

        btnNewPost=findViewById(R.id.btnNewPost);
        toolbarPost=findViewById(R.id.toolbarNewPost);
        editTextTitle=findViewById(R.id.edtTitle);
        editTextEdsc=findViewById(R.id.edtDesc);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();


        btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog=new ProgressDialog(AddNewPostActivity.this);
                progressDialog.setTitle("يتم نشر البوست");
                progressDialog.show();

                title=editTextTitle.getText().toString();
                desc=editTextEdsc.getText().toString();

                PostModel postModel=new PostModel(title,desc,"null",firebaseUser.getUid());

                databaseReference.child("Posts").child("02").setValue(postModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            editTextTitle.setText("");
                            editTextEdsc.setText("");
                        }
                    }
                });

            }
        });


    }
}