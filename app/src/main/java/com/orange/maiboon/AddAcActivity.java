package com.orange.maiboon;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AddAcActivity extends Activity {

    TextView note;
    TextView rev;
    TextView cost;
    Button btn;
    SQLiteHelper dbHelper = new SQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ac);

        note = (TextView) findViewById(R.id.noteText);
        rev = (TextView) findViewById(R.id.revText);
        cost = (TextView) findViewById(R.id.costText);
        btn = (Button) findViewById(R.id.submitBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String fd = df.format(c.getTime());
                AcProfile ap = new AcProfile();
                ap.setDate(fd);
                ap.setNote(note.getText().toString());
                ap.setRev(Integer.parseInt(rev.getText().toString()));
                ap.setCost(Integer.parseInt(cost.getText().toString()));
                dbHelper.addAcLog(ap);
            }
        });

    }

}
