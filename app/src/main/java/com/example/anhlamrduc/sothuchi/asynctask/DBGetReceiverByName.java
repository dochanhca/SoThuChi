package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.ReceiverController;
import com.example.anhlamrduc.sothuchi.item.Receiver;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class DBGetReceiverByName extends AsyncTask<String, Object, Receiver> {
    private Context context;

    public DBGetReceiverByName(Context context) {
        this.context = context;
    }

    @Override
    protected Receiver doInBackground(String... params) {
        ReceiverController db = new ReceiverController(context);
        return db.getReceiverByName(params[0]);
    }
}
