package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.PayController;
import com.example.anhlamrduc.sothuchi.item.SumPayBySpendingItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class DBGetSumPayBySpending extends AsyncTask<Void, Object, ArrayList<SumPayBySpendingItem>> {
    private Context context;

    public DBGetSumPayBySpending(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<SumPayBySpendingItem> doInBackground(Void... params) {
        PayController db = new PayController(context);
        return db.getSumBySpendingItem();
    }
}
