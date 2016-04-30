package com.example.anhlamrduc.sothuchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class ReceiverController extends SQLiteAssetHelper {

    private static final String KEY_ID = "MaNguoiDuocChi";
    private static final String KEY_NAME = "TenNguoiDuocChi";
    private static final String DB_TABLE = "NguoiDuocChi";

    private static final String DB_NAME = "misa.sqlite";
    private static final int DB_VERSION = 1;

    public ReceiverController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * @return list Account
     */
    public ArrayList<Receiver> getListReceiver() {

        ArrayList<Receiver> arrReceiver = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT " + KEY_ID + ", " + KEY_NAME 
                    + " FROM " + DB_TABLE;

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {
                    int maNguoiDuocChi = cs.getInt(cs.getColumnIndex(KEY_ID));
                    String tenNguoiDuocChi = cs.getString(cs.getColumnIndex(KEY_NAME));

                    Receiver receiver = new Receiver(maNguoiDuocChi, tenNguoiDuocChi);
                    arrReceiver.add(receiver);
                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return arrReceiver;
    }

    public Receiver getReceiverByID(int id) {
        Receiver receiver = new Receiver();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            Cursor cs = db.query(true, DB_TABLE, new String[] {KEY_ID, KEY_NAME},
                    KEY_ID+ "=" +id, null, null, null, null, null);
            if (cs.moveToFirst()) {
                    int maNguoiDuocChi = cs.getInt(cs.getColumnIndex(KEY_ID));
                    String tenNguoiDuocChi = cs.getString(cs.getColumnIndex(KEY_NAME));

                    receiver = new Receiver(maNguoiDuocChi, tenNguoiDuocChi);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return receiver;
    }

    public Receiver getReceiverByName(String name) {
        Receiver receiver = new Receiver();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            Cursor cs = db.query(true, DB_TABLE, new String[] {KEY_ID, KEY_NAME},
                    KEY_NAME+ "=" +name, null, null, null, null, null);
            if (cs.moveToFirst()) {
                int maNguoiDuocChi = cs.getInt(cs.getColumnIndex(KEY_ID));
                String tenNguoiDuocChi = cs.getString(cs.getColumnIndex(KEY_NAME));

                receiver = new Receiver(maNguoiDuocChi, tenNguoiDuocChi);
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return receiver;
    }

    public long addReceiver(Receiver receiver) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, receiver.getReceiverName());
            return db.insert(DB_TABLE, null, contentValues);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }
}
