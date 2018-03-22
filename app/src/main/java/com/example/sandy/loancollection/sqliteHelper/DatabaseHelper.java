package com.example.sandy.loancollection.sqliteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sandy on 12/2/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database version
    private static final int DB_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DBContract.DB_NAME, null, DB_VERSION);
    }

    // Create table
    public static final String CREATE_TABLE ="create table " +
            DBContract.TABLE_NAME+ "(" +
            DBContract.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,  "+
            DBContract.COLUMN_NAME+ " text, "+
            DBContract.COLUMN_ACC_ID+ " text, "+
            DBContract.COLUMN_LOAN+ " text, "+
            DBContract.COLUMN_INSTALLMENT+ " text, "+
            DBContract.COLUMN_REMAINING+ " text, "+
            DBContract.COLUMN_ADDRESS+ " text);";

    // Drop table if exits
    private static final String DROP_TABLE= "drop table if exits "+DBContract.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DROP_TABLE);
        onCreate(db);

    }

    public void saveInformation(JSONObject jsonObject,SQLiteDatabase db) {
        //SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        try
        {
            //contentValues.put(DBContract.COLUMN_ID,jsonObject.getString("customer_name"));
            contentValues.put(DBContract.COLUMN_NAME,jsonObject.getString("customer_name"));
            contentValues.put(DBContract.COLUMN_ACC_ID,jsonObject.getString("account"));
            contentValues.put(DBContract.COLUMN_ADDRESS,jsonObject.getString("collection_address"));
            contentValues.put(DBContract.COLUMN_INSTALLMENT,jsonObject.getString("final_am"));
            contentValues.put(DBContract.COLUMN_REMAINING,jsonObject.getString("remaining_amount"));
            contentValues.put(DBContract.COLUMN_LOAN,jsonObject.getString("amount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
