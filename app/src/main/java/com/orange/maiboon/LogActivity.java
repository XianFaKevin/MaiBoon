package com.orange.maiboon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.beardedhen.androidbootstrap.BootstrapButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class LogActivity extends Activity {

    ArrayList<Profile> mList, yList, aList;
    SQLiteHelper dbHelper = new SQLiteHelper(this);
    BootstrapButton backBtn, addBtn, month, year, all;
    boolean mNeedUpdate = true;
    boolean yNeedUpdate = true;
    boolean aNeedUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        backBtn = (BootstrapButton) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addBtn = (BootstrapButton) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(i);
                finish();
            }
        });

        month = (BootstrapButton) findViewById(R.id.month);
        month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayMonth();
            }
        });

        year = (BootstrapButton) findViewById(R.id.year);
        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayYear();
            }
        });

        all = (BootstrapButton) findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayAll();
            }
        });

        displayMonth();
    }

    public void displayMonth() {
        if (mNeedUpdate) {
            mList = new ArrayList<Profile>();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 3);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String curDate = df.format(c.getTime());
            mList = dbHelper.list(curDate);
            mNeedUpdate = false;
        }

        RowAdapter Adapter = new RowAdapter(this, mList);

        ListView lv1 = (ListView) findViewById(R.id.listV);
        lv1.setAdapter(Adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ProfileInfo.class);
                i.putExtra("Id", mList.get(position).getId());
                startActivity(i);
                finish();
            }
        });
        month.setEnabled(false);
        year.setEnabled(true);
        all.setEnabled(true);
    }

    public void displayYear() {
        if (yNeedUpdate) {
            yList = new ArrayList<Profile>();
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 1);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String curDate = df.format(c.getTime());
            yList = dbHelper.list(curDate);
            yNeedUpdate = false;
        }

        RowAdapter Adapter = new RowAdapter(this, yList);

        ListView lv1 = (ListView) findViewById(R.id.listV);
        lv1.setAdapter(Adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ProfileInfo.class);
                i.putExtra("Id", yList.get(position).getId());
                startActivity(i);
                finish();
            }
        });
        month.setEnabled(true);
        year.setEnabled(false);
        all.setEnabled(true);
    }

    public void displayAll() {
        if (aNeedUpdate) {
            aList = new ArrayList<Profile>();
            aList = dbHelper.list("");
            aNeedUpdate = false;
        }

        RowAdapter Adapter = new RowAdapter(this, aList);

        ListView lv1 = (ListView) findViewById(R.id.listV);
        lv1.setAdapter(Adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ProfileInfo.class);
                i.putExtra("Id", aList.get(position).getId());
                startActivity(i);
                finish();
            }
        });
        month.setEnabled(true);
        year.setEnabled(true);
        all.setEnabled(false);
    }
}
