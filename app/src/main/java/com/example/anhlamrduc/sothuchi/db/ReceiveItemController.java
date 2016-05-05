package com.example.anhlamrduc.sothuchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class ReceiveItemController extends SQLiteAssetHelper {
    private static final String KEY_ID = "MaMucThu";
    private static final String KEY_NAME = "TenMucThu";
    private static final String KEY_NOTE = "GhiChu";

    private static final String DB_TABLE = "MucThu";

    public ReceiveItemController(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }

    public long addReceiveItem(ReceiveItem item) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, item.getReceiveItemName());
            contentValues.put(KEY_NOTE, item.getDescription());
            return db.insert(DB_TABLE, null, contentValues);
        } catch (SQLException e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }

    public ArrayList<ReceiveItem> getListReceiveItem() {

        ArrayList<ReceiveItem> receiveItems = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
//            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            Cursor cs = db.rawQuery("SELECT * FROM " + DB_TABLE, null);
            if (cs.moveToFirst()) {
                do {
                    ReceiveItem receiveItem = new ReceiveItem();
                    receiveItem.setReceiveItemID(cs.getInt(cs.getColumnIndex(KEY_ID)));
                    receiveItem.setReceiveItemName(cs.getString(cs.getColumnIndex(KEY_NAME)));
                    receiveItem.setDescription(cs.getString(cs.getColumnIndex(KEY_NOTE)));
                    receiveItems.add(receiveItem);

                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return receiveItems;
    }

}
