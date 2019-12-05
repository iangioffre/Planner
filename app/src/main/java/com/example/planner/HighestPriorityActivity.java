package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class HighestPriorityActivity extends AppCompatActivity {
    static final String TAG = "HighestPriorityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highest_priority);

        final EventOpenHelper openHelper = new EventOpenHelper(this);

        Cursor cursor = openHelper.getSelectHighestPriorityAssignmentCursor();
        Log.d(TAG, "onCreate: " + cursor.getCount());
        cursor.moveToNext();
        String title = cursor.getString(cursor.getColumnIndex(openHelper.TITLE));
        String course = cursor.getString(cursor.getColumnIndex(openHelper.COURSE));

        TextView titleView = findViewById(R.id.title);
        TextView courseView = findViewById(R.id.course);

        titleView.setText("Assignment: " + title);
        courseView.setText("Course: " + course);
    }
}
