package com.example.anhlamrduc.sothuchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.Event;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class EventController extends SQLiteAssetHelper {

    private static final String KEY_ID = "MaSuKien";
    private static final String KEY_NAME = "TenSuKien";
    private static final String KEY_START_DATE = "NgayBatDau";
    private static final String KEY_STATUS = "TrangThai";

    private static final String DB_TABLE = "SuKien";

    public EventController(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }

    /**
     * @return list Account
     */
    public ArrayList<Event> getListEvent() {

        ArrayList<Event> events = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT " + KEY_ID + ", " + KEY_NAME + ", " + KEY_START_DATE +
                    ", " + KEY_STATUS + " FROM " + DB_TABLE;

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {
                    int id = cs.getInt(cs.getColumnIndex(KEY_ID));
                    String eventName = cs.getString(cs.getColumnIndex(KEY_NAME));
                    String startDate = cs.getString(cs.getColumnIndex(KEY_START_DATE));
                    String status = cs.getString(cs.getColumnIndex(KEY_STATUS));

                    Event event = new Event(id, eventName, startDate, status);
                    events.add(event);
                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return events;
    }

//    public Receiver getReceiverByID(int id) {
//        Receiver receiver = new Receiver();
//        try {
//            //open connect to database
//            SQLiteDatabase db = getReadableDatabase();
//            Cursor cs = db.query(true, DB_TABLE, new String[]{KEY_ID, KEY_NAME},
//                    KEY_ID + "=" + id, null, null, null, null, null);
//            if (cs.moveToFirst()) {
//                int maNguoiDuocChi = cs.getInt(cs.getColumnIndex(KEY_ID));
//                String tenNguoiDuocChi = cs.getString(cs.getColumnIndex(KEY_NAME));
//
//                receiver = new Receiver(maNguoiDuocChi, tenNguoiDuocChi);
//            }
//        } catch (Exception e) {
//            e.getMessage();
//        } finally {
//            //close connect to database
//            close();
//        }
//        return receiver;
//    }

    public Event getEventByName(String name) {
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT * FROM " + DB_TABLE +
                    " WHERE " + KEY_NAME + " LIKE '" + name.trim() + "' OR " + KEY_NAME + "='" + KEY_NAME + "'";

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                int id = cs.getInt(cs.getColumnIndex(KEY_ID));
                String eventName = cs.getString(cs.getColumnIndex(KEY_NAME));
                String startDate = cs.getString(cs.getColumnIndex(KEY_START_DATE));
                String status = cs.getString(cs.getColumnIndex(KEY_STATUS));

                Event event = new Event(id, eventName, startDate, status);

                return event;
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return null;
    }

    public long addEvent(Event event) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, event.getEventName());
            Date currentDay = Calendar.getInstance().getTime();
            contentValues.put(KEY_START_DATE, currentDay.toString());
            contentValues.put(KEY_STATUS, "Đang diễn ra");
            return db.insert(DB_TABLE, null, contentValues);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }
}
