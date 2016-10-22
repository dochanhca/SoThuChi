package com.example.anhlamrduc.sothuchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Borrower;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.item.Lender;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.SumIncomeByReceiveItem;
import com.example.anhlamrduc.sothuchi.utils.Constant;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class IncomeController extends SQLiteAssetHelper {
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

    public IncomeController(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }

    public ArrayList<Income> getAllIncome() {

        ArrayList<Income> incomes = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT MaThuTien, SoTien, Ngay, MoTa, TaiKhoan.MaTaiKhoan, TenTaiKhoan, " +
                    "MucThu.MaMucThu, TenMucThu, SuKien.MaSuKien, TenSuKien \n" +
                    " FROM ThuTien, TaiKhoan, MucThu, SuKien\n" +
                    "                 WHERE ThuTien.MaTaiKhoan = TaiKhoan.MaTaiKhoan \n" +
                    "                   AND ThuTien.MaMucThu = MucThu.MaMucThu \n" +
                    "                    AND ThuTien.MaSuKien = SuKien.MaSuKien";

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {
                    Account account = new Account();
                    account.setAccountID(cs.getInt(cs.getColumnIndex("MaTaiKhoan")));
                    account.setAccountName(cs.getString(cs.getColumnIndex("TenTaiKhoan")));

                    ReceiveItem receiveItem = new ReceiveItem();
                    receiveItem.setReceiveItemID(cs.getInt(cs.getColumnIndex("MaMucThu")));
                    receiveItem.setReceiveItemName(cs.getString(cs.getColumnIndex("TenMucThu")));

                    Borrower borrower = new Borrower();

                    Lender lender = new Lender();

                    Event event = new Event();
                    event.setEventID(cs.getInt(cs.getColumnIndex("MaSuKien")));
                    event.setEventName(cs.getString(cs.getColumnIndex("TenSuKien")));



                    int receiveMoneyID = cs.getInt(cs.getColumnIndex(KEY_ID));
                    double amount = cs.getDouble(cs.getColumnIndex(KEY_MONEY));
                    String description = cs.getString(cs.getColumnIndex(KEY_NOTE));
                    Date day = Calendar.getInstance().getTime();
                    try {
                        day = Constant.GMT_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(KEY_DATE)));
                    } catch (Exception e) {
                        e.getMessage();
                    }
                    Income income = new Income(receiveMoneyID, amount, day, description,
                            account, receiveItem, lender, borrower, event);
                    incomes.add(income);

                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return incomes;
    }


    public long addIncome(Income income) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MONEY, income.getAmount());
            contentValues.put(KEY_DATE, income.getReceiveDate().toString());
            contentValues.put(KEY_NOTE, income.getDescription());
            contentValues.put(KEY_ACCOUNT_ID, income.getAccount().getAccountID());
            contentValues.put(KEY_BORROWER_ID, income.getBorrower().getBorrowerID());
            contentValues.put(KEY_LENDER_ID, income.getLender().getLenderID());
            contentValues.put(KEY_RECEIVE_ITEM_ID, income.getReceiveItem().getReceiveItemID());
            contentValues.put(KEY_EVENT_ID, income.getEvent().getEventID());
            return db.insert(DB_TABLE_RECEIVE_MONEY, null, contentValues);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }

    public long updateIncome(Income income) {
        try {
            SQLiteDatabase db = getReadableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_MONEY, income.getAmount());
            contentValues.put(KEY_DATE, income.getReceiveDate().toString());
            contentValues.put(KEY_NOTE, income.getDescription());
            contentValues.put(KEY_ACCOUNT_ID, income.getAccount().getAccountID());
            contentValues.put(KEY_BORROWER_ID, income.getBorrower().getBorrowerID());
            contentValues.put(KEY_LENDER_ID, income.getLender().getLenderID());
            contentValues.put(KEY_RECEIVE_ITEM_ID, income.getReceiveItem().getReceiveItemID());
            contentValues.put(KEY_EVENT_ID, income.getEvent().getEventID());
            return db.update(DB_TABLE_RECEIVE_MONEY, contentValues, KEY_ID + "=" + income.getReceiveMoneyID(), null);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }

    public ArrayList<SumIncomeByReceiveItem> getSumIncomeByReceiveItem() {
        ArrayList<SumIncomeByReceiveItem> listSumOfReceive = new ArrayList<>();

        try {
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT Sum(SoTien) AS Tong, Ngay, MucThu.MaMucThu, MucThu.TenMucThu\n" +
                    "FROM ThuTien t\n" +
                    "JOIN MucThu\n" +
                    "ON ThuTien.MaMucThu= MucThu.MaMucThu\n" +
                    "GROUP BY MucThu.TenMucThu";
            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {

                    SumIncomeByReceiveItem sumIncomeByReceiveItem = new SumIncomeByReceiveItem();
                    sumIncomeByReceiveItem.setId(cs.getInt(cs.getColumnIndex("MaMucThu")));
                    sumIncomeByReceiveItem.setName(cs.getString(cs.getColumnIndex("MucThu.TenMucThu")));
                    sumIncomeByReceiveItem.setSum(cs.getDouble(cs.getColumnIndex("Tong")));
                    Date date = Constant.GMT_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(KEY_DATE)));
                    sumIncomeByReceiveItem.setReceiveDate(date);
                    listSumOfReceive.add(sumIncomeByReceiveItem);
                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return listSumOfReceive;
    }


}
