package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = new ListView(this);
        setContentView(listView);

        EventOpenHelper openHelper = new EventOpenHelper(this);
        Event event = new Event();
        event.setTitle("TITLE");
        openHelper.insertMeeting(event);

        Cursor cursor = openHelper.getSelectAllEventsCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                cursor,
                new String[] {EventOpenHelper.TITLE},
                new int[] {android.R.id.text1},
                0
        );

        listView.setAdapter(cursorAdapter);
    }
}
