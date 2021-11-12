package com.example.buildindcompany;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.DragAndDropPermissions;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class OrderActivity extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    SimpleCursorAdapter simpleCursorAdapter;
    Cursor serviceCursor, costCursor;
    LinearLayout order_layout, service_choose;
    ListView services_choose;
    TextView service_text, cost_text;
    EditText street_txt, house_txt, square_txt;
    Button count;
    String service, address, service_name, user_id, square, street, house, worker;
    String[] workers;
    Double price, cost;
    Double[] prices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        service_choose = (LinearLayout) findViewById(R.id.service_choose);
        order_layout = (LinearLayout) findViewById(R.id.order_layout);
        services_choose = (ListView) findViewById(R.id.services_choose_list);
        service_text = (TextView) findViewById(R.id.textView_service);
        cost_text = (TextView) findViewById(R.id.textView_cost);
        street_txt = (EditText) findViewById(R.id.editText_street);
        house_txt = (EditText) findViewById(R.id.editText_house);
        square_txt = (EditText) findViewById(R.id.editText_square);
        count = (Button) findViewById(R.id.button14);
        sqlHelper = new DatabaseHelper(this);
        sqlHelper.create_db();

        Intent intent = getIntent();
        user_id = intent.getStringExtra("user_id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        db = sqlHelper.open();
        serviceCursor = db.rawQuery(" select * from " + "'" + DatabaseHelper.TABLE_S + "'", null);
        String[] services = new String[]{DatabaseHelper.COLUMN_NAME_S};

        simpleCursorAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.services_choose, serviceCursor,
                services, new int[]{android.R.id.text1}, 0);
        services_choose.setAdapter(simpleCursorAdapter);

        services_choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                service = textView.getText().toString();

                service_choose.setVisibility(View.GONE);
                order_layout.setVisibility(View.VISIBLE);

                service_name = String.format("Услуга: %s", service);
                service_text.setText(service_name);

                costCursor = db.rawQuery(" select * from" + "'" + DatabaseHelper.TABLE_S + "'" + " where " +
                        DatabaseHelper.COLUMN_NAME_S + " = " + "'" + service + "'", null);
                int costColumnIndex = costCursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE_S);
                prices = new Double[costCursor.getCount()];

                if (costCursor.moveToFirst()) {
                    for (int i = 0; i < costCursor.getCount(); i++) {
                        prices[i] = costCursor.getDouble(costColumnIndex);
                        costCursor.moveToFirst();
                    }
                }
                price = prices[0];

                int workerColumnIndex = costCursor.getColumnIndex(DatabaseHelper.COLUMN_WORKER_S);
                workers = new String[costCursor.getCount()];

                if (costCursor.moveToFirst()) {
                    for (int i = 0; i < costCursor.getCount(); i++) {
                        workers[i] = costCursor.getString(workerColumnIndex);
                        costCursor.moveToFirst();
                    }
                }
                worker = workers[0];
            }
        });
    }

    public void Count(View view) {
        square = square_txt.getText().toString();
        if (square.isEmpty()) {
            Toast.makeText(this, "Укажите площадь", Toast.LENGTH_SHORT).show();
        } else {
            cost = Double.parseDouble(square) * price;
            cost_text.setText(String.valueOf(cost));
            count.setText("Продолжить заказ");
            count.setOnClickListener(this::CreateOrder);
        }
    }

    public void CreateOrder(View view) {
        street = street_txt.getText().toString();
        house = house_txt.getText().toString();
        if (street.isEmpty() || house.isEmpty()) {
            Toast.makeText(this, "Укажите адрес", Toast.LENGTH_SHORT).show();
        } else {
            address = street + ", " + String.valueOf(house);

            db.execSQL(" insert into " + "'" + DatabaseHelper.TABLE_O + "'" + " ( " + DatabaseHelper.COLUMN_USER_O +
                    " , " + DatabaseHelper.COLUMN_SERVICE_O + " , " + DatabaseHelper.COLUMN_COST_O +
                    " , " + DatabaseHelper.COLUMN_WORKER_O + " , " + DatabaseHelper.COLUMN_ADDRESS_O +
                    " ) " + " values " + " ( " + "'" + user_id + "'" + " , " + "'" + service + "'" + " , "
                    + "'" + cost + "'" + " , " + "'" + worker + "'" + " , " + "'" + address + "'" + " ) ");

            Toast.makeText(this, "Ваш заказ принят, наш менеджер с вами свяжется", Toast.LENGTH_SHORT).show();
        }
    }

    public void Return(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}