package com.example.planner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import java.util.List;

import ca.antonious.materialdaypicker.MaterialDayPicker;

public class AddClassActivity extends AppCompatActivity {

    int monday;
    int tuesday;
    int wednesday;
    int thursday;
    int friday;

    public AddClassActivity() {
        this.monday = 0;
        this.tuesday = 0;
        this.wednesday = 0;
        this.thursday = 0;
        this.friday = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

    }

    public void onSave(){
        EditText nameEdit = (EditText) findViewById(R.id.editText);
        String className = nameEdit.getText().toString();
        MaterialDayPicker materialDayPicker = (MaterialDayPicker) findViewById(R.id.day_picker);
        List<MaterialDayPicker.Weekday> days = materialDayPicker.getSelectedDays();
        if (days.contains(MaterialDayPicker.Weekday.MONDAY)) {
            monday = 1;
        }
        if (days.contains(MaterialDayPicker.Weekday.TUESDAY)) {
            tuesday = 1;
        }
        if (days.contains(MaterialDayPicker.Weekday.WEDNESDAY)) {
            wednesday = 1;
        }
        if (days.contains(MaterialDayPicker.Weekday.THURSDAY)) {
            thursday = 1;
        }
        if (days.contains(MaterialDayPicker.Weekday.FRIDAY)) {
            friday = 1;
        }
    }
}
