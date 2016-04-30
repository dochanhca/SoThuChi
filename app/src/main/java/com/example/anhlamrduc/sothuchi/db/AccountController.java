package com.example.anhlamrduc.sothuchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.AccountType;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 20-Mar-16.
 */
public class AccountController extends SQLiteAssetHelper {

    private static final String KEY_ID = "MaTaiKhoan";
    private static final String KEY_NAME = "TenTaiKhoan";
    private static final String KEY_FIST_MONEY = "SoTienBanDau";
    private static final String KEY_CURRENT_MONEY = "SoTienHienCo";
    private static final String KEY_NOTE = "GhiChu";
    private static final String KEY_TYPE_ACCOUNT_ID = "MaLoai";
    private static final String KEY_HINH_ANH = "HinhAnh";
    private static final String DB_TABLE = "TaiKhoan";
    private static final String DB_TABLE_LOAITK = "LoaiTaiKhoan";

    private static final String DB_NAME = "misa.sqlite";
    private static final int DB_VERSION = 1;

    public AccountController(Context con) {
        super(con, DB_NAME, null, DB_VERSION);
    }

    /**
     * @return list Account
     */
    public ArrayList<Account> getListAccount() {

        ArrayList<Account> arrAccount = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT " + KEY_ID + ", " + KEY_NAME + ", " + KEY_FIST_MONEY + ", "
                    + KEY_CURRENT_MONEY + ", " + KEY_NOTE + ", " + KEY_TYPE_ACCOUNT_ID
                    + " FROM " + DB_TABLE;

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {
                    AccountType accountType = new AccountType();
                    accountType.setTypeID(cs.getInt(cs.getColumnIndex(KEY_TYPE_ACCOUNT_ID)));
                    int accountID = cs.getInt(cs.getColumnIndex(KEY_TYPE_ACCOUNT_ID));
                    String accountName = cs.getString(cs.getColumnIndex(KEY_NAME));
                    double firstMoney = cs.getDouble(cs.getColumnIndex(KEY_FIST_MONEY));
                    double currentMoney = cs.getDouble(cs.getColumnIndex(KEY_CURRENT_MONEY));
                    String note = cs.getString(cs.getColumnIndex(KEY_NOTE));
                    Account account = new Account(accountID, accountName, firstMoney, currentMoney,
                            note, accountType);
                    arrAccount.add(account);

                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return arrAccount;
    }

    /**
     * get HinhAnh column From Database
     *
     * @param typeAccountID
     * @return
     */
    public String getImageName(int typeAccountID) {

        String imageName = null;
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT " + KEY_HINH_ANH + " FROM " + DB_TABLE_LOAITK + " WHERE MaLoai = " + typeAccountID;

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                imageName = cs.getString(cs.getColumnIndex(KEY_HINH_ANH));
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return imageName;

    }

    /**
     * Calculate sum of SoTienHienCo
     *
     * @return sum_money
     */
    public double getSumMoney() {
        double sum = 0;
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT SUM(" + KEY_CURRENT_MONEY + ") AS Sum_MoNey FROM " + DB_TABLE;

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                sum = cs.getDouble(cs.getColumnIndex("Sum_MoNey"));
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return sum;
    }

    public long addAccount(Account account) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, account.getAccountName());
            contentValues.put(KEY_FIST_MONEY, account.getFirstMoney());
            contentValues.put(KEY_CURRENT_MONEY, account.getCurrentMoney());
            contentValues.put(KEY_NOTE, account.getNote());
            contentValues.put(KEY_TYPE_ACCOUNT_ID, account.getAccountType().getTypeID());
            return db.insert(DB_TABLE, null, contentValues);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }

    public long updateAccount(Account account) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_NAME, account.getAccountName());
            contentValues.put(KEY_FIST_MONEY, account.getFirstMoney());
            contentValues.put(KEY_CURRENT_MONEY, account.getCurrentMoney());
            contentValues.put(KEY_NOTE, account.getNote());
            contentValues.put(KEY_TYPE_ACCOUNT_ID, account.getAccountType().getTypeID());
            return db.update(DB_TABLE, contentValues, KEY_ID + "=" + account.getAccountID(), null);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }
}
