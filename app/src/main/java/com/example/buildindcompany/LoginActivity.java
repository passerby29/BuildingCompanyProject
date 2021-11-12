package com.example.buildindcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor idCursor;
    EditText email_box, pass_box;
    String email, pass;
    int[] user_id;
    String user;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email_box = (EditText) findViewById(R.id.email_log);
        pass_box = (EditText) findViewById(R.id.pass_log);
        back = (TextView) findViewById(R.id.textView_back);
        sqlHelper = new DatabaseHelper(this);
        sqlHelper.create_db();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = sqlHelper.open();
    }

    public void login(View view) {
        email = email_box.getText().toString();
        pass = pass_box.getText().toString();

        if (!email.isEmpty() || !pass.isEmpty()) {
            if (email.length() >= 5 || pass.length() >= 5) {
                idCursor = db.rawQuery(" select * from " + "'" + DatabaseHelper.TABLE_U + "'" +
                        " where " + DatabaseHelper.COLUMN_EMAIL_U + " = " + "'" + email + "'" +
                        " and " + DatabaseHelper.COLUMN_PASS_U + " = " + "'" + pass + "'", null);

                int columnIndexId = idCursor.getColumnIndex(DatabaseHelper.COLUMN_ID_U);
                user_id = new int[idCursor.getCount()];

                if (idCursor.moveToFirst()) {
                    for (int i = 0; i < idCursor.getCount(); i++) {
                        user_id[i] = idCursor.getInt(columnIndexId);
                        idCursor.moveToNext();
                    }
                }

                int count = idCursor.getCount();

                if (count > 0) {
                    Intent intent = new Intent(this, MenuActivity.class);
                    intent.putExtra("user_id", String.valueOf(user_id[0]));
                    startActivity(intent);
                } else {
                    if (count == 0) {
                        Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(this, "Электронная почта и пароль должны быть не меньше 5 символов", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show();
        }
    }
}

