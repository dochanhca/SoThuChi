package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.ReceiveMoneyController;
import com.example.anhlamrduc.sothuchi.item.ReceiveMoney;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class DBInsertAReceiveMoney extends AsyncTask<ReceiveMoney, Object, Long> {
    private Context context;

    public DBInsertAReceiveMoney(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(ReceiveMoney... params) {
        ReceiveMoneyController db = new ReceiveMoneyController(context);
        return db.addReceiveMoney(params[0]);
    }
}
