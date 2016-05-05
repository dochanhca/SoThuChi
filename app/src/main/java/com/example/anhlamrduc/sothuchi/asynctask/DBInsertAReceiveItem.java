package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.ReceiveItemController;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;

/**
 * Created by AnhlaMrDuc on 03-May-16.
 */
public class DBInsertAReceiveItem extends AsyncTask<ReceiveItem, Object, Long> {
    private Context context;

    public DBInsertAReceiveItem(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(ReceiveItem... params) {
        ReceiveItemController db = new ReceiveItemController(context);
        return db.addReceiveItem(params[0]);
    }
}
