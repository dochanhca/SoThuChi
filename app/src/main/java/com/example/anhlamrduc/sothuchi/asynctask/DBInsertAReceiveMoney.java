package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.IncomeController;
import com.example.anhlamrduc.sothuchi.item.Income;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class DBInsertAReceiveMoney extends AsyncTask<Income, Object, Long> {
    private Context context;

    public DBInsertAReceiveMoney(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Income... params) {
        IncomeController db = new IncomeController(context);
        return db.addIncome(params[0]);
    }
}
