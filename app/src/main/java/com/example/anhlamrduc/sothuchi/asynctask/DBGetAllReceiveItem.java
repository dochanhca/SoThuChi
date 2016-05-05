package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.ReceiveItemController;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class DBGetAllReceiveItem extends AsyncTask<Void, Object, ArrayList<ReceiveItem>> {
    private Context context;

    public DBGetAllReceiveItem(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<ReceiveItem> doInBackground(Void... params) {
        ReceiveItemController db = new ReceiveItemController(context);
        return db.getListReceiveItem();
    }
}
