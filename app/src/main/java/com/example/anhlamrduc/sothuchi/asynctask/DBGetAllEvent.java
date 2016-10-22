package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.EventController;
import com.example.anhlamrduc.sothuchi.item.Event;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class DBGetAllEvent extends AsyncTask<Void, Object, ArrayList<Event>>{
    private Context context;

    public DBGetAllEvent(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Event> doInBackground(Void... params) {
        EventController db = new EventController(context);
        return db.getListEvent();
    }
}
