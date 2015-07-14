package com.orange.maiboon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;


public class ProfileInfo extends Activity {

    TextView date;
    TextView phone;
    TextView inDate;
    TextView outDate;
    TextView dur;
    TextView peo;
    TextView rm;
    TextView price;
    TextView note;
    Spinner acc;
    TextView accText;
    BootstrapButton btn;
    BootstrapButton backBtn;
    TextView dummyView;
    int id;
    Profile p;
    boolean modified = false;
    SQLiteHelper dbHelper = new SQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        Intent i = getIntent();
        id = i.getIntExtra("Id", -1);
        p = new Profile();

        date = (TextView) findViewById(R.id.creationText);
        phone = (TextView) findViewById(R.id.phoneText);
        inDate = (TextView) findViewById(R.id.inText);
        outDate = (TextView) findViewById(R.id.outText);
        dur = (TextView) findViewById(R.id.durText);
        peo = (TextView) findViewById(R.id.peoText);
        rm = (TextView) findViewById(R.id.rmText);
        price = (TextView) findViewById(R.id.priceText);
        note = (TextView) findViewById(R.id.noteText);
        acc = (Spinner) findViewById(R.id.feeSpn);
        accText = (TextView) findViewById(R.id.feeText);
        dummyView = (TextView) findViewById(R.id.dummyView);

        backBtn = (BootstrapButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn = (BootstrapButton) findViewById(R.id.changeBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update(id, acc.getSelectedItemPosition()+1);
            }
        });

        acc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (modified) btn.setBootstrapButtonEnabled(true);
                else modified = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        display();
    }

    public void display() {
        p = dbHelper.get(id);
        date.setText(p.getDate());
        phone.setText(String.valueOf(p.getContact()));
        inDate.setText(p.getIn());
        outDate.setText(p.getOut());
        dur.setText(String.valueOf(p.getDuration()));
        peo.setText(String.valueOf(p.getPeople()));
        rm.setText(p.getRoom());
        price.setText(String.valueOf(p.getPrice()));
        acc.setSelection(p.getAccounted() - 1);
        if (p.getAccounted() == 3) accText.setText("已交");
        note.setText(p.getRemarks());
        if (p.getAccounted() == 3) {
            btn.setVisibility(View.GONE);
            acc.setVisibility(View.GONE);
            accText.setVisibility(View.VISIBLE);
            backBtn.setPadding(0, 0, 0, 0);
            dummyView.setText("");
        }
    }

    public void update(int id, int acc) {
        dbHelper.updateAccounted(id, acc);
        Intent i = new Intent(getApplicationContext(), LogActivity.class);
        startActivity(i);
        finish();
    }
}
