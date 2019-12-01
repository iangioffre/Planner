package com.example.sprint.sqlitefuns2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class EventOpenHelper extends SQLiteOpenHelper {
    static final String TAG = "SQLiteFunTag";

    // define some fields for our database
    static final String DATABASE_NAME = "contactsDatabase";
    static final int DATABASE_VERSION = 1;

    static final String TABLE_CONTACTS = "tableContacts";
    static final String ID = "_id"; // _id is for use with adapters later
    static final String NAME = "name";
    static final String PHONE_NUMBER = "phoneNumber";
    static final String IMAGE_RESOURCE = "imageResource";

    public ContactOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // where we create tables in our database
        // construct a SQL statement to create a table to store contacts
        // CREATE TABLE tableContacts(_id INTEGER PRIMARY KEY AUTOINCREMENT,
        // name TEXT,
        // phoneNumber TEXT,
        // imageResource INTEGER)

        // create a string that represents our SQL statement
        // structured query language
        String sqlCreate = "CREATE TABLE " + TABLE_CONTACTS +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT, " +
                PHONE_NUMBER + " TEXT, " +
                IMAGE_RESOURCE + " INTEGER)";
        Log.d(TAG, "onCreate: " + sqlCreate);
        // execute this sql statement
        sqLiteDatabase.execSQL(sqlCreate);
        // onCreate() only executes one time
        // and that is after the first call to getWritableDatabase()
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertContact(Contact contact) {
        // INSERT INTO tableContacts VALUES(null, 'Spike the Bulldog',
        // '509-509-5095', -1)
        String sqlInsert = "INSERT INTO " + TABLE_CONTACTS + " VALUES(null, '" +
                contact.getName() + "', '" +
                contact.getPhoneNumber() + "', " +
                contact.getImageResourceId() + ")";
        Log.d(TAG, "insertContact: " + sqlInsert);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close(); // good practice to close database open for writing
    }

    public Cursor getSelectAllContactsCursor() {
        // cursor used to navigate through results from a query
        // think of the cursor like a file cursor
        // SELECT * FROM tableContacts
        String sqlSelect = "SELECT * FROM " + TABLE_CONTACTS;
        // * means all columns
        Log.d(TAG, "getSelectAllContactsCursor: " + sqlSelect);
        SQLiteDatabase db = getReadableDatabase();
        // use db.rawQuery() because its returs a Cursor
        Cursor cursor = db.rawQuery(sqlSelect, null);
        // don't close the database, the cursor needs it open

        return cursor;
    }

    // for debug purposes only!!
    // for PA7 use SimpleCursorAdapter to wire up the database to the listview
    public List<Contact> getSelectAllContactsList() {
        List<Contact> contactList = new ArrayList<>();

        // goal: walk through each record using a cursor
        // create a Contact and add it to the list
        // the cursor doesn't start at the first record
        // because there might be not be a first record
        Cursor cursor = getSelectAllContactsCursor();
        while (cursor.moveToNext()) { // returns false when there is no next record
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phoneNumber = cursor.getString(2);
            int imageResource = cursor.getInt(3);
            Contact contact = new Contact(id, name, phoneNumber, imageResource);
            contactList.add(contact);
        }

        return contactList;
    }

    public void updateContactById(int id, Contact newContact) {
        // UPDATE tableContacts SET name='SPIKE', phoneNumber='208-208-2082' WHERE _id=1
        String sqlUpdate = "UPDATE " + TABLE_CONTACTS + " SET " +
                NAME + "='" + newContact.getName() + "', " +
                PHONE_NUMBER  + "='" + newContact.getPhoneNumber() + "' WHERE " +
                ID + "=" + id;
        Log.d(TAG, "updateContactById: " + sqlUpdate);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    public void deleteAllContacts() {
        // DELETE FROM tableContacts
        String sqlDelete = "DELETE FROM " + TABLE_CONTACTS;
        Log.d(TAG, "deleteAllContacts: " + sqlDelete);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }

}
