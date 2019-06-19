package edu.arizona.uas.glucose.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.arizona.uas.glucose.Glucose;
import edu.arizona.uas.glucose.MyDate;

public class GlucoseDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GlucoseDB.db";
    private static SQLiteDatabase db = null;

    private GlucoseDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static void initialize(Context context) {
        if(db == null)
            db = new GlucoseDatabase(context.getApplicationContext())
                    .getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + GlucoseSchema.GlucoseTabel.NAME +
                                                                                                                            "("+ "_id integer primary key autoincrement" + "," +
                                                                                                                                    GlucoseSchema.GlucoseTabel.Col.DATE + "," +
                                                                                                                                    GlucoseSchema.GlucoseTabel.Col.NOTE + "," +
                                                                                                                                    GlucoseSchema.GlucoseTabel.Col.FASTING + "," +
                                                                                                                                    GlucoseSchema.GlucoseTabel.Col.BREAKFAST + "," +
                                                                                                                                    GlucoseSchema.GlucoseTabel.Col.LUNCH + "," +
                                                                                                                                    GlucoseSchema.GlucoseTabel.Col.DINNER + ")"
                            );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private static ContentValues getContentValues(Glucose glucose) {

        ContentValues values = new ContentValues();
        values.put(GlucoseSchema.GlucoseTabel.Col.DATE, glucose.date.toString());
        values.put(GlucoseSchema.GlucoseTabel.Col.NOTE, glucose.note);
        values.put(GlucoseSchema.GlucoseTabel.Col.FASTING, glucose.fasting_val);
        values.put(GlucoseSchema.GlucoseTabel.Col.BREAKFAST, glucose.breakfast_val);
        values.put(GlucoseSchema.GlucoseTabel.Col.LUNCH, glucose.lunch_val);
        values.put(GlucoseSchema.GlucoseTabel.Col.DINNER, glucose.dinner_val);
        return values;
    }


    public static void insertGlucoseTable(Glucose glucose) {
        db.insert(GlucoseSchema.GlucoseTabel.NAME, null, getContentValues(glucose));
    }



    public static void updateGlucoseTable(Glucose glucose) {
        db.update(GlucoseSchema.GlucoseTabel.NAME,
                            getContentValues(glucose),
                GlucoseSchema.GlucoseTabel.Col.DATE + "=?",
                            new String[] {glucose.date.toString()});
    }


    private static GlucoseCursorWrapper queryGlucoseObject() {
        return  new GlucoseCursorWrapper(db.query(GlucoseSchema.GlucoseTabel.NAME, null, null, null, null, null, null));
    }


    public static  List<Glucose> getAllGlucoseObjects() {

        List<Glucose> lst = new ArrayList<>();
        GlucoseCursorWrapper recordItr = queryGlucoseObject();
        recordItr.moveToFirst();

        while(!recordItr.isAfterLast()) {
            lst.add(recordItr.getConstructedGlucoseObject());
            recordItr.moveToNext();
        }
        return lst;
    }


/*
* Inner class for cursor wrapper
*/
    static class GlucoseCursorWrapper extends CursorWrapper {

        public GlucoseCursorWrapper(Cursor cursor) {
            super(cursor);
        }


        public Glucose getConstructedGlucoseObject() {
            MyDate date = new MyDate(getString(getColumnIndex(GlucoseSchema.GlucoseTabel.Col.DATE)));
            String note = getString(getColumnIndex(GlucoseSchema.GlucoseTabel.Col.NOTE));

            int  fasting = getInt(getColumnIndex(GlucoseSchema.GlucoseTabel.Col.FASTING));
            int  breakfast = getInt(getColumnIndex(GlucoseSchema.GlucoseTabel.Col.BREAKFAST));
            int  lunch = getInt(getColumnIndex(GlucoseSchema.GlucoseTabel.Col.LUNCH));
            int  dinner = getInt(getColumnIndex(GlucoseSchema.GlucoseTabel.Col.DINNER));

            return new Glucose(fasting, breakfast, lunch, dinner,  date, note);
        }
    }
}