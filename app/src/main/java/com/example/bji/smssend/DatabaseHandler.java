package com.example.bji.smssend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Detector";

    // Contacts table name
    private static final String Table_VALEUR = "Valeur";

    // Contacts Table Columns names
    private static final String KEY_X = "valx";
    private static final String KEY_Y = "valy";
    private static final String KEY_Z = "valz";
    public static final String COLUMN_TIME_STAMP = "timeStamp";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_VALEUR = "CREATE TABLE " + Table_VALEUR + "("
                + KEY_X + " TEXT," + KEY_Y + " TEXT,"
                + KEY_Z + " TEXT"// + COLUMN_TIME_STAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_TABLE_VALEUR);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Table_VALEUR);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addContact(AccesValeur accesValeur) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

            values.put(KEY_X, accesValeur.getX()); // Contact Name
            values.put(KEY_Y, accesValeur.getY()); // Contact Phone
            values.put(KEY_Z, accesValeur.getZ()); // Contact Name
//            values.put(COLUMN_TIME_STAMP, " time('now') ");

            // Inserting Row
            db.insert(Table_VALEUR, null, values);


        db.close(); // Closing database connection

    }
    // Getting single contact


    // Getting All Contacts
    public ArrayList< AccesValeur>  getAllValeur() {
        ArrayList<AccesValeur> valeurList = new ArrayList<AccesValeur>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table_VALEUR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AccesValeur valeur = new AccesValeur();
                valeur.setX(cursor.getString(0));
                valeur.setY(cursor.getString(1));
                valeur.setZ(cursor.getString(2));
                // Adding contact to list
                valeurList.add(valeur);
            } while (cursor.moveToNext());
        }

        // return contact list
        return valeurList;
    // Updating single contact
}}