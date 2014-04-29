package com.example.taxisecurity;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "taxi_security";
 
    // Contacts table name
    private static final String TABLE_POLICE = "police_station";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_NAME = "Police_Station_Name";
    private static final String KEY_PH_NO = "Phone_Number";

	public DatabaseHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String CREATE_POLICE_TABLE = "CREATE TABLE " + TABLE_POLICE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_POLICE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POLICE);
 
        // Create tables again
        onCreate(db);
	}
	
	 /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new contact
    void addPolice(Police police) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, police.getName()); // Contact Name
        values.put(KEY_PH_NO, police.getPhoneNumber()); // Contact Phone
 
        // Inserting Row
        db.insert(TABLE_POLICE, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single contact
    Police getPolice(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_POLICE, new String[] { KEY_ID,
                KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        Police police = new Police(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return police;
    }
    
 // Getting All Contacts
    public List<Police> getAllPolice() {
        List<Police> policeList = new ArrayList<Police>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_POLICE;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Police police = new Police();
                police.setID(Integer.parseInt(cursor.getString(0)));
                police.setName(cursor.getString(1));
                police.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                policeList.add(police);
            } while (cursor.moveToNext());
        }
 
        // return contact list
        return policeList;
    }
}
