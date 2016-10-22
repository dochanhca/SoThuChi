package com.example.anhlamrduc.sothuchi.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListEventAdapter;
import com.example.anhlamrduc.sothuchi.item.Event;

import java.util.ArrayList;
import java.util.ListIterator;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class TripFragment extends BaseFragment {
    private ArrayList<Event> events;
    private ListEventAdapter listEventAdapter;
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String eventName = events.get(position).getEventName();
            edtEvent.setText(eventName);
        }
    };

    @Override
    protected int layoutID() {
        return R.layout.fr_trip;
    }

    @Override
    protected void initView() {
        listEventAdapter = new ListEventAdapter(getContext(), events);
        listEvent.setAdapter(listEventAdapter);
        listEvent.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void handleData() {
        events = getArguments().getParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB);
        if (getNewEvent() != null) {
            Event event = getNewEvent();
            events.add(event);
            getEditor().remove(MainActivity.NEW_EVENT);
            getEditor().commit();
        }
        handleList();

    }

    private void handleList() {
        ListIterator iterator = events.listIterator();
        while (iterator.hasNext()) {
            Event event = (Event) iterator.next();
            if (event.getEventName().length() == 0) {
                iterator.remove();
            }
        }
    }

    @OnClick(R.id.txt_done)
    public void selectEventDone() {
        String trip = edtEvent.getText().toString().trim();
        setEvent(trip);
        getFragmentManager().popBackStack();

    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        getFragmentManager().popBackStack();
    }

    @Bind(R.id.edt_event)
    EditText edtEvent;
    @Bind(android.R.id.list)
    ListView listEvent;

}
