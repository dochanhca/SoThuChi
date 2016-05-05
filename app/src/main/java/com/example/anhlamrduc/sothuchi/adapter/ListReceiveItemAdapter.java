package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 03-May-16.
 */
public class ListReceiveItemAdapter extends ArrayAdapter<ReceiveItem> {
    LayoutInflater inflater;

    public ListReceiveItemAdapter(Context context, ArrayList<ReceiveItem> list) {
        super(context, -1, list);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReceiveItem receiveItem = getItem(position);

        convertView = inflater.inflate(R.layout.item_receive, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.txtReceiveItemName.setText(receiveItem.getReceiveItemName());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.txt_item_name)
        TextView txtReceiveItemName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
