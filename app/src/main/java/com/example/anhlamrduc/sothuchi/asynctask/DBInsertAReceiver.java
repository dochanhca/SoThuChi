package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.ReceiverController;
import com.example.anhlamrduc.sothuchi.item.Receiver;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class DBInsertAReceiver extends AsyncTask<Receiver, Object, Long> {
    private Context context;

    public DBInsertAReceiver(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Receiver... params) {
        ReceiverController db = new ReceiverController(context);
        return db.addReceiver(params[0]);
    }
}
