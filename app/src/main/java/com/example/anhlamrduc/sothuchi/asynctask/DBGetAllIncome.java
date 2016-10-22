package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.IncomeController;
import com.example.anhlamrduc.sothuchi.item.Income;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class DBGetAllIncome extends AsyncTask<Void, Object, ArrayList<Income>> {
    private Context context;

    public DBGetAllIncome(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Income> doInBackground(Void... params) {
        IncomeController db = new IncomeController(context);
        return db.getAllIncome();
    }
}
