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

    public AccountTypeController(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
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
                    int id = cs.getInt(cs.getColumnIndex(KEY_ID));
                    String name = cs.getString(cs.getColumnIndex(KEY_NAME));
                    String image = cs.getString(cs.getColumnIndex(KEY_IMAGE));

                    AccountType accountType = new AccountType(id, name, image);
                    arrAccountType.add(accountType);

                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return arrAccountType;
    }


}
