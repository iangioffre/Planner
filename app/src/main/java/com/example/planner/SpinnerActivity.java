package com.example.planner;


import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

public class SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // can retrieve the selected item using parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        //Another interface callback
    }
}
