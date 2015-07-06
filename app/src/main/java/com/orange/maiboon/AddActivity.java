package com.orange.maiboon;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddActivity extends FragmentActivity {

    TextView inDate;
    Button pickInDate;
    Button pickOutDate;
    Spinner durationSpn, peopleSpn, roomSpn;
    Boolean choosingIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inDate = (TextView) findViewById(R.id.inDate);
        pickInDate = (Button) findViewById(R.id.pickInDate);
        pickInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosingIn = true;
                showDatePickerDialog(view);
            }
        });
        pickOutDate = (Button) findViewById(R.id.pickOutDate);
        pickOutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosingIn = false;
                showDatePickerDialog(view);
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newInFragment = new DatePickerFragment();
        newInFragment.show(getFragmentManager(), "datePicker");
    }

    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            showDate(c);
        }
    }

    public void showDate(Calendar c) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        //inDate.setText(formattedDate);
        if (choosingIn) pickInDate.setText(formattedDate);
        else pickOutDate.setText(formattedDate);
        try {
            c.setTime(df.parse(formattedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(), df.format(c.getTime()),
          //      Toast.LENGTH_LONG).show();
    }


}
