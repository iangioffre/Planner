package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.planner.EventOpenHelper.COURSE;
import static com.example.planner.EventOpenHelper.DATE_TIME;
import static com.example.planner.EventOpenHelper.IS_DONE;
import static com.example.planner.EventOpenHelper.NAME;
import static com.example.planner.EventOpenHelper.NOTES;
import static com.example.planner.EventOpenHelper.PRIORITY;
import static com.example.planner.EventOpenHelper.TITLE;

public class AssignmentActivity extends AppCompatActivity {

    DatePicker datePicker;
    EditText titleEditText;
    Spinner prioritySpinner;
    ArrayList<String> courseList;
    Spinner courseSpinner;
    Spinner isDoneSpinner;
    EditText descriptionEditText;
    Intent intent;
    long intentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        titleEditText = (EditText) findViewById(R.id.title_edit_text);
        descriptionEditText = (EditText) findViewById(R.id.description_edit_text);
        prioritySpinner = (Spinner) findViewById(R.id.priority_spinner);
        courseSpinner = (Spinner) findViewById(R.id.course_spinner);
        isDoneSpinner = (Spinner) findViewById(R.id.is_done_spinner);

        EventOpenHelper openListHelper = new EventOpenHelper(this);
        courseList = openListHelper.getCourseNameList();

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> isDoneAdapter = ArrayAdapter.createFromResource(this, R.array.is_done_array, android.R.layout.simple_spinner_item);
        isDoneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        isDoneSpinner.setAdapter(isDoneAdapter);

        ArrayAdapter<String> courseNameAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courseList);
        courseNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(courseNameAdapter);

        intent = getIntent();
        if (intent.getExtras() != null) {
            intentID = intent.getLongExtra("id", 0);
            EventOpenHelper openHelper = new EventOpenHelper(this);
            Cursor cursor1 = openHelper.getAssignment(intentID);
            cursor1.moveToNext();

            titleEditText.setText(cursor1.getString(cursor1.getColumnIndex(TITLE)));
            descriptionEditText.setText(cursor1.getString(cursor1.getColumnIndex(NOTES)));
            prioritySpinner.setSelection(adapter.getPosition(cursor1.getString(cursor1.getColumnIndex(PRIORITY))));
            courseSpinner.setSelection(courseNameAdapter.getPosition(cursor1.getString(cursor1.getColumnIndex(COURSE))));
            if (Integer.parseInt(cursor1.getString(cursor1.getColumnIndex(IS_DONE))) == 0){
                isDoneSpinner.setSelection(0);
            } else {
                isDoneSpinner.setSelection(1);
            }
            String string_date = cursor1.getString(cursor1.getColumnIndex(DATE_TIME));
            String[]divide = string_date.split("-");
            int year = Integer.parseInt(divide[0]);
            int month = Integer.parseInt(divide[1]) - 1;
            int day = Integer.parseInt(divide[2]);
            datePicker.updateDate(year, month, day);
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
                boolean isSaved = saveAssignment();
                if (isSaved) {
                    finish();
                }
                return true;
        }
        return true;
    }


    public boolean saveAssignment () {
        if (intent.getExtras() != null) {
            EventOpenHelper eventOpenHelper = new EventOpenHelper(this);

            Assignment assignment = new Assignment();
            assignment.setId((int)intentID);
            assignment.setTitle(titleEditText.getText().toString());
            assignment.setDateTime(datePicker.getYear() + "-" + (Integer) (datePicker.getMonth() + 1) + "-" +  datePicker.getDayOfMonth());
            assignment.setCourse(courseSpinner.getSelectedItem().toString());
            assignment.setCourse(courseSpinner.getSelectedItem().toString());
            if (isDoneSpinner.getSelectedItemPosition() == 0) {
                assignment.setIsDone(0);
            } else {
                assignment.setIsDone(1);
            }
            assignment.setPriority(Integer.parseInt(prioritySpinner.getSelectedItem().toString()));
            assignment.setNotes(descriptionEditText.getText().toString());
            if(eventOpenHelper.isNewAssignment(assignment.getDateTime(), assignment.getTitle())) {
                eventOpenHelper.updateAssignment(assignment);
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Meeting already in Database! Please change title, date, or time", Toast.LENGTH_LONG).show();
                return false;
            }

    } else {
            EventOpenHelper eventOpenHelper = new EventOpenHelper(this);

            Assignment assignment = new Assignment();
            assignment.setTitle(titleEditText.getText().toString());
            assignment.setDateTime(datePicker.getYear() + "-" + (Integer) (datePicker.getMonth() + 1) + "-" +  datePicker.getDayOfMonth());
            assignment.setCourse(courseSpinner.getSelectedItem().toString());
            if (isDoneSpinner.getSelectedItemPosition() == 0) {
                assignment.setIsDone(0);
            } else {
                assignment.setIsDone(1);
            }
            assignment.setPriority(Integer.parseInt(prioritySpinner.getSelectedItem().toString()));
            assignment.setNotes(descriptionEditText.getText().toString());

            if(eventOpenHelper.isNewAssignment(assignment.getDateTime(), assignment.getTitle())) {
                eventOpenHelper.insertAssignment(assignment);
                return true;
            } else {
                Toast.makeText(getApplicationContext(), "Meeting already in Database! Please change title, date, or time", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }
}
