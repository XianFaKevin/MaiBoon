package com.orange.maiboon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Kevin on 27/6/2015.
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "testdb";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_LOGS = "logs";
    public static final String COL_ID = "id";
    public static final String COL_DATE = "date";
    public static final String COL_CONTACT = "contact";
    public static final String COL_IN = "indate";
    public static final String COL_OUT = "outdate";
    public static final String COL_DURATION = "duration";
    public static final String COL_PEOPLE = "people";
    public static final String COL_ROOM = "room";
    public static final String COL_PRICE = "price";
    public static final String COL_PAID = "paid";
    public static final String COL_ACCOUNTED = "accounted";
    public static final String COL_REMARKS = "remarks";

    // Database creation sql statement
    private static final String TABLE_CREATE = "create table "
            + TABLE_LOGS + "(" + COL_ID
            + " integer primary key autoincrement, " + COL_DATE
            + " date, " + COL_CONTACT + " integer, " + COL_IN + " string, "
            + COL_OUT + " string, " + COL_DURATION + " integer, "
            + COL_PEOPLE + " integer, " + COL_ROOM + " string, "
            + COL_PRICE + " integer, " + COL_PAID + " boolean, "
            + COL_ACCOUNTED + " boolean, " + COL_REMARKS + " string);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        onCreate(db);
    }

    public void seed() {
        Random ran = new Random();
        int cont;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());
        int dur;
        int peo;
        String rm;
        for (int i=1; i<6; i++) {
            cont = ran.nextInt(9999999) + 90000000;
            dur = ran.nextInt(9) + 1;
            peo = ran.nextInt(5) + 1;
            rm = "A";
            Profile pf = new Profile();
            pf.setContact(cont);
            pf.setDate(formattedDate);
            pf.setIn(formattedDate);
            pf.setOut(formattedDate);
            pf.setDuration(dur);
            pf.setPeople(peo);
            pf.setRoom(rm);
            this.add(pf);
        }
    }

    public void add(Profile pf) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_CONTACT, pf.getContact());
        values.put(COL_DATE, pf.getDate());
        values.put(COL_IN, pf.getIn());
        values.put(COL_OUT, pf.getOut());
        values.put(COL_PEOPLE, pf.getPeople());
        values.put(COL_DURATION, pf.getDuration());
        values.put(COL_ROOM, pf.getRoom());

        db.insert(TABLE_LOGS, null, values);
        db.close();
    }

    public ArrayList<Profile> list() {
        ArrayList<Profile> list = new ArrayList<Profile>();
        String SELECT_QUERY = "Select * FROM " + TABLE_LOGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToFirst()) {
            do {
                Profile profile = new Profile();
                profile.setContact(cursor.getInt(2));
                profile.setDate(cursor.getString(1));
                profile.setIn(cursor.getString(3));
                profile.setOut(cursor.getString(4));
                profile.setPeople(cursor.getInt(6));
                profile.setDuration(cursor.getInt(5));
                profile.setRoom(cursor.getString(7));
                list.add(profile);
            } while (cursor.moveToNext());
        }
        return list;
    }
}

