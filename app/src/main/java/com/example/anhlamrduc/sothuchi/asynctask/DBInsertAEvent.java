package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.EventController;
import com.example.anhlamrduc.sothuchi.item.Event;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class DBInsertAEvent extends AsyncTask<Event, Object, Long> {
    private Context context;

    public DBInsertAEvent(Context context) {
        this.context = context;
    }

    @Override
    protected Long doInBackground(Event... params) {
        EventController db = new EventController(context);
        return db.addEvent(params[0]);
    }
}
