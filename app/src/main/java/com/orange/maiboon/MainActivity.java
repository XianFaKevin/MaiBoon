package com.orange.maiboon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;


public class MainActivity extends Activity {

    BootstrapButton acBtn;
    Button seedBtn, settingsBtn;
    BootstrapButton addAcBtn;
    BootstrapButton logBtn;
    BootstrapButton addLogBtn;
    SQLiteHelper dbHelper = new SQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acBtn = (BootstrapButton) findViewById(R.id.acBtn);
        acBtn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AcActivity.class);
                startActivity(i);
            }
        });

        addAcBtn = (BootstrapButton) findViewById(R.id.addAcBtn);
        addAcBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddAcActivity.class);
                startActivity(i);
            }
        });

        logBtn = (BootstrapButton) findViewById(R.id.logBtn);
        logBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LogActivity.class);
                startActivity(i);
            }
        });

        addLogBtn = (BootstrapButton) findViewById(R.id.addLogBtn);
        addLogBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(i);
            }
        });

        settingsBtn = (Button) findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.reset();
                Toast.makeText(getApplicationContext(), "Reset!", Toast.LENGTH_LONG);
            }
        });
    }

}
