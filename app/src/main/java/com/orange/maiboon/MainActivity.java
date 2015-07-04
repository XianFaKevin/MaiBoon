package com.orange.maiboon;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    Button btn;
    Button seedBtn;
    SQLiteHelper dbHelper = new SQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), List.class);
                startActivity(i);
            }
        });

        seedBtn = (Button) findViewById(R.id.seedBtn);
        seedBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.seed();
                Toast.makeText(getApplicationContext(), "Database seeded!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}
