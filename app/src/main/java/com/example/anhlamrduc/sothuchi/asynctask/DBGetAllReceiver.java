package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.ReceiverController;
import com.example.anhlamrduc.sothuchi.item.Receiver;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class DBGetAllReceiver extends AsyncTask<Void, Object, ArrayList<Receiver>> {
    private Context context;

    public DBGetAllReceiver(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Receiver> doInBackground(Void... params) {
        ReceiverController db = new ReceiverController(context);
        return db.getListReceiver();
    }
}
