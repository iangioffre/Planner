package com.example.planner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class ClassListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final EventOpenHelper openHelper = new EventOpenHelper(this);

        ListView listView = new ListView(this);
        setContentView(listView);
        Cursor cursor = openHelper.getAllCourses();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2,
                cursor,
                new String[] {EventOpenHelper.NAME},
                new int[] {android.R.id.text1},
                0
        ) {
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView tv1 = view.findViewById(android.R.id.text1);
                TextView tv2 = view.findViewById(android.R.id.text2);

                String name = cursor.getString(cursor.getColumnIndex(openHelper.NAME));
                int monday = cursor.getInt(cursor.getColumnIndex(openHelper.ON_MONDAY));
                int tuesday = cursor.getInt(cursor.getColumnIndex(openHelper.ON_TUESDAY));
                int wednesday = cursor.getInt(cursor.getColumnIndex(openHelper.ON_WEDNESDAY));
                int thursday = cursor.getInt(cursor.getColumnIndex(openHelper.ON_THURSDAY));
                int friday = cursor.getInt(cursor.getColumnIndex(openHelper.ON_FRIDAY));
                String startTime = cursor.getString(cursor.getColumnIndex(openHelper.START_TIME));
                StringBuilder modTime = new StringBuilder(startTime);
                modTime.delete(0, 11);
                modTime.delete(5, modTime.length());

                if(friday==1 || thursday==1 || wednesday==1 || tuesday==1 || monday==1){
                    modTime.insert(0, " ");
                }
                if (friday==1){
                    modTime.insert(0,"F");
                }
                if (thursday==1){
                    modTime.insert(0,"Tr");
                }
                if (wednesday==1){
                    modTime.insert(0, "W");
                }
                if (tuesday==1){
                    modTime.insert(0,"Tu");
                }
                if (monday==1){
                    modTime.insert(0,"M");
                }

                tv1.setText(name);
                tv2.setText(modTime);
            }

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                LayoutInflater cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                return cursorInflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            }
        };

        listView.setAdapter(cursorAdapter);


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Course course = new Course();
                final String name = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                course.setName(name);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ClassListActivity.this);
                alertBuilder.setTitle("Item Clicked")
                        .setMessage("Are you sure you want to delete this class?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openHelper.deleteCourse(course);
                            }
                        })
                        .setNegativeButton("No", null);
                alertBuilder.show();

                return true;
            }
        });
    }
}
