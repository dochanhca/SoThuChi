package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.AccountTypeController;
import com.example.anhlamrduc.sothuchi.item.AccountType;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 04-May-16.
 */
public class DBGetAllAccountType extends AsyncTask<Void, Object, ArrayList<AccountType>> {
    private Context context;

    public DBGetAllAccountType(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<AccountType> doInBackground(Void... params) {
        AccountTypeController db = new AccountTypeController(context);
        return db.getListAccountType();
    }
}
