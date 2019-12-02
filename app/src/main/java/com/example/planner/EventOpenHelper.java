package com.example.sprint.sqlitefuns2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.planner.Assignment;
import com.example.planner.Event;

import java.util.ArrayList;

public class EventOpenHelper extends SQLiteOpenHelper {
    static final String TAG = "SQLiteFunTag";

    // fields

    private static final String DATABASE_NAME = "plannerDatabase";
    private static final int DATABASE_VERSION = 1;

    private static final String EVENTS_TABLE = "events";
    private static final String ASSIGNMENTS_TABLE = "assignments";
    private static final String COURSES_TABLE = "courses";

    private static final String ID = "_id"; // _id is for use with adapters later

    private static final String NAME = "name";
    private static final String ON_MONDAY = "on_monday";
    private static final String ON_TUESDAY = "on_tuesday";
    private static final String ON_WEDNESDAY = "on_wednesday";
    private static final String ON_THURDAY = "on_thursday";
    private static final String ON_FRIDAY = "on_friday";
    private static final String START_TIME = "start_time";
    private static final String STOP_TIME = "end_time";

    private static final String TITLE = "title";
    private static final String DUE_DATE = "due_date";
    private static final String DATE_TIME = "date_time";
    private static final String COURSE = "course";
    private static final String PRIORITY = "priority";
    private static final String IS_DONE = "id_done";
    private static final String NOTES = "notes";

    // constructors

    public EventOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // methods

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate = "CREATE TABLE " + COURSES_TABLE +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " "
        Log.d(TAG, "onCreate: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertEvent(Event event) {
        String sqlInsert = "";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close(); // good practice to close database open for writing
    }

    public void insertAssignment(Assignment assignment) {
        String sqlInsert = "";
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
