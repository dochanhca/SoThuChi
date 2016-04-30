package com.example.anhlamrduc.sothuchi.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.AccountType;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 20-Mar-16.
 */
public class AccountTypeController extends SQLiteAssetHelper {

    private static final String KEY_ID = "MaLoai";
    private static final String KEY_NAME = "TenLoai";
    private static final String KEY_IMAGE = "HinhAnh";
    private static final String DB_TABLE = "LoaiTaiKhoan";

    private static final String DB_NAME = "misa.sqlite";
    private static final int DB_VERSION = 1;

    public AccountTypeController(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * @return list of AccountType
     */
    public ArrayList<AccountType> getListAccountType() {

        ArrayList<AccountType> arrAccountType = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
//            SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

            Cursor cs = db.rawQuery("SELECT " + KEY_ID + ", " + KEY_NAME + ", " + KEY_IMAGE
                    + " FROM " + DB_TABLE, null);
            if (cs.moveToFirst()) {
                do {
                    AccountType accountType = new AccountType(cs.getInt(1), cs.getString(2), cs.getString(3));
                    arrAccountType.add(accountType);

                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return arrAccountType;
    }


}
