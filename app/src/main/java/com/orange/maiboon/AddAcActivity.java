package com.orange.maiboon;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddAcActivity extends Activity {

    BootstrapEditText note, rev, cost;
    BootstrapButton btn;
    SQLiteHelper dbHelper = new SQLiteHelper(this);
    TextView errorMsg;
    RelativeLayout errorFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ac);
        setupUI(findViewById(R.id.rw));

        note = (BootstrapEditText) findViewById(R.id.noteText);
        rev = (BootstrapEditText) findViewById(R.id.revText);
        cost = (BootstrapEditText) findViewById(R.id.costText);
        btn = (BootstrapButton) findViewById(R.id.submitBtn);
        errorMsg = (TextView) findViewById(R.id.errorMsg);
        errorFrame = (RelativeLayout) findViewById(R.id.errorFrame);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String fd = df.format(c.getTime());
                AcProfile ap = new AcProfile();
                boolean error = false;
                ap.setDate(fd);
                ap.setNote(note.getText().toString());
                try {
                    ap.setRev(Double.parseDouble(rev.getText().toString()));
                    ap.setCost(Double.parseDouble(cost.getText().toString()));
                } catch (NumberFormatException nfe) {
                    errorMsg.setText("数目输入错误");
                    errorFrame.setVisibility(View.VISIBLE);
                    error = true;
                }
                if (!error) {
                    dbHelper.addAcLog(ap);
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
                    hideSoftKeyboard(AddAcActivity.this);
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
