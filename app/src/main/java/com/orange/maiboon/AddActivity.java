package com.orange.maiboon;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EmptyStackException;


public class AddActivity extends FragmentActivity {

    Button pickInDate, pickOutDate, addBtn;
    Spinner durationSpn, peopleSpn, roomSpn, accSpn;
    Boolean choosingIn;
    Calendar inCal, outCal;
    Profile profile;
    SQLiteHelper dbHelper = new SQLiteHelper(this);
    EditText phone, price, note;
    String formattedInDate, formattedOutDate;
    String toRm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        profile = new Profile();

        phone = (EditText) findViewById(R.id.editPhoneText);
        price = (EditText) findViewById(R.id.editPriceText);
        note = (EditText) findViewById(R.id.editNoteText);

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

        addListenerOnButton();
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
        if (choosingIn) {
            formattedInDate = df.format(c.getTime());
            pickInDate.setText(formattedInDate);
        }
        else {
            formattedOutDate = df.format(c.getTime());
            pickOutDate.setText(formattedOutDate);
        }
    }

    public boolean validate() {
        toRm = String.valueOf(roomSpn.getSelectedItem());
        String errorMsgs = "";
        boolean gotErrors = false;
        int toPhone=0, toDur=0, toPeo=0, toAcc=0;
        Double toPrice=0.0;
        String nl = System.getProperty("line.separator");
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

        TextView errorText = (TextView) findViewById(R.id.errorText);

        toDur = Integer.parseInt(String.valueOf(durationSpn.getSelectedItem()));
        toPeo = Integer.parseInt(String.valueOf(peopleSpn.getSelectedItem()));
        if (String.valueOf(accSpn.getSelectedItem()).equals("没交")) toAcc = 1;
        else if (String.valueOf(accSpn.getSelectedItem()).equals("浸交")) toAcc = 2;
        else if (String.valueOf(accSpn.getSelectedItem()).equals("已交")) toAcc = 3;

        // catch phone number error
        try {
            toPhone = Integer.parseInt(phone.getText().toString());
        } catch (NumberFormatException nfe) {
            gotErrors = true;
            errorMsgs += "电话号码只能有数字" + nl;
        }

        // catch dates not set & date format error
        try {
            Date d = df.parse(formattedInDate);
            Date dd = df.parse(formattedOutDate);
            if (d.after(dd)) {
                gotErrors = true;
                errorMsgs += "住的日期不对" + nl;
            }
        } catch (Exception e) {
            gotErrors = true;
            errorMsgs += "日期输入错误" + nl;
        }

        // check if room is booked
        if (!dbHelper.check(formattedInDate, formattedOutDate, toRm)) {
            gotErrors = true;
            errorMsgs += "房间已有人住" + nl;
        }

        // check price not set or error format
        try {
            toPrice = Double.parseDouble(price.getText().toString());
            toPrice = toPrice * 100;
        } catch (NumberFormatException nfe) {
            gotErrors = true;
            errorMsgs += "价钱输入错误";
        }

        // display input errors or add new log
        if (gotErrors) {
            errorText.setText(errorMsgs);
            errorText.setVisibility(View.VISIBLE);
            Log.d("price as: ", String.valueOf(toPrice.intValue()));
        } else {
            profile.setContact(toPhone);
            profile.setDuration(toDur);
            profile.setPeople(toPeo);
            profile.setRoom(toRm);
            profile.setIn(formattedInDate);
            profile.setOut(formattedOutDate);
            Calendar c = Calendar.getInstance();
            String now = df.format(c.getTime());
            profile.setDate(now);
            profile.setRemarks(note.getText().toString());
            profile.setPrice(toPrice.intValue());
            Log.d("Acc: ", Integer.toString(toAcc));
            profile.setAccounted(toAcc);
            dbHelper.add(profile);
        }
        return gotErrors;
    }

    public void addListenerOnButton() {
        roomSpn = (Spinner) findViewById(R.id.roomSpn);
        durationSpn = (Spinner) findViewById(R.id.durationSpn);
        peopleSpn = (Spinner) findViewById(R.id.peopleSpn);
        accSpn = (Spinner) findViewById(R.id.feeSpn);
        addBtn = (Button) findViewById(R.id.addBtn);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Intent i = new Intent(getApplicationContext(), LogActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        });
    }
}
