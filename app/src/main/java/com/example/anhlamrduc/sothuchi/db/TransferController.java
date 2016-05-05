package com.example.anhlamrduc.sothuchi.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Transfer;
import com.example.anhlamrduc.sothuchi.utils.Constant;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class TransferController extends SQLiteAssetHelper {

    private static final String KEY_ID = "MaChuyenKhoan";
    private static final String KEY_FROM_ACCOUNT_ID = "MaTaiKhoanGui";
    private static final String KEY_TO_ACCOUNT_ID = "MaTaiKhoanNhan";
    private static final String KEY_MONEY = "SoTien";
    private static final String KEY_NOTE = "MoTa";
    private static final String KEY_DATE = "Ngay";
    private static final String KEY_TRANSFER_FEE = "PhiChuyenKhoan";

    private static final String DB_TABLE_PAY = "ChuyenKhoan";

    public TransferController(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }

    public long addTransfer(Transfer transfer) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TRANSFER_FEE, transfer.getTransferFee());
            contentValues.put(KEY_MONEY, transfer.getAmount());
            contentValues.put(KEY_DATE, transfer.getTransferDate().toString());
            contentValues.put(KEY_NOTE, transfer.getDescription());
            contentValues.put(KEY_FROM_ACCOUNT_ID, transfer.getFromAccount().getAccountID());
            contentValues.put(KEY_TO_ACCOUNT_ID, transfer.getToAccount().getAccountID());
            return db.insert(DB_TABLE_PAY, null, contentValues);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }

    public long updateTransfer(Transfer transfer) {
        try {
            SQLiteDatabase db = getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_TRANSFER_FEE, transfer.getTransferFee());
            contentValues.put(KEY_MONEY, transfer.getAmount());
            contentValues.put(KEY_DATE, transfer.getTransferDate().toString());
            contentValues.put(KEY_NOTE, transfer.getDescription());
            contentValues.put(KEY_FROM_ACCOUNT_ID, transfer.getFromAccount().getAccountID());
            contentValues.put(KEY_TO_ACCOUNT_ID, transfer.getToAccount().getAccountID());
            return db.update(DB_TABLE_PAY, contentValues, KEY_ID + "=" + transfer.getTransferID(), null);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            close();
        }
        return 0;
    }

    public ArrayList<Transfer> getAllTransfer() {

        ArrayList<Transfer> transfers = new ArrayList<>();
        try {
            //open connect to database
            SQLiteDatabase db = getReadableDatabase();
            String sql = "SELECT MaChuyenKhoan, SoTien, Ngay, Mota, PhiChuyenKhoan, " +
                    "tkg.TenTaiKhoan as TenTaiKhoanGui, tkn.TenTaiKhoan as TenTaiKhoanNhan " +
                    "FROM ChuyenKhoan as ck \n" +
                    "INNER JOIN TaiKhoan as tkg ON\n" +
                    "ck.MaTaiKhoanGui = tkg.MaTaiKhoan\n" +
                    " INNER JOIN TaiKhoan as tkn ON\n" +
                    "ck.MaTaiKhoanNhan = tkn.MaTaiKhoan";

            Cursor cs = db.rawQuery(sql, null);
            if (cs.moveToFirst()) {
                do {
                    Account fromAccount = new Account();
                    fromAccount.setAccountName(cs.getString(cs.getColumnIndex("TenTaiKhoanGui")));

                    Account toAccount = new Account();
                    toAccount.setAccountName(cs.getString(cs.getColumnIndex("TenTaiKhoanNhan")));


                    int transferID = cs.getInt(cs.getColumnIndex(KEY_ID));
                    double amount = cs.getDouble(cs.getColumnIndex(KEY_MONEY));
                    double transferFee = cs.getDouble(cs.getColumnIndex(KEY_TRANSFER_FEE));
                    String description = cs.getString(cs.getColumnIndex(KEY_NOTE));
                    Date day = Constant.VN_DATE_FORMAT.parse(cs.getString(cs.getColumnIndex(KEY_DATE)));
                    Transfer transfer = new Transfer(transferID, amount, day, description, transferFee, fromAccount, toAccount );
                    transfers.add(transfer);

                } while (cs.moveToNext());
            }
        } catch (Exception e) {
            e.getMessage();
        } finally {
            //close connect to database
            close();
        }
        return transfers;
    }
}
