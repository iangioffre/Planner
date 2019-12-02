package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = new ListView(this);
        setContentView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open the edit activity
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // allow a deletion

                return true;
            }
        });

        EventOpenHelper openHelper = new EventOpenHelper(this);
        Event event = new Event();
        event.setTitle("TITLE");
        openHelper.insertMeeting(event);

        Cursor cursor = openHelper.getSelectAllEventsByDateCursor();
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
