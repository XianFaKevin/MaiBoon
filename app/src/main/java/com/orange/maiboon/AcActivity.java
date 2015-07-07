package com.orange.maiboon;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class AcActivity extends Activity {

    ListView lv;
    SQLiteHelper dbHelper = new SQLiteHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac);

        RowAdapter2 adapter = new RowAdapter2(AcActivity.this, dbHelper.listAc());
        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);
        Log.d(String.valueOf(dbHelper.listAc().size()), "dbj");
    }

}
