package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.item.Event;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class ListEventAdapter extends ArrayAdapter<Event> {

    LayoutInflater layoutInflater;

    public ListEventAdapter(Context context, ArrayList<Event> list) {
        super(context, -1, list);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Event event = getItem(position);
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_receiver, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.txtReceiverName.setText(event.getEventName());
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.img_edit)
        ImageView imgEdit;
        @Bind(R.id.txt_receiver_name)
        TextView txtReceiverName;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }
}
