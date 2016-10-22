package com.example.anhlamrduc.sothuchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Borrower;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Lender;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.example.anhlamrduc.sothuchi.item.SumPayBySpendingItem;
import com.example.anhlamrduc.sothuchi.utils.Constant;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

    public PayController(Context con) {
        super(con, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }

    public ArrayList<Pay> getAllPay() {

        ArrayList<Pay> listPay = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT MaChiTien, SoTien, Ngay, MoTa, TaiKhoan.MaTaiKhoan, TenTaiKhoan, MucChi.MaMucChi, " +
                    "TenMucChi, MucCha, Ngay, NguoiDuocChi.MaNguoiDuocChi , TenNguoiDuocChi, SuKien.MaSuKien, TenSuKien \n" +
                    "FROM ChiTien, TaiKhoan, MucChi, NguoiDuocChi, SuKien \n" +
                    "WHERE ChiTien.MaTaiKhoan = TaiKhoan.MaTaiKhoan \n" +
                    "AND ChiTien.MaMucChi = MucChi.MaMucChi \n" +
                    "AND ChiTien.MaNguoiDuocChi = NguoiDuocChi.MaNguoiDuocChi \n" +
                    "AND ChiTien.MaSuKien = SuKien.MaSuKien";

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {
                    Account account = new Account();
                    account.setAccountID(cs.getInt(cs.getColumnIndex("MaTaiKhoan")));
                    account.setAccountName(cs.getString(cs.getColumnIndex("TenTaiKhoan")));

                    SpendingItem spendingItem = new SpendingItem();
                    spendingItem.setSpendingItemID(cs.getInt(cs.getColumnIndex("MaMucChi")));
                    spendingItem.setSpendingItemName(cs.getString(cs.getColumnIndex("TenMucChi")));
                    spendingItem.setParentItem(cs.getString(cs.getColumnIndex("MucCha")));

                    Lender lender = new Lender();
                    Borrower borrower = new Borrower();

                    Receiver receiver = new Receiver();
                    receiver.setReceiverID(cs.getInt(cs.getColumnIndex("MaNguoiDuocChi")));
                    receiver.setReceiverName(cs.getString(cs.getColumnIndex("TenNguoiDuocChi")));

                    Event event = new Event();
                    event.setEventID(cs.getInt(cs.getColumnIndex("MaSuKien")));
                    event.setEventName(cs.getString(cs.getColumnIndex("TenSuKien")));

                    int payID = cs.getInt(cs.getColumnIndex(KEY_ID));
                    double amount = cs.getDouble(cs.getColumnIndex(KEY_MONEY));
                    String description = cs.getString(cs.getColumnIndex(KEY_NOTE));
//                    String date = Constant.VN_DATE_FORMAT.format();
                    Date day = Calendar.getInstance().getTime();
                    try {
                        day = Constant.GMT_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(KEY_DATE)));
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Pay pay = new Pay(payID, amount, day, description, account, spendingItem,
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
            contentValues.put(KEY_DATE, pay.getPayDate().toString());
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
            contentValues.put(KEY_DATE, pay.getPayDate().toString());
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

    public ArrayList<SumPayBySpendingItem> getSumBySpendingItem() {
        ArrayList<SumPayBySpendingItem> listSumOfPay = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT Sum(SoTien) AS Tong, Ngay, MucChi.MaMucChi as MaMucChi, MucChi.TenMucChi as TenMucChi \n" +
                    "FROM ChiTien\n" +
                    "JOIN MucChi\n" +
                    "ON ChiTien.MaMucChi = MucChi.MaMucChi\n" +
                    "GROUP BY MucChi.MaMucChi\n" +
                    "HAVING MucChi.MaMucChi IN (SELECT MaMucChi FROM MucChi WHERE MucCha ='')\n";
            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {

                    SumPayBySpendingItem sumPayBySpendingItem = new SumPayBySpendingItem();
                    sumPayBySpendingItem.setId(cs.getInt(cs.getColumnIndex("MaMucChi")));
                    sumPayBySpendingItem.setName(cs.getString(cs.getColumnIndex("TenMucChi")));
                    sumPayBySpendingItem.setSum(cs.getDouble(cs.getColumnIndex("Tong")));
                    Date date = Constant.GMT_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(KEY_DATE)));
                    sumPayBySpendingItem.setPayDate(date);
                    listSumOfPay.add(sumPayBySpendingItem);
                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return listSumOfPay;
    }

}
