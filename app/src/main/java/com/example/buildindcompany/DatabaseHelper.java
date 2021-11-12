package com.example.buildindcompany;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH;
    private static String DB_NAME = "BC2.db";
    private static final int SCHEMA = 1;
    static final String TABLE_O = "orders";
    static final String TABLE_S = "services";
    static final String TABLE_U = "users";

    //table users
    static String COLUMN_ID_U = "_id";
    static String COLUMN_EMAIL_U = "email";
    static String COLUMN_PASS_U = "password";
    static String COLUMN_SUR_U = "surname";
    static String COLUMN_NAME_U = "name";
    static String COLUMN_PATR_U = "patrontmic";
    static String COLUMN_PNUMBER_U = "pnumber";

    //table orders
    static String COLUMN_ID_O = "_id";
    static String COLUMN_USER_O = "user_id";
    static String COLUMN_SERVICE_O = "service_name";
    static String COLUMN_COST_O = "cost";
    static String COLUMN_WORKER_O = "worker";
    static String COLUMN_ADDRESS_O = "adress";

    //table services
    static String COLUMN_ID_S = "_id";
    static String COLUMN_NAME_S = "name";
    static String COLUMN_PRICE_S = "price";
    static String COLUMN_WORKER_S = "worker";

    private Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext = context;
        DB_PATH = context.getFilesDir().getPath() + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    void create_db() {
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH);
            if (!file.exists()) {
                myInput = myContext.getAssets().open(DB_NAME);

                String outFileName = DB_PATH;

                myOutput = new FileOutputStream(outFileName);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
            }
        } catch (Exception ex) {
            Log.d("DatabaseHelper", ex.getMessage());
        } finally {
            try {
                if (myOutput != null) myOutput.close();
                if (myInput != null) myInput.close();
            } catch (Exception ex) {
                Log.d("DatabaseHelper", ex.getMessage());
            }
        }
    }

    public SQLiteDatabase open() throws SQLException {
        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }
}
