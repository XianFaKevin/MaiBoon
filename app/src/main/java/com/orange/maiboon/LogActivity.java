package com.orange.maiboon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class LogActivity extends Activity {

    ArrayList<Profile> list;
    SQLiteHelper dbHelper = new SQLiteHelper(this);
    Button backBtn, addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        list = new ArrayList<Profile>();

        RowAdapter Adapter = new RowAdapter(LogActivity.this, dbHelper.list());

        ListView lv1 = (ListView) findViewById(R.id.listV);
        lv1.setAdapter(Adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ProfileInfo.class);
                i.putExtra("Id", dbHelper.list().get(position).getId());
                startActivity(i);
            }
        });

        backBtn = (Button) findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(i);
            }
        });
    }

}
