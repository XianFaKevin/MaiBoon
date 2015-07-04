package com.orange.maiboon;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class List extends Activity {

    ArrayList<Profile> list;
    SQLiteHelper dbHelper = new SQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        list = new ArrayList<Profile>();

        RowAdapter Adapter = new RowAdapter(List.this, dbHelper.list());

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
