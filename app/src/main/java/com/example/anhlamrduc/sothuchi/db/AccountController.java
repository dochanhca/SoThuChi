package com.example.anhlamrduc.sothuchi.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.LoaiTaiKhoan;
import com.example.anhlamrduc.sothuchi.item.TaiKhoan;
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
     * @return list TaiKhoan
     */
    public ArrayList<TaiKhoan> getListAccount() {

        ArrayList<TaiKhoan> arrAccount = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT " + KEY_ID + ", " + KEY_NAME + ", " + KEY_FIST_MONEY + ", "
                    + KEY_CURRENT_MONEY + ", " + KEY_NOTE + ", " + KEY_TYPE_ACCOUNT_ID
                    + " FROM " + DB_TABLE;

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {
                    LoaiTaiKhoan loaiTaiKhoan = new LoaiTaiKhoan();
                    loaiTaiKhoan.setMaLoai(cs.getColumnIndex(KEY_TYPE_ACCOUNT_ID));
                    int maTaiKhoan = cs.getInt(cs.getColumnIndex(KEY_TYPE_ACCOUNT_ID));
                    String tenTaiKhoan = cs.getString(cs.getColumnIndex(KEY_NAME));
                    double soTienBanDau = cs.getDouble(cs.getColumnIndex(KEY_FIST_MONEY));
                    double soTienHienCo = cs.getDouble(cs.getColumnIndex(KEY_CURRENT_MONEY));
                    String ghiChu = cs.getString(cs.getColumnIndex(KEY_NOTE));
                    TaiKhoan account = new TaiKhoan(maTaiKhoan, tenTaiKhoan, soTienBanDau, soTienHienCo,
                            ghiChu, loaiTaiKhoan);
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
     * @param typeAccountID
     * @return
     */
    public String getImageName(int typeAccountID) {

        String imageName = null;
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT " +KEY_HINH_ANH+ " FROM "+DB_TABLE_LOAITK+" WHERE MaLoai = " +typeAccountID;

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
}
