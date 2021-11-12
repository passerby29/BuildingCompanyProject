package com.example.buildindcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MenuActivity extends AppCompatActivity {

    String user_id;
    ImageView services, order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        services = (ImageView) findViewById(R.id.imageView_services);
        order = (ImageView) findViewById(R.id.imageView_order);

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        services.setOnClickListener(this::Services);
        order.setOnClickListener(this::Order);
    }

    public void Services (View view){
        Intent intent = new Intent(this, ServicesActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("intent", "menu");
        startActivity(intent);
    }

    public void Order (View view){
        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }
}