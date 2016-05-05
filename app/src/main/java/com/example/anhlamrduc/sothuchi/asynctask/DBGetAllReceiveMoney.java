package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.ReceiveMoneyController;
import com.example.anhlamrduc.sothuchi.item.ReceiveMoney;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class DBGetAllReceiveMoney extends AsyncTask<Void, Object, ArrayList<ReceiveMoney>> {
    private Context context;

    public DBGetAllReceiveMoney(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<ReceiveMoney> doInBackground(Void... params) {
        ReceiveMoneyController db = new ReceiveMoneyController(context);
        return db.getAllReceiveMoney();
    }
}
