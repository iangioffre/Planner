package com.example.planner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c  = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

    }

}
