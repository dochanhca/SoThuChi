package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.IncomeController;
import com.example.anhlamrduc.sothuchi.item.SumIncomeByReceiveItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class DBGetSumReceiveByReceiveItem extends AsyncTask<Void, Object, ArrayList<SumIncomeByReceiveItem>> {
    private Context context;

    public DBGetSumReceiveByReceiveItem(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<SumIncomeByReceiveItem> doInBackground(Void... params) {
        IncomeController db = new IncomeController(context);
        return db.getSumIncomeByReceiveItem();
    }
}
