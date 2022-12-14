package com.example.fallenangels.startup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.fallenangels.user_pages.MainActivity;
import com.example.fallenangels.R;
import com.google.firebase.auth.FirebaseAuth;

public class GetStarted extends AppCompatActivity {

    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);

        //Finding Id's
        btnStart = findViewById(R.id.btnStart);

        //by default, sign out user from firebase authentication
        FirebaseAuth auth;
        auth = FirebaseAuth.getInstance();
        auth.signOut();

        //Listeners
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GetStarted.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    //--------------------------------- Log In On Click method -------------------------------------
    public void ClickLogIn(View view) {
        Intent i = new Intent(GetStarted.this, Login.class);
        startActivity(i);
    }
    //----------------------------------------------------------------------------------------------
}