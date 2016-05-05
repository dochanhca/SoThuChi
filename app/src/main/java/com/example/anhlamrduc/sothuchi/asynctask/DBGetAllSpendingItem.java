package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.SpendingController;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class DBGetAllSpendingItem extends AsyncTask<Void, Object, ArrayList<SpendingItem>> {
    private Context context;

    public DBGetAllSpendingItem(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<SpendingItem> doInBackground(Void... params) {
        SpendingController db = new SpendingController(context);
        return db.getListSpending();
    }
}
