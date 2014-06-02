package com.example.taxisecurity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class Database_Handler extends SQLiteOpenHelper {

	


	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "taxiSecurity2";

    // Contacts table name
    private static final String TABLE_POLICE = "Police_Station_Details2";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_LAN = "langitude";
    private static final String KEY_LON = "longitude";
//    private Database_Handler ourHelper;
//    private final Context ourContext;

    public Database_Handler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_POLICE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," +KEY_LAN +" DOUBLE," + KEY_LON +" DOUBLE"+")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POLICE);

        // Create tables again
        onCreate(db);
    }
    
//    public Contact(Context c) {
//        ourContext = c;
//    }
//
//    public Contact open() throws SQLException {
//        ourHelper = new Database_Handler(ourContext);
//        db = ourHelper.getWritableDatabase();
//        return this;
//    }
//
//    public void close() {
//        ourHelper.close();
//    }
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEY_PH_NO, contact.getPhoneNumber()); // Contact Phone
        values.put(KEY_LAN, contact.getLan()); // Contact longitude
        values.put(KEY_LON, contact.getLon()); // Contact latitude
        
        // Inserting Row
        if(!checkIfExist(contact.getName())){
        db.insert(TABLE_POLICE, null, values);
        db.close(); // Closing database connection
        }
    }
//Checking data existance
    
    boolean checkIfExist(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POLICE, new String[] { KEY_ID,
                KEY_NAME, KEY_PH_NO,KEY_LAN,KEY_LON }, KEY_NAME + "=?",
                new String[] { name }, null, null, null, null);


        if(cursor.moveToFirst()){
            return true;
        }else{
            return false;
        }

    }
    
    // Getting single contact
    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_POLICE, new String[] { KEY_ID,
                KEY_NAME, KEY_PH_NO, KEY_LAN, KEY_LON }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getDouble(3), cursor.getDouble(4) );
        // return contact
        return contact;
    }

    // Getting All Contacts
    public ArrayList<HashMap<String, String>> getAllContact(){
    	SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<HashMap<String, String>> policeArrayList = new ArrayList<HashMap<String, String>>();
        //String [] columne = new String[] { KEY_ID, KEY_NAME, KEY_PH_NO,KEY_LAN,KEY_LON };
        Cursor cursor = db.query(TABLE_POLICE, new String[] {KEY_ID, KEY_NAME,
                KEY_PH_NO, KEY_LAN,KEY_LON}, null, null, null, null, KEY_NAME + " ASC");//KEY_NAME+"ASC");
        if(cursor.moveToFirst()){

            do{
                HashMap<String, String> policeMap = new HashMap<String, String>();
                policeMap.put("policeID", cursor.getString(0));
                policeMap.put("policeName", cursor.getString(1));
                policeMap.put("policePhone", cursor.getString(2));
                policeMap.put("policeLan", cursor.getString(3));
                policeMap.put("policeLon", cursor.getString(4));
                
                policeArrayList.add(policeMap);

            } while(cursor.moveToNext());
        }
        return policeArrayList;
    
    }

    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());
        values.put(KEY_LAN, contact.getLan());
        values.put(KEY_LON, contact.getLon());
        // updating row
        return db.update(TABLE_POLICE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }

    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_POLICE, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
        db.close();
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_POLICE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    
    public double getLatitudeFromId(String Name) {
        double rowLat =0.0000;
        System.out.println(Name);
      
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POLICE, new String[] { KEY_LAN },"name=" + "'"+Name + "'", null, null, null,null);
        if (cursor.moveToFirst()){
            rowLat = cursor.getDouble(cursor.getColumnIndex(KEY_LAN));
            //Log.i("database", "GetLatitude Called");
        }
        cursor.close();
        db.close();
       
        // return coordinates
        return rowLat;
    }  
    public double getLongitudeFromId(String Name) {
        double rowLon =0.0000;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_POLICE, new String[] { KEY_LON },"name=" + "'"+ Name + "'", null, null, null,null);
        if (cursor.moveToFirst()){
            rowLon = cursor.getDouble(cursor.getColumnIndex(KEY_LON));
        }
        cursor.close();
        db.close();

        // return coordinates
        return rowLon;
    }  

}


