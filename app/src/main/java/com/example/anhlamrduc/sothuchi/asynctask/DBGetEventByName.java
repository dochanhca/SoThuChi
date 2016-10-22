package com.example.anhlamrduc.sothuchi.asynctask;

import android.content.Context;
import android.os.AsyncTask;

import com.example.anhlamrduc.sothuchi.db.EventController;
import com.example.anhlamrduc.sothuchi.item.Event;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class DBGetEventByName extends AsyncTask<String, Object, Event> {
    private Context context;

    public DBGetEventByName(Context context) {
        this.context = context;
    }

    @Override
    protected Event doInBackground(String... params) {
        EventController db = new EventController(context);
        return db.getEventByName(params[0]);
    }
}
