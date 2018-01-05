package com.orchard.seg.busbump.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.orchard.seg.busbump.model.BusInfo;

import java.util.LinkedList;
import java.util.List;

public class BusInfoRepository extends SQLiteOpenHelper {
    private static final String TAG = "BusInfoRepository";

    private static final String DATABASE_NAME = "BUS_INFO_DATABASE";
    private static  final int DATABASE_VERSION = 1;

    public BusInfoRepository(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.w(TAG, "Creating BusInfo table");
        db.execSQL("CREATE TABLE " + BusInfoTable.TABLE_NAME.get() + " ("
                + BusInfoTable._ID.get() + " INTEGER PRIMARY KEY"
                + ", " + BusInfoTable.COL_STOP_NO.get() + " INTEGER"
                + ", " + BusInfoTable.COL_BUS_NO.get() + " INTEGER"
                + ", " + BusInfoTable.COL_DIRECTION_NO.get() + " INTEGER"
                + ", " + BusInfoTable.COL_LOAD_ON_LAUNCH.get() + " INTEGER"
                + ", " + BusInfoTable.COL_NAME.get() + " TEXT"
                + ", " + BusInfoTable.COL_COLOR.get() + " TEXT"
                + ");");
        Log.w(TAG, "BusInfo table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading BusInfo table");
        db.execSQL("DROP TABLE IF EXISTS " + BusInfoTable.TABLE_NAME.get());
        onCreate(db);
    }

    public List<BusInfo> getBusInfoDataSet() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<BusInfo> busInfos = new LinkedList<>();
        Cursor cursor = db.query(BusInfoTable.TABLE_NAME.get(),
                null,null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                busInfos.add(busInfoFromCursor(cursor));
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        return busInfos;
    }

    public long insertBusInfo(BusInfo busInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = valuesFromBusInfo(busInfo);
        if (busInfo.getId() < 0) {
            long id =  db.insert(BusInfoTable.TABLE_NAME.get(), null, values);
            busInfo.setId(id);
            return id;
        } else {
            db.update(BusInfoTable.TABLE_NAME.get(), values,
                    "ID=?",
                    new String[]{String.valueOf(busInfo.getId())});
            return busInfo.getId();
        }
    }

    //Todo: Populate ID field
    private BusInfo busInfoFromCursor(Cursor cursor) {
        long id =
                cursor.getLong(cursor.getColumnIndex(BusInfoTable._ID.get()));
        int stopNo =
                cursor.getInt(cursor.getColumnIndex(BusInfoTable.COL_STOP_NO.get()));
        int busNo =
                cursor.getInt(cursor.getColumnIndex(BusInfoTable.COL_BUS_NO.get()));
        int directionNo =
                cursor.getInt(cursor.getColumnIndex(BusInfoTable.COL_DIRECTION_NO.get()));
        boolean loadOnLaunch = (1 ==
                cursor.getInt(cursor.getColumnIndex(BusInfoTable.COL_LOAD_ON_LAUNCH.get())));
        String name =
                cursor.getString(cursor.getColumnIndex(BusInfoTable.COL_NAME.get()));
        String color =
                cursor.getString(cursor.getColumnIndex(BusInfoTable.COL_COLOR.get()));
        BusInfo busInfo = new BusInfo(stopNo, busNo, directionNo, loadOnLaunch, name, color);
        busInfo.setId(id);
        return busInfo;
    }

    private ContentValues valuesFromBusInfo(BusInfo busInfo) {
        ContentValues values = new ContentValues();
        values.put(BusInfoTable.COL_STOP_NO.get(), busInfo.getStopNumber());
        values.put(BusInfoTable.COL_BUS_NO.get(), busInfo.getBusNumber());
        values.put(BusInfoTable.COL_DIRECTION_NO.get(), busInfo.getDirectionNumber());
        values.put(BusInfoTable.COL_LOAD_ON_LAUNCH.get(),
                busInfo.loadOnLaunch() ? 1 : 0);
        values.put(BusInfoTable.COL_NAME.get(), busInfo.getName());
        values.put(BusInfoTable.COL_COLOR.get(), busInfo.getColor());
        return values;
    }

    //Todo: Should be removed once BusInfo CRUD is implemented
    @Deprecated
    public void injectSampleData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + BusInfoTable.TABLE_NAME.get());
        onCreate(db);
        db.close();

        List<BusInfo> dataSet = getSampleDataSet();
        for (BusInfo busInfo : dataSet) {
            insertBusInfo(busInfo);
        }
    }
    @Deprecated
    private List<BusInfo> getSampleDataSet() {
        List<BusInfo> dataSet = new LinkedList<>();
        dataSet.add(new BusInfo(7613, 16, 1));
        dataSet.add(new BusInfo(7610, 16, 0));
        dataSet.add(new BusInfo(3020, 44, 1));
        return dataSet;
    }

    private enum BusInfoTable {
        TABLE_NAME("BUS_INFO"),
        _ID("_id"),
        COL_STOP_NO("STOP_NO"),
        COL_BUS_NO("BUS_NO"),
        COL_DIRECTION_NO("DIRECTION_NO"),
        COL_LOAD_ON_LAUNCH("LOAD_ON_LAUNCH"),
        COL_NAME("NAME"),
        COL_COLOR("COLOR");

        private String mValue;

        BusInfoTable(String value) {
            this.mValue = value;
        }

        public String get() {
            return this.mValue;
        }
    }
}
