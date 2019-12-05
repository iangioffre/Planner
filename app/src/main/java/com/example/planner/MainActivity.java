package com.example.planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivityTag";

    EventOpenHelper openHelper;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listView = new ListView(this);
        setContentView(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MeetingActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String title = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
                alertBuilder.setTitle("Item Clicked")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openHelper.deleteEventByTitle(title);
                                sortListByDate();
                            }
                        })
                        .setNegativeButton("No", null);
                alertBuilder.show();

                return true;
            }
        });

        openHelper = new EventOpenHelper(this);

        Event event = new Event();
        event.setTitle("TITLE2");
        event.setDateTime("2019-12-22 12:00:00");
        openHelper.insertMeeting(event);

        Assignment assignment = new Assignment();
        assignment.setTitle("My Assignment");
        assignment.setCourse("None");
        assignment.setPriority(3);
        openHelper.insertAssignment(assignment);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sortListByDate();
    }

    private void sortListByDate() {
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

    private void sortListByClass() {
        Cursor cursor = openHelper.getSelectAllEventsByClassCursor();
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
                String course = cursor.getString(cursor.getColumnIndex(openHelper.COURSE));

                tv1.setText(title);
                tv2.setText(course);
            }

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                return cursorInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            }
        };

        listView.setAdapter(cursorAdapter);
    }

    private void sortListByImportance() {
        Cursor cursor = openHelper.getSelectAllEventsByImportanceCursor();
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
                String priority = cursor.getString(cursor.getColumnIndex(openHelper.PRIORITY));

                tv1.setText(title);
                tv2.setText(priority);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.addMeetingMenuItem:
                Intent meetingIntent = new Intent(MainActivity.this, MeetingActivity.class);
                startActivity(meetingIntent);
                return true;
            case R.id.addAssignmentMenuItem:
                Intent assignmentIntent = new Intent(MainActivity.this, AssignmentActivity.class);
                startActivity(assignmentIntent);
                return true;
            case R.id.addClassMenuItem:
                Intent intent2 = new Intent(MainActivity.this, AddClassActivity.class);
                startActivity(intent2);
                return true;
            case R.id.classListMenuItem:
                Intent intent3 = new Intent(MainActivity.this, ClassListActivity.class);
                startActivity(intent3);
                return true;
            case R.id.sortByClassMenuItem:
                sortListByClass();
                return true;
            case R.id.sortByDateMenuItem:
                sortListByDate();
                return true;
            case R.id.sortByImportanceMenuItem:
                sortListByImportance();
                return true;
            case R.id.showHighestPriorityMenuItem:
                Intent intent1 = new Intent(MainActivity.this, HighestPriorityActivity.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
