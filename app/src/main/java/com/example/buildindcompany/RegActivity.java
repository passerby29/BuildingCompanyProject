package com.example.buildindcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegActivity extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    EditText email_reg, pass_reg, conf_pass_reg, surname_reg, name_reg, patr_reg, pnumber_reg;
    String email, pass, conf_pass, surname, name, patr, pnumber;
    Cursor numberCursor, emailCursor;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        email_reg = (EditText) findViewById(R.id.email_reg);
        pass_reg = (EditText) findViewById(R.id.pass_reg);
        conf_pass_reg = (EditText) findViewById(R.id.conf_pass_reg);
        surname_reg = (EditText) findViewById(R.id.surname_reg);
        name_reg = (EditText) findViewById(R.id.name_reg);
        patr_reg = (EditText) findViewById(R.id.patr_reg);
        pnumber_reg = (EditText) findViewById(R.id.phone_reg);
        back = (TextView) findViewById(R.id.textView_back2);
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

    public void Registration(View view) {
        db = sqlHelper.open();

        email = email_reg.getText().toString();
        pass = pass_reg.getText().toString();
        conf_pass = conf_pass_reg.getText().toString();
        surname = surname_reg.getText().toString();
        name = name_reg.getText().toString();
        patr = patr_reg.getText().toString();
        pnumber = pnumber_reg.getText().toString();

        if (pass.equals(conf_pass)) {
            if (email.isEmpty() || pass.isEmpty() || name.isEmpty() || surname.isEmpty()
                    || patr.isEmpty() || pnumber.isEmpty()) {
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            } else {
                if (email.length() < 5 || pass.length() < 5) {
                    Toast.makeText(this, "Электронная почта и пароль должны быть не меньше 5 символов", Toast.LENGTH_SHORT).show();
                } else {
                    numberCursor = db.rawQuery(" select * from " + "'" + DatabaseHelper.TABLE_U + "'" +
                            " where " + DatabaseHelper.COLUMN_PNUMBER_U + " = " + "'" + pnumber + "'", null);

                    int numberCount = numberCursor.getCount();

                    emailCursor = db.rawQuery(" select * from" + "'" + DatabaseHelper.TABLE_U + "'" +
                            " where " + DatabaseHelper.COLUMN_EMAIL_U + " = " + "'" + email + "'", null);

                    int emailCount = emailCursor.getCount();

                    if (emailCount > 0) {
                        Toast.makeText(this, "Пользователь с такой электронной почтой уже существует", Toast.LENGTH_SHORT).show();
                    } else {
                        if (numberCount > 0) {
                            Toast.makeText(this, "Пользователь с таким номером телефона уже существует", Toast.LENGTH_SHORT).show();
                        } else {
                            db.execSQL(" insert into " + DatabaseHelper.TABLE_U + " ( " + DatabaseHelper.COLUMN_EMAIL_U +
                                    " , " + DatabaseHelper.COLUMN_PASS_U + " , " + DatabaseHelper.COLUMN_SUR_U +
                                    " , " + DatabaseHelper.COLUMN_NAME_U + " , " + DatabaseHelper.COLUMN_PATR_U +
                                    " , " + DatabaseHelper.COLUMN_PNUMBER_U + " ) " + " values " + " ( " +
                                    "'" + email + "'" + " , " + "'" + pass + "'" + " , " + "'" + surname + "'" +
                                    " , " + "'" + name + "'" + " , " + "'" + patr + "'" + " , " + "'" + pnumber + "'" + " ) ");

                            Toast.makeText(getApplicationContext(), "Вы успешно зарегестрировались", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
        }
    }
}