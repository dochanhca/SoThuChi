package com.example.anhlamrduc.sothuchi.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 01-Apr-16.
 */
public class SpendingController extends SQLiteAssetHelper {

    private static final String KEY_ID = "MaMucChi";
    private static final String KEY_NAME = "TenMucChi";
    private static final String KEY_NOTE = "GhiChu";
    private static final String KEY_PARENT_NAME = "MucCha";

    private static final String DB_TABLE = "MucChi";

    private static final String DB_NAME = "misa.sqlite";
    private static final int DB_VERSION = 1;

    public SpendingController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * @return list of SpendingItem cha
     */
    public ArrayList<SpendingItem> getListSpending() {

        ArrayList<SpendingItem> arrSpending = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
//            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            Cursor cs = db.rawQuery("SELECT * FROM " + DB_TABLE, null);
            if (cs.moveToFirst()) {
                do {
                    SpendingItem spending = new SpendingItem();
                    spending.setSpendingItemID(cs.getInt(cs.getColumnIndex(KEY_ID)));
                    spending.setSpendingItemName(cs.getString(cs.getColumnIndex(KEY_NAME)));
                    spending.setNote(cs.getString(cs.getColumnIndex(KEY_NOTE)));
                    spending.setParentItem(cs.getString(cs.getColumnIndex(KEY_PARENT_NAME)));
                    arrSpending.add(spending);

                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return arrSpending;
    }

    /**
     * @return list of SpendingItem child
     */
//    public ArrayList<SpendingItem> getListSpendingChild(String parentName) {
//
//        ArrayList<SpendingItem> arrSpending = new ArrayList<>();
//        try {
//            //open connect to database
//            SQLiteDatabase db = getReadableDatabase();
////            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//
//            Cursor cs = db.rawQuery("SELECT " + KEY_ID + ", " + KEY_NAME + ", "
//                    + KEY_NOTE+ ", "+KEY_PARENT_NAME
//                    + " FROM " + DB_TABLE + " WHERE " + KEY_PARENT_NAME + " = '"+parentName+"'", null);
//            if (cs.moveToFirst()) {
//                do {
//                    SpendingItem spending = new SpendingItem(cs.getInt(1), cs.getString(2), cs.getString(3),
//                            cs.getString(4));
//                    arrSpending.add(spending);
//
//                } while (cs.moveToNext());
//            }
//        } catch (Exception e) {
//            e.getMessage();
//        }
//        return arrSpending;
//    }
}
