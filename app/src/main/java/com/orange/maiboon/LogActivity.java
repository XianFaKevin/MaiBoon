package com.orange.maiboon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class LogActivity extends Activity {

    ArrayList<Profile> list;
    SQLiteHelper dbHelper = new SQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = new ArrayList<Profile>();

        RowAdapter Adapter = new RowAdapter(LogActivity.this, dbHelper.list());

        ListView lv1 = (ListView) findViewById(R.id.listV);
        lv1.setAdapter(Adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ProfileInfo.class);
                startActivity(i);
            }
        });
    }

}
