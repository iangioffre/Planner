package com.example.planner;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EventOpenHelper extends SQLiteOpenHelper {
    static final String TAG = "EventOpenHelperTag";

    // fields

    static final String DATABASE_NAME = "plannerDatabase";
    static final int DATABASE_VERSION = 1;

    static final String MEETINGS_TABLE = "meetings";
    static final String ASSIGNMENTS_TABLE = "assignments";
    static final String COURSES_TABLE = "courses";

    static final String ID = "_id"; // _id is for use with adapters later

    static final String NAME = "name";
    static final String ON_MONDAY = "on_monday";
    static final String ON_TUESDAY = "on_tuesday";
    static final String ON_WEDNESDAY = "on_wednesday";
    static final String ON_THURSDAY = "on_thursday";
    static final String ON_FRIDAY = "on_friday";
    static final String START_TIME = "start_time";
    static final String END_TIME = "end_time";

    static final String TITLE = "title";
    static final String DATE_TIME = "date_time";
    static final String COURSE = "course";
    static final String PRIORITY = "priority";
    static final String IS_DONE = "is_done";
    static final String NOTES = "notes";

    // constructors

    public EventOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // methods

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate = "CREATE TABLE " + COURSES_TABLE +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " VARCHAR(20), " +
                ON_MONDAY + " BOOLEAN, " +
                ON_TUESDAY + " BOOLEAN, " +
                ON_WEDNESDAY + " BOOLEAN, " +
                ON_THURSDAY + " BOOLEAN, " +
                ON_FRIDAY + " BOOLEAN, " +
                START_TIME + " DATETIME, " +
                END_TIME + " DATETIME)";
        Log.d(TAG, "onCreate: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);

        sqlCreate = "CREATE TABLE " + ASSIGNMENTS_TABLE +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " VARCHAR(100), " +
                DATE_TIME + " DATETIME, " +
                COURSE + " VARCHAR(50), " +
                PRIORITY + " INT UNSIGNED, " +
                IS_DONE + " BOOLEAN, " +
                NOTES + " VARCHAR(255), " +
                "FOREIGN KEY (" + COURSE + ") REFERENCES " + COURSES_TABLE + " (" + NAME + "))";
        Log.d(TAG, "onCreate: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);

        sqlCreate = "CREATE TABLE " + MEETINGS_TABLE +
                "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TITLE + " VARCHAR(50), " +
                DATE_TIME + " DATETIME, " +
                COURSE + " VARCHAR(50), " +
                PRIORITY + " INT UNSIGNED, " +
                NOTES + " VARCHAR(255), " +
                "FOREIGN KEY (" + COURSE + ") REFERENCES " + COURSES_TABLE + " (" + NAME + "))";
        Log.d(TAG, "onCreate: " + sqlCreate);
        sqLiteDatabase.execSQL(sqlCreate);

        Course course = new Course(0, "Classes", 0, 0, 0, 0, 0, "0000-00-00 00:00:00", "0000-00-00 00:00:00");

        String sqlInsert = "INSERT INTO " + COURSES_TABLE + " VALUES(null, '" +
                course.getName() + "', " +
                course.getOnMonday() + ", " +
                course.getOnTuesday() + ", " +
                course.getOnWednesday() + ", " +
                course.getOnThursday() + ", " +
                course.getOnFriday() + ", '" +
                course.getStartTime() + "', '" +
                course.getEndTime() + "')";
        Log.d(TAG, "insertCourse: " + sqlInsert);

        sqLiteDatabase.execSQL(sqlInsert);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertCourse(Course course) {
        String sqlInsert = "INSERT INTO " + COURSES_TABLE + " VALUES(null, '" +
                course.getName() + "', " +
                course.getOnMonday() + ", " +
                course.getOnTuesday() + ", " +
                course.getOnWednesday() + ", " +
                course.getOnThursday() + ", " +
                course.getOnFriday() + ", '" +
                course.getStartTime() + "', '" +
                course.getEndTime() + "')";
        Log.d(TAG, "insertCourse: " + sqlInsert);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close();
    }

    public void insertMeeting(Event event) {
        String sqlInsert = "INSERT INTO " + MEETINGS_TABLE + " VALUES(null, '" +
                event.getTitle() + "', '" +
                event.getDateTime() + "', '" +
                event.getCourse() + "', " +
                event.getPriority() + ", '" +
                event.getNotes() + "')";
        Log.d(TAG, "insertMeeting: " + sqlInsert);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close();
    }

    public void insertAssignment(Assignment assignment) {
        String sqlInsert = "INSERT INTO " + ASSIGNMENTS_TABLE + " VALUES(null, '" +
                assignment.getTitle() + "', '" +
                assignment.getDateTime() + "', '" +
                assignment.getCourse() + "', " +
                assignment.getPriority() + ", " +
                assignment.getIsDone() + ", '" +
                assignment.getNotes() + "')";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlInsert);
        db.close();
    }

    public void deleteCourse(Course course) {
        String sqlDelete = "DELETE FROM " + COURSES_TABLE + " WHERE " + NAME + " = '" + course.getName() + "'";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }

    public void deleteEventByTitle(String title) {
        String sqlDelete = "DELETE FROM " + MEETINGS_TABLE + " WHERE " + TITLE + " = '" + title + "'";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();

        sqlDelete = "DELETE FROM " + ASSIGNMENTS_TABLE + " WHERE " + TITLE + " = '" + title + "'";
        db = getWritableDatabase();
        db.execSQL(sqlDelete);
        db.close();
    }

    public Cursor getSelectAllEventsByDateCursor() {
        String sqlSelect = "SELECT * FROM " + MEETINGS_TABLE + " UNION SELECT " +
                ID + ", " + TITLE + ", " + DATE_TIME + ", " + COURSE + ", " + PRIORITY + ", " + NOTES + " FROM " + ASSIGNMENTS_TABLE +
                " WHERE strftime('%Y-%m-%d %H-%M-%S','now') ORDER BY " + DATE_TIME;
        Log.d(TAG, "getSelectAllEventsCursor: " + sqlSelect);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        return cursor;
    }

    public Cursor getSelectAllEventsByClassCursor() {
        String sqlSelect = "SELECT * FROM " + MEETINGS_TABLE + " UNION SELECT " +
                ID + ", " + TITLE + ", " + DATE_TIME + ", " + COURSE + ", " + PRIORITY + ", " + NOTES + " FROM " + ASSIGNMENTS_TABLE +
                " WHERE strftime('%Y-%m-%d %H-%M-%S','now') ORDER BY " + COURSE;
        Log.d(TAG, "getSelectAllEventsCursor: " + sqlSelect);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        return cursor;
    }

    public Cursor getSelectAllEventsByImportanceCursor() {
        String sqlSelect = "SELECT * FROM " + MEETINGS_TABLE + " UNION SELECT " +
                ID + ", " + TITLE + ", " + DATE_TIME + ", " + COURSE + ", " + PRIORITY + ", " + NOTES + " FROM " + ASSIGNMENTS_TABLE +
                " WHERE strftime('%Y-%m-%d %H-%M-%S','now') ORDER BY " + PRIORITY;
        Log.d(TAG, "getSelectAllEventsCursor: " + sqlSelect);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect, null);

        return cursor;
    }

    public Cursor getAllCourses() {
        String sqlSelect = "SELECT " + ID + ", " + NAME + ", " + ON_MONDAY + ", " + ON_TUESDAY + ", " + ON_WEDNESDAY + ", " + ON_THURSDAY + ", " + ON_FRIDAY + ", " + START_TIME + " FROM " + COURSES_TABLE;
        Log.d(TAG, "getAllCourses: " + sqlSelect);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect,null);

        return cursor;
    }

    public Cursor getSelectHighestPriorityAssignmentCursor() {
        String sqlSelect = "SELECT a1." + TITLE + ", a1." + COURSE + ", a1." + PRIORITY + ", c." + START_TIME +
                " FROM " + COURSES_TABLE + " c JOIN " + ASSIGNMENTS_TABLE + " a1 ON (c." + NAME + " = a1." + COURSE + ")" +
                " GROUP BY a1." + PRIORITY + " HAVING a1." + PRIORITY +
                " = (SELECT MIN(a2." + PRIORITY + ") FROM " + ASSIGNMENTS_TABLE + " a2" +
                " WHERE NOT a2." + IS_DONE + ")";
        Log.d(TAG, "getSelectHighestPriorityAssignmentCursor: " + sqlSelect);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect,null);

        return cursor;
    }

    public boolean isMeeting (String title) {
        String sqlSelect = "SELECT * FROM " + MEETINGS_TABLE + " WHERE " + TITLE + " = '" + title + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect,null);
        if (cursor.getCount() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getMeeting(long id) {
        String sqlSelect = "SELECT * FROM " + MEETINGS_TABLE + " WHERE " + ID + " = " + id;
        Log.d(TAG, "getMeeting: " + sqlSelect);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect,null);

        return cursor;
    }

    public void updateMeeting(Event event) {
        String sqlUpdate = "UPDATE " + MEETINGS_TABLE + " SET " +
                TITLE + " = '" + event.getTitle() + "', " +
                DATE_TIME + " = '" + event.getDateTime() + "', " +
                COURSE + " = '" + event.getCourse() + "', " +
                PRIORITY + " = '" + event.getPriority() + "', " +
                NOTES + " = '" + event.getNotes() + "' WHERE " +
                ID + " = '" + event.getId() + "'" ;
        Log.d(TAG, "updateMeeting: " + sqlUpdate);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    public Cursor getAssignment(long id) {
        String sqlSelect = "SELECT * FROM " + ASSIGNMENTS_TABLE + " WHERE " + ID + " = " + id;
        Log.d(TAG, "getAssignment: " + sqlSelect);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect,null);

        return cursor;
    }

    public void updateAssignment(Assignment assignment) {
        String sqlUpdate = "UPDATE " + ASSIGNMENTS_TABLE + " SET " +
                TITLE + " = '" + assignment.getTitle() + "', " +
                DATE_TIME + " = '" + assignment.getDateTime() + "', " +
                COURSE + " = '" + assignment.getCourse() + "', " +
                PRIORITY + " = '" + assignment.getPriority() + "', " +
                IS_DONE + " = '" + assignment.getIsDone() + "', " +
                NOTES + " = '" + assignment.getNotes() + "' WHERE " +
                ID + " = '" + assignment.getId() + "'" ;
        Log.d(TAG, "updateAssignment: " + sqlUpdate);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sqlUpdate);
        db.close();
    }

    public ArrayList<String> getCourseNameList() {
        String sqlSelect = "SELECT * FROM " + COURSES_TABLE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sqlSelect,null);

        ArrayList<String> names = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                names.add(cursor.getString(cursor.getColumnIndex(NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return names;
    }
}