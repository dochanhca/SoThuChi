package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.PayController;
import com.example.anhlamrduc.sothuchi.item.Pay;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 01-May-16.
 */
public class DBGetAllPay extends AsyncTask<Void, Object, ArrayList<Pay>> {
    private Context context;

    public DBGetAllPay(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Pay> doInBackground(Void... params) {
        PayController payController = new PayController(context);
        return payController.getAllPay();
    }
}
