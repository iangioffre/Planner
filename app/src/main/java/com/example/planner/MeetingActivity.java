/**
 * Coded by: Kelsey Lally
 * Date: 12/4/19
 * Description:
 * Sources:
 * Date/ Time picker info - https://developer.android.com/guide/topics/ui/controls/pickers
 * Spinner - https://developer.android.com/guide/topics/ui/controls/spinner
 */

package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import static com.example.planner.EventOpenHelper.NOTES;
import static com.example.planner.EventOpenHelper.TITLE;

public class MeetingActivity extends AppCompatActivity {

    TimePicker timePicker;
    DatePicker datePicker;
    EditText titleEditText;
    Spinner prioritySpinner;
    Spinner courseSpinner;
    EditText descriptionEditText;
    long intentID;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        titleEditText = (EditText) findViewById(R.id.title_edit_text);
        descriptionEditText = (EditText) findViewById(R.id.description_edit_text);
        prioritySpinner = (Spinner) findViewById(R.id.priority_spinner);
        courseSpinner = (Spinner) findViewById(R.id.course_spinner);

        Spinner prioritySpinner = (Spinner) findViewById(R.id.priority_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        Spinner courseSpinner = (Spinner) findViewById(R.id.course_spinner);
        final EventOpenHelper eoh = new EventOpenHelper(this);
        Cursor cursor = eoh.getAllCourses();
        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_spinner_item,
                cursor,
                new String[] {eoh.NAME},
                new int[] {android.R.id.text1},
                0
        );
        cursorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(cursorAdapter);

        intent = getIntent();
        if (intent.getExtras() != null) {
            intentID = intent.getLongExtra("id", 0);
            EventOpenHelper openHelper = new EventOpenHelper(this);
            Cursor cursor1 = openHelper.getMeeting(intentID);
            cursor1.moveToNext();
            titleEditText.setText(cursor1.getString(cursor1.getColumnIndex(TITLE)));
            descriptionEditText.setText(cursor1.getString(cursor1.getColumnIndex(NOTES)));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.saveMenuItem:
                saveMeeting();
                finish();
                return true;
        }
        return true;
    }


    public void saveMeeting () {
        if (intent.getExtras() != null) {
            // update existing thing
        } else {
            EventOpenHelper eventOpenHelper = new EventOpenHelper(this);
            Event event = new Event();
            event.setTitle(titleEditText.getText().toString());
            Log.d("SAVE MEETING: ", "saveMeeting: " + datePicker.getYear() + "-" + datePicker.getMonth() + "-" +  datePicker.getDayOfMonth());
            event.setDateTime(datePicker.getYear() + "-" + datePicker.getMonth() + "-" +  datePicker.getDayOfMonth());
            event.setCourse(courseSpinner.getSelectedItem().toString());
            event.setPriority(Integer.parseInt(prioritySpinner.getSelectedItem().toString()));
            event.setNotes(descriptionEditText.getText().toString());
            eventOpenHelper.insertMeeting(event);
        }
    }

}
