package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.TransferController;
import com.example.anhlamrduc.sothuchi.item.Transfer;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class DBInsertATransfer extends AsyncTask<Transfer, Object, Long> {
    private Context context;

    public DBInsertATransfer(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Transfer... params) {
        TransferController db = new TransferController(context);
        return db.addTransfer(params[0]);
    }
}
