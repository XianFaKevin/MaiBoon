package com.orange.maiboon;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;

import java.util.Random;


public class MainActivity extends Activity {

    BootstrapButton acBtn;
    Button settingsBtn;
    BootstrapButton addAcBtn;
    BootstrapButton logBtn;
    BootstrapButton addLogBtn;
    //BootstrapCircleThumbnail bb;
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
                settingsDialog(MainActivity.this);
            }
        });

        //bb = (BootstrapCircleThumbnail) findViewById(R.id.bb);
        //setImage();
    }

    public void settingsDialog(final Activity context) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, 2);
        // set dialog message
        alertDialogBuilder
                .setTitle("确定清零资料库？")
                //.setMessage("确定清零资料库？")
                .setNegativeButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setIcon(R.drawable.warning)
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dbHelper.reset();
                        Toast.makeText(getApplicationContext(), "资料库清零完毕", Toast.LENGTH_LONG).show();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    /*public void setImage() {
        int count = new Random().nextInt(6);
        if (count == 0) bb.setImage(R.drawable.a1);
        else if (count == 1) bb.setImage(R.drawable.a2);
        else if (count == 2) bb.setImage(R.drawable.a4);
        else if (count == 3) bb.setImage(R.drawable.a5);
        else if (count == 4) bb.setImage(R.drawable.a8);
        else if (count == 5) bb.setImage(R.drawable.a11);
        else bb.setImage(R.drawable.a10);
    }*/

}
