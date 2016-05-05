package com.example.anhlamrduc.sothuchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Borrower;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Lender;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.ReceiveMoney;
import com.example.anhlamrduc.sothuchi.utils.Constant;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class ReceiveMoneyController extends SQLiteAssetHelper {
    private static final String KEY_ID = "MaThuTien";
    private static final String KEY_ACCOUNT_ID = "MaTaiKhoan";
    private static final String KEY_RECEIVE_ITEM_ID = "MaMucThu";
    private static final String KEY_LENDER_ID = "MaNguoiChoVay";
    private static final String KEY_BORROWER_ID = "MaNguoiVay";
    private static final String KEY_EVENT_ID = "MaSuKien";
    private static final String KEY_MONEY = "SoTien";
    private static final String KEY_NOTE = "MoTa";
    private static final String KEY_DATE = "Ngay";

    private static final String DB_TABLE_RECEIVE_MONEY = "ThuTien";

    public ReceiveMoneyController(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }

    public ArrayList<ReceiveMoney> getAllReceiveMoney() {

        ArrayList<ReceiveMoney> receiveMoneys = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT MaThuTien, SoTien, Ngay, MoTa, TenTaiKhoan, TenMucThu, TenNguoiChoVay, TenNguoiVay, TenSuKien \n" +
                    "FROM ThuTien tt, TaiKhoan tk, MucThu mt, NguoiChoVay ncv, NguoiVay nv, SuKien sk\n" +
                    "                  WHERE tt.MaTaiKhoan = tk.MaTaiKhoan\n" +
                    "                    AND tt.MaMucThu = mt.MaMucThu\n" +
                    "                    AND  tt.MaNguoiChoVay = ncv.MaNguoiChoVay \n" +
                    "                    AND  tt.MaNguoiVay = nv.MaNguoiVay\n" +
                    "                    AND tt.MaSuKien = sk.MaSuKien";

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {
                    Account account = new Account();
                    account.setAccountName(cs.getString(cs.getColumnIndex("TenTaiKhoan")));

                    ReceiveItem receiveItem = new ReceiveItem();
                    receiveItem.setReceiveItemName(cs.getString(cs.getColumnIndex("TenMucThu")));

                    Borrower borrower = new Borrower();
                    borrower.setBorrowerName(cs.getString(cs.getColumnIndex("TenNguoiVay")));

                    Lender lender = new Lender();
                    lender.setLenderName(cs.getString(cs.getColumnIndex("TenNguoiChoVay")));

                    Event event = new Event();
                    event.setEventName(cs.getString(cs.getColumnIndex("TenSuKien")));

                    int receiveMoneyID = cs.getInt(cs.getColumnIndex(KEY_ID));
                    double amount = cs.getDouble(cs.getColumnIndex(KEY_MONEY));
                    String description = cs.getString(cs.getColumnIndex(KEY_NOTE));
                    Date day = Constant.VN_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(KEY_DATE)));
                    ReceiveMoney receiveMoney = new ReceiveMoney(receiveMoneyID, amount, day, description,
                            account, receiveItem, lender, borrower, event);
                    receiveMoneys.add(receiveMoney);

                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return receiveMoneys;
    }


    public long addReceiveMoney(ReceiveMoney receiveMoney) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MONEY, receiveMoney.getAmount());
            contentValues.put(KEY_DATE, receiveMoney.getReceiveDate().toString());
            contentValues.put(KEY_NOTE, receiveMoney.getDescription());
            contentValues.put(KEY_ACCOUNT_ID, receiveMoney.getAccount().getAccountID());
            contentValues.put(KEY_BORROWER_ID, receiveMoney.getBorrower().getBorrowerID());
            contentValues.put(KEY_LENDER_ID, receiveMoney.getLender().getLenderID());
            contentValues.put(KEY_RECEIVE_ITEM_ID, receiveMoney.getReceiveItem().getReceiveItemID());
            contentValues.put(KEY_EVENT_ID, receiveMoney.getEvent().getEventID());
            return db.insert(DB_TABLE_RECEIVE_MONEY, null, contentValues);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }

    public long updateReceiveMoney(ReceiveMoney receiveMoney) {
        try {
            SQLiteDatabase db = getReadableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MONEY, receiveMoney.getAmount());
            contentValues.put(KEY_DATE, receiveMoney.getReceiveDate().toString());
            contentValues.put(KEY_NOTE, receiveMoney.getDescription());
            contentValues.put(KEY_ACCOUNT_ID, receiveMoney.getAccount().getAccountID());
            contentValues.put(KEY_BORROWER_ID, receiveMoney.getBorrower().getBorrowerID());
            contentValues.put(KEY_LENDER_ID, receiveMoney.getLender().getLenderID());
            contentValues.put(KEY_RECEIVE_ITEM_ID, receiveMoney.getReceiveItem().getReceiveItemID());
            contentValues.put(KEY_EVENT_ID, receiveMoney.getEvent().getEventID());
            return db.update(DB_TABLE_RECEIVE_MONEY, contentValues, KEY_ID + "=" + receiveMoney.getReceiveMoneyID(), null);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }


}
