package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.AccountController;
import com.example.anhlamrduc.sothuchi.item.Account;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 03-May-16.
 */
public class DBGetAllAccount extends AsyncTask<Void, Object, ArrayList<Account>> {
    private Context context;

    public DBGetAllAccount(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Account> doInBackground(Void... params) {
        AccountController db = new AccountController(context);
        return db.getListAccount();
    }
}
