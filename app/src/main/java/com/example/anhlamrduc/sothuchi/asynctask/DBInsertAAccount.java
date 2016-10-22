package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.AccountController;
import com.example.anhlamrduc.sothuchi.item.Account;

/**
 * Created by AnhlaMrDuc on 05-May-16.
 */
public class DBInsertAAccount extends AsyncTask<Account, Object, Long> {
    private Context context;

    public DBInsertAAccount(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Account... params) {
        AccountController db = new AccountController(context);
        return db.addAccount(params[0]);
    }
}
