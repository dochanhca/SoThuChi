package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.PayController;
import com.example.anhlamrduc.sothuchi.item.Pay;

import java.util.Objects;

/**
 * Created by AnhlaMrDuc on 25-Apr-16.
 */
public class DBInsertPay extends AsyncTask<Pay, Objects, Boolean> {
    private Context context;

    public DBInsertPay(Context context) {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Pay... params) {
        PayController db = new PayController(context);
        db.addPay(params[0]);
        return Boolean.TRUE;
    }
}
