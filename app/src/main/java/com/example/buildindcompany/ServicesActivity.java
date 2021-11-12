package com.example.buildindcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ServicesActivity extends AppCompatActivity {

    ListView service_list;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor servicesCursor;
    SimpleCursorAdapter servicesAdapter;
    public String user_id, activity;
    TextView backToMain;
    Button backToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        service_list = (ListView) findViewById(R.id.services_list);
        backToMain = (TextView) findViewById(R.id.textView_back3);
        backToMenu = (Button) findViewById(R.id.button6);
        sqlHelper = new DatabaseHelper(this);
        sqlHelper.create_db();

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
        activity = intent.getStringExtra("intent");

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        if (activity.equals("main")){
            backToMain.setVisibility(View.VISIBLE);
        } else {
            backToMenu.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = sqlHelper.open();
        servicesCursor = db.rawQuery(" select * from " + "'" + DatabaseHelper.TABLE_S + "'", null);
        String[] services = new String[]{DatabaseHelper.COLUMN_NAME_S, DatabaseHelper.COLUMN_PRICE_S};

        servicesAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.services, servicesCursor,
                services, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        service_list.setAdapter(servicesAdapter);
    }

    public void Return(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }
}