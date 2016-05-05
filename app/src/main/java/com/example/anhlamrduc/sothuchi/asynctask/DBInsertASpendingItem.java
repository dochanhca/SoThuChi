package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.SpendingController;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

/**
 * Created by AnhlaMrDuc on 03-May-16.
 */
public class DBInsertASpendingItem extends AsyncTask<SpendingItem, Object, Long> {
    private Context context;

    public DBInsertASpendingItem(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(SpendingItem... params) {
        SpendingController db = new SpendingController(context);
        return db.addSpendingItem(params[0]);
    }
}
