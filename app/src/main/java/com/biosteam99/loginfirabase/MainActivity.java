package com.biosteam99.loginfirabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView txtEmail;
    FloatingActionButton fabNewPost;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    Toolbar toolbarMain;
    DatabaseReference dataBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

        toolbarMain=findViewById(R.id.toolbarMain);

        setSupportActionBar(toolbarMain);

        fabNewPost=findViewById(R.id.fabNewPost);
        fabNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,AddNewPostActivity.class);
                startActivity(intent);
            }
        });
        Button button=findViewById(R.id.btnProfile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

/*
        dataBase= FirebaseDatabase.getInstance().getReference();

        dataBase.child("Users").child("uid1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name =snapshot.child("name").getValue().toString();
                txtEmail.setText(name);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        Posts posts=new Posts("Post Title","https://images.unsplash.com/photo-1533167649158-6d508895b680?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1032&q=80",
                "Posts Desc");

      dataBase.child("Posts").child("01").setValue(posts);


        dataBase.child("Posts").child("01").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String desc=snapshot.child("desc").getValue().toString();
                String title =snapshot.child("title").getValue().toString();

                txtPostDesc.setText(desc);
                txtPostTitle.setText(title);

                Glide.with(MainActivity.this).load(snapshot.child("image").getValue().toString())
                        .into(imgPost);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/



    }


    @Override
    protected void onStart() {
        super.onStart();

        if (firebaseUser==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.itemLogout){
            FirebaseAuth.getInstance().signOut();
            Intent  intent=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}