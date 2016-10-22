package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.AccountController;
import com.example.anhlamrduc.sothuchi.item.Account;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class DBUpdateAccount extends AsyncTask<Account, Void, Long> {
    private Context context;

    public DBUpdateAccount(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Account... params) {
        AccountController db = new AccountController(context);
        return db.updateAccount(params[0]);
    }
}
