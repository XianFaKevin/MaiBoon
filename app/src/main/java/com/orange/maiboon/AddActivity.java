package com.orange.maiboon;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddActivity extends FragmentActivity {

    BootstrapButton pickInDate, pickOutDate, addBtn;
    Spinner durationSpn, peopleSpn, roomSpn, accSpn;
    Boolean choosingIn;
    Calendar inCal, outCal;
    Profile profile;
    SQLiteHelper dbHelper = new SQLiteHelper(this);
    BootstrapEditText phone, price, note;
    String formattedInDate, formattedOutDate;
    String toRm = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setupUI(findViewById(R.id.rr));

        profile = new Profile();

        phone = (BootstrapEditText) findViewById(R.id.editPhoneText);
        price = (BootstrapEditText) findViewById(R.id.editPriceText);
        note = (BootstrapEditText) findViewById(R.id.editNoteText);

        pickInDate = (BootstrapButton) findViewById(R.id.pickInDate);
        pickInDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosingIn = true;
                showDatePickerDialog(view);
            }
        });
        pickOutDate = (BootstrapButton) findViewById(R.id.pickOutDate);
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
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        if (choosingIn) {
            formattedInDate = df2.format(c.getTime());
            String fInDate = df.format(c.getTime());
            pickInDate.setText(fInDate);
        }
        else {
            formattedOutDate = df2.format(c.getTime());
            String fOutDate = df.format(c.getTime());
            pickOutDate.setText(fOutDate);
        }
    }

    public boolean validate() {
        toRm = String.valueOf(roomSpn.getSelectedItem());
        String errorMsgs = "";
        boolean gotErrors = false;
        int toDur=0, toPeo=0, toAcc=0;
        Double toPrice=0.0;
        String nl = System.getProperty("line.separator");
        String toPhone;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        TextView errorText = (TextView) findViewById(R.id.errorText);

        toPhone = phone.getText().toString();
        toDur = Integer.parseInt(String.valueOf(durationSpn.getSelectedItem()));
        toPeo = Integer.parseInt(String.valueOf(peopleSpn.getSelectedItem()));
        if (String.valueOf(accSpn.getSelectedItem()).equals("没交")) toAcc = 1;
        else if (String.valueOf(accSpn.getSelectedItem()).equals("浸交")) toAcc = 2;
        else if (String.valueOf(accSpn.getSelectedItem()).equals("已交")) toAcc = 3;

        // catch phone number error
        /*try {
            toPhone = Integer.parseInt(phone.getText().toString());
        } catch (NumberFormatException nfe) {
            gotErrors = true;
            errorMsgs += "电话号码只能有数字" + nl;
        }*/

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
        addBtn = (BootstrapButton) findViewById(R.id.addBtn);


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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(AddActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }
}
