package com.orange.maiboon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final String COL_ACCOUNTED = "accounted";
    public static final String COL_REMARKS = "remarks";
    public static final String TABLE_AC = "ac";
    public static final String COL_REV = "revenue";
    public static final String COL_COST = "cost";
    public static final String COL_PROFIT = "profit";

    // Database creation sql statement
    private static final String TABLE_CREATE_LOGS = "create table "
            + TABLE_LOGS + "(" + COL_ID
            + " integer primary key autoincrement, " + COL_DATE
            + " date, " + COL_CONTACT + " string, " + COL_IN + " string, "
            + COL_OUT + " string, " + COL_DURATION + " integer, "
            + COL_PEOPLE + " integer, " + COL_ROOM + " string, "
            + COL_PRICE + " integer, " + COL_ACCOUNTED + " integer, "
            + COL_REMARKS + " string);";

    private static final String TABLE_CREATE_AC = "create table "
            + TABLE_AC + "(" + COL_ID
            + " integer primary key autoincrement, " + COL_DATE
            + " date, " + COL_REMARKS + " string, " + COL_REV
            + " integer, " + COL_COST + " integer, " + COL_PROFIT
            + " integer);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE_LOGS);
        db.execSQL(TABLE_CREATE_AC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AC);
        onCreate(db);
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
        values.put(COL_PRICE, pf.getPrice());
        values.put(COL_ACCOUNTED, pf.getAccounted());
        values.put(COL_REMARKS, pf.getRemarks());

        db.insert(TABLE_LOGS, null, values);
        db.close();
    }

    public void updateAccounted(int id, int acc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ACCOUNTED, acc);
        db.update(TABLE_LOGS, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public boolean check(String inD, String outD, String rm) {
        String SELECT_QUERY = "Select * FROM " + TABLE_LOGS + " WHERE " + COL_IN + " BETWEEN '" +
                inD + "' AND '" + outD + "' AND " + COL_OUT + " BETWEEN '" + inD + "' AND '" + outD +
                "' AND " + COL_ROOM + " = '" + rm + "'";

        //String SELECT_QUERY = "Select * FROM " + TABLE_LOGS + " WHERE " + COL_ROOM + " = '" + rm + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        Log.d("checking db for dates", "k");
        if (cursor.moveToFirst()) {
            Log.d("Number of logs: ",String.valueOf(cursor.getCount()));
            Log.d("in date: ", inD);
            Log.d("out date: ", outD);
            return false;
        }
        else {
            Log.d("No logs", "true");
            return true;
        }
    }

    public ArrayList<Profile> list(String curDate) {
        ArrayList<Profile> list = new ArrayList<Profile>();
        String SELECT_QUERY = "";
        if (curDate != "") SELECT_QUERY = "Select * FROM " + TABLE_LOGS + " WHERE "
                + COL_OUT + " >= '" + curDate + "'";
        else SELECT_QUERY = "Select * FROM " + TABLE_LOGS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToLast()) {
            do {
                Profile profile = new Profile();
                profile.setId(cursor.getInt(0));
                profile.setContact(cursor.getString(2));
                profile.setDate(cursor.getString(1));
                profile.setIn(cursor.getString(3));
                profile.setOut(cursor.getString(4));
                profile.setPeople(cursor.getInt(6));
                profile.setDuration(cursor.getInt(5));
                profile.setRoom(cursor.getString(7));
                profile.setPrice(cursor.getInt(8) / 100);
                profile.setAccounted(cursor.getInt(9));
                profile.setRemarks(cursor.getString(10));
                list.add(profile);
            } while (cursor.moveToPrevious());
        }
        return list;
    }

    public Profile get(int id) {
        String SELECT_QUERY = "SELECT * FROM " + TABLE_LOGS + " WHERE " + COL_ID
                + " = '" + id + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToNext()) {
            Profile profile = new Profile();
            profile.setId(cursor.getInt(0));
            profile.setContact(cursor.getString(2));
            profile.setDate(cursor.getString(1));
            profile.setIn(cursor.getString(3));
            profile.setOut(cursor.getString(4));
            profile.setPeople(cursor.getInt(6));
            profile.setDuration(cursor.getInt(5));
            profile.setRoom(cursor.getString(7));
            Log.d("db price: ", String.valueOf(cursor.getInt(8)));
            profile.setPrice(cursor.getInt(8)/100);
            Log.d("p price: ", String.valueOf(profile.getPrice()));
            profile.setAccounted(cursor.getInt(9));
            profile.setRemarks(cursor.getString(10));
            return profile;
        } else return null;
    }

    public void addAcLog(AcProfile ap) {
        SQLiteDatabase db = this.getWritableDatabase();
        int profit = getBalance();
        profit += ap.getRev();
        profit -= ap.getCost();
        ContentValues values = new ContentValues();
        values.put(COL_DATE, ap.getDate());
        values.put(COL_REMARKS,ap.getNote());
        values.put(COL_REV, ap.getRev());
        values.put(COL_COST, ap.getCost());
        values.put(COL_PROFIT, profit);
        db.insert(TABLE_AC, null, values);
        db.close();
    }

    public int getBalance() {
        String SELECT_QUERY = "SELECT * FROM " + TABLE_AC;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToLast()) {
            return cursor.getInt(5);
        } else return -1;
    }

    public ArrayList<AcProfile> listAc() {
        String SELECT_QUERY = "SELECT * FROM " + TABLE_AC;
        ArrayList<AcProfile> list = new ArrayList<AcProfile>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor.moveToLast()) {
            do {
                AcProfile ap = new AcProfile();
                ap.setId(cursor.getInt(0));
                ap.setDate(cursor.getString(1));
                ap.setNote(cursor.getString(2));
                ap.setRev(cursor.getInt(3));
                ap.setCost(cursor.getInt(4));
                ap.setProfit(cursor.getInt(5));
                list.add(ap);
            } while(cursor.moveToPrevious());
        }
        return list;
    }

    public void reset() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGS, null, null);
        db.delete(TABLE_AC, null, null);
    }
}

