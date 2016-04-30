package com.example.anhlamrduc.sothuchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Lender;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.Borrower;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 23-Apr-16.
 */
public class PayController extends SQLiteAssetHelper {

    private static final String KEY_ID = "MaChiTien";
    private static final String KEY_ACCOUNT_ID = "MaTaiKhoan";
    private static final String KEY_SPENDING_ID = "MaMucChi";
    private static final String KEY_LENDER_ID = "MaNguoiChoVay";
    private static final String KEY_BORROWER_ID = "MaNguoiVay";
    private static final String KEY_EVENT_ID = "MaSuKien";
    private static final String KEY_RECEIVER_ID = "MaNguoiDuocChi";
    private static final String KEY_MONEY = "SoTien";
    private static final String KEY_NOTE = "MoTa";
    private static final String KEY_DATE = "Ngay";

    private static final String DB_TABLE_PAY = "ChiTien";
    private static final String DB_TABLE_ACCOUNT = "TaiKhoanr";
    private static final String DB_TABLE_SPENDING = "SpendingItem";
    private static final String DB_TABLE_LENDER = "Lender";
    private static final String DB_TABLE_BORROWER = "Borrower";
    private static final String DB_TABLE_RECEIVER_ = "Receiver";
    private static final String DB_TABLE_EVENT = "Event";

    private static final String DB_NAME = "misa.sqlite";
    private static final int DB_VERSION = 1;

    public PayController(Context con) {
        super(con, DB_NAME, null, DB_VERSION);
    }

    /**
     * @return list Account
     */
    public ArrayList<Pay> getPay() {

        ArrayList<Pay> listPay = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT MaChiTien, SoTien, Ngay, MoTa, TenTaiKhoan, TenMucChi, TenNguoiChoVay, TenNguoiVay, TenNguoiDuocChi, TenSuKien \n" +
                    "FROM Pay ct, Account tk, SpendingItem mc, Lender ncv, Borrower nv, Receiver ndc, Event sk\n" +
                    "WHERE ct.MaTaiKhoan = tk.MaTaiKhoan " +
                    "AND ct.MaMucChi = mc.MaMucChi " +
                    "AND  ct.MaNguoiChoVay = ncv.MaNguoiChoVay " +
                    "AND  ct.MaNguoiVay = nv.MaNguoiVay " +
                    "AND ct.MaNguoiDuocChi = ndc.MaNguoiDuocChi " +
                    "AND ct.MaSuKien = sk.MaSuKien";

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {
                    Account taikhoan = new Account();
                    taikhoan.setAccountName(cs.getString(cs.getColumnIndex("TenTaiKhoan")));

                    SpendingItem spendingItem = new SpendingItem();
                    spendingItem.setSpendingItemName(cs.getString(cs.getColumnIndex("TenMucChi")));

                    Borrower borrower = new Borrower();
                    borrower.setBorrowerName(cs.getString(cs.getColumnIndex("TenNguoiVay")));

                    Lender lender = new Lender();
                    lender.setLenderName(cs.getString(cs.getColumnIndex("TenNguoiChoVay")));

                    Receiver receiver = new Receiver();
                    receiver.setReceiverName(cs.getString(cs.getColumnIndex("TenNguoiDuocChi")));

                    Event event = new Event();
                    event.setEventName(cs.getString(cs.getColumnIndex("TenSuKien")));

                    int maChiTien = cs.getInt(cs.getColumnIndex(KEY_ID));
                    double soTien = cs.getDouble(cs.getColumnIndex(KEY_MONEY));
                    String mota = cs.getString(cs.getColumnIndex(KEY_NOTE));
                    String ngay = cs.getString(cs.getColumnIndex(KEY_DATE));

                    Pay pay = new Pay(maChiTien, soTien, ngay, mota, taikhoan, spendingItem,
                            lender, borrower, receiver, event);
                    listPay.add(pay);

                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return listPay;
    }


    public long addPay(Pay pay) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MONEY, pay.getMoney());
            contentValues.put(KEY_DATE, pay.getPayDate());
            contentValues.put(KEY_NOTE, pay.getDescription());
            contentValues.put(KEY_ACCOUNT_ID, pay.getAccount().getAccountID());
            contentValues.put(KEY_BORROWER_ID, pay.getBorrower().getBorrowerID());
            contentValues.put(KEY_LENDER_ID, pay.getLender().getLenderID());
            contentValues.put(KEY_RECEIVER_ID, pay.getReceiver().getReceiverID());
            contentValues.put(KEY_SPENDING_ID, pay.getSpendingItem().getSpendingItemID());
            contentValues.put(KEY_EVENT_ID, pay.getEvent().getEventID());
            return db.insert(DB_TABLE_PAY, null, contentValues);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }

    public long updatePay(Pay pay) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MONEY, pay.getMoney());
            contentValues.put(KEY_DATE, pay.getPayDate());
            contentValues.put(KEY_NOTE, pay.getDescription());
            contentValues.put(KEY_ACCOUNT_ID, pay.getAccount().getAccountID());
            contentValues.put(KEY_BORROWER_ID, pay.getBorrower().getBorrowerID());
            contentValues.put(KEY_LENDER_ID, pay.getLender().getLenderID());
            contentValues.put(KEY_RECEIVER_ID, pay.getReceiver().getReceiverID());
            contentValues.put(KEY_SPENDING_ID, pay.getSpendingItem().getSpendingItemID());
            contentValues.put(KEY_EVENT_ID, pay.getEvent().getEventID());
            return db.update(DB_TABLE_PAY, contentValues, KEY_ID + "=" + pay.getPayID(), null);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }
}
