package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = new ListView(this);
        setContentView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // open the edit activity
//                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                intent.putExtra("id", id);
//                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // allow a deletion

                return true;
            }
        });

        final EventOpenHelper openHelper = new EventOpenHelper(this);
        Event event = new Event();
        event.setTitle("TITLE2");
        event.setDateTime("2019-12-22 12:00:00");
        openHelper.insertMeeting(event);

        Cursor cursor = openHelper.getSelectAllEventsByDateCursor();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {EventOpenHelper.TITLE},
                new int[] {android.R.id.text1},
                0
        ) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView tv1 = view.findViewById(android.R.id.text1);
                TextView tv2 = view.findViewById(android.R.id.text2);

                String title = cursor.getString(cursor.getColumnIndex(openHelper.TITLE));
                String date = cursor.getString(cursor.getColumnIndex(openHelper.DATE_TIME));

                tv1.setText(title);
                tv2.setText(date);
            }

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                return cursorInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            }
        };

        listView.setAdapter(cursorAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // update the listview with the db
    }
}
