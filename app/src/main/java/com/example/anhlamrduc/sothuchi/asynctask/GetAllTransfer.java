package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.TransferController;
import com.example.anhlamrduc.sothuchi.item.Transfer;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class GetAllTransfer extends AsyncTask<Void, Object, ArrayList<Transfer>> {
    private Context context;

    public GetAllTransfer(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Transfer> doInBackground(Void... params) {
        TransferController db = new TransferController(context);
        return db.getAllTransfer();
    }
}
