//date picker from https://github.com/gantonious/MaterialDayPicker

package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class AddClassActivity extends AppCompatActivity {

    int monday;
    int tuesday;
    int wednesday;
    int thursday;
    int friday;
    String className;
    int startTimeHour;
    int startTimeMinute;
    int endTimeHour;
    int endTimeMinute;

    public AddClassActivity() {
        monday = 0;
        tuesday = 0;
        wednesday = 0;
        thursday = 0;
        friday = 0;
        className = "Class Name";
        startTimeHour = 1;
        startTimeMinute = 1;
        endTimeMinute = 1;
        endTimeHour = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final MaterialDayPicker materialDayPicker = (MaterialDayPicker) findViewById(R.id.day_picker);
        materialDayPicker.setDaySelectionChangedListener(new MaterialDayPicker.DaySelectionChangedListener() {
            @Override
            public void onDaySelectionChanged(List<MaterialDayPicker.Weekday> list) {
                if(list.contains(MaterialDayPicker.Weekday.SATURDAY)){
                    materialDayPicker.deselectDay(MaterialDayPicker.Weekday.SATURDAY);
                    Toast.makeText(AddClassActivity.this, "No class on weekends. Enjoy the day off!", Toast.LENGTH_SHORT).show();
                }
                if(list.contains(MaterialDayPicker.Weekday.SUNDAY)){
                    materialDayPicker.deselectDay(MaterialDayPicker.Weekday.SUNDAY);
                    Toast.makeText(AddClassActivity.this, "No class on weekends. Enjoy the day off!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void onSave(){
        EditText nameEdit = (EditText) findViewById(R.id.editText);
        this.className = nameEdit.getText().toString();
        MaterialDayPicker materialDayPicker = (MaterialDayPicker) findViewById(R.id.day_picker);
        List<MaterialDayPicker.Weekday> days = materialDayPicker.getSelectedDays();
        if (days.contains(MaterialDayPicker.Weekday.MONDAY)) {
            this.monday = 1;
        }
        if (days.contains(MaterialDayPicker.Weekday.TUESDAY)) {
            this.tuesday = 1;
        }
        if (days.contains(MaterialDayPicker.Weekday.WEDNESDAY)) {
            this.wednesday = 1;
        }
        if (days.contains(MaterialDayPicker.Weekday.THURSDAY)) {
            this.thursday = 1;
        }
        if (days.contains(MaterialDayPicker.Weekday.FRIDAY)) {
            this.friday = 1;
        }
        TimePicker startTimePicker = (TimePicker) findViewById(R.id.timePicker1);
        TimePicker endTimePicker = (TimePicker) findViewById(R.id.timePicker2);
        this.startTimeHour = startTimePicker.getCurrentHour();
        this.startTimeMinute = startTimePicker.getCurrentMinute();
        this.endTimeHour = endTimePicker.getCurrentHour();
        this.endTimeMinute = endTimePicker.getCurrentMinute();

        String startTime = "2001-01-01 " + String.format("%02d", startTimeHour) + ":" + String.format("%02d", startTimeMinute) + ":00";
        String endTime = "2001-01-01 " + String.format("%02d", endTimeHour) + ":" + String.format("%02d", endTimeMinute) + ":00";
        Course course = new Course(-1, className, monday, tuesday, wednesday, thursday, friday, startTime, endTime);
        EventOpenHelper eventOpenHelper = new EventOpenHelper(this);
        eventOpenHelper.insertCourse(course);
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
                onSave();
                finish();
                return true;
        }
        return true;
    }
}
