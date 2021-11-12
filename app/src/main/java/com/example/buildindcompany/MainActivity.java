package com.example.buildindcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public String activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        activity = intent.getStringExtra("intent");
    }

    public void Login(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void Reg(View view) {
        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
    }

    public void Services(View view){
        Intent intent = new Intent(this, ServicesActivity.class);
        intent.putExtra("intent", "main");
        startActivity(intent);
    }
}