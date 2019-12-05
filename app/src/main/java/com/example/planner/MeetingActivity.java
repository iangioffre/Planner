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
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import static com.example.planner.EventOpenHelper.COURSE;
import static com.example.planner.EventOpenHelper.DATE_TIME;
import static com.example.planner.EventOpenHelper.NOTES;
import static com.example.planner.EventOpenHelper.PRIORITY;
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
            prioritySpinner.setSelection(adapter.getPosition(cursor1.getString(cursor1.getColumnIndex(PRIORITY))));
            String string_date = cursor1.getString(cursor1.getColumnIndex(DATE_TIME));
            String[]divide = string_date.split("-");
            String[]divide2 = divide[2].split(" ");
            String[]divide3 = divide2[1].split(":");
            int year = Integer.parseInt(divide[0]);
            int month = Integer.parseInt(divide[1]) - 1;
            int day = Integer.parseInt(divide2[0]);
            datePicker.updateDate(year, month, day);
            timePicker.setCurrentHour(Integer.parseInt(divide3[0]));
            timePicker.setCurrentMinute(Integer.parseInt(divide3[1]));
        }
    }

    public int setCourseSpinnerSelection(String st, CursorAdapter ca) {
        int  index = 0;
        for (int i = 1; i < ca.getCount() - 2; i++) {
            if (ca.getItem(i) == st) {
                index = i;
            }
        }
        return index;
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
            EventOpenHelper eventOpenHelper = new EventOpenHelper(this);

            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            Event event = new Event();
            event.setId((int)intentID);
            event.setTitle(titleEditText.getText().toString());
            event.setDateTime(datePicker.getYear() + "-" + (Integer) (datePicker.getMonth() + 1) + "-" +  datePicker.getDayOfMonth() + " " + String.format("%02d", hour) + ":" + String.format("%02d", minute));
            event.setCourse(courseSpinner.getSelectedItem().toString());
            event.setPriority(Integer.parseInt(prioritySpinner.getSelectedItem().toString()));
            event.setNotes(descriptionEditText.getText().toString());
            eventOpenHelper.updateMeeting(event);
        } else {
            EventOpenHelper eventOpenHelper = new EventOpenHelper(this);

            int hour = timePicker.getCurrentHour();
            int minute = timePicker.getCurrentMinute();

            Event event = new Event();
            event.setTitle(titleEditText.getText().toString());
            event.setDateTime(datePicker.getYear() + "-" + (Integer) (datePicker.getMonth() + 1) + "-" +  datePicker.getDayOfMonth() + " " + String.format("%02d", hour) + ":" + String.format("%02d", minute));
            event.setCourse(courseSpinner.getSelectedItem().toString());
            event.setPriority(Integer.parseInt(prioritySpinner.getSelectedItem().toString()));
            event.setNotes(descriptionEditText.getText().toString());
            eventOpenHelper.insertMeeting(event);
        }
    }

}
