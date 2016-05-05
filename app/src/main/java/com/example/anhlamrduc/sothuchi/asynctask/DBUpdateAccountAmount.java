package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.AccountController;
import com.example.anhlamrduc.sothuchi.item.Account;

/**
 * Created by AnhlaMrDuc on 01-May-16.
 */
public class DBUpdateAccountAmount extends AsyncTask<Account, Object, Boolean> {
    private Context context;

    public DBUpdateAccountAmount(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Account... params) {
        AccountController db = new AccountController(context);
        return db.updateAccountAmount(params[0]);
    }
}
