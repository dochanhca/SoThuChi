package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 03-May-16.
 */
public class ListSpendingItemParentAdapter extends ArrayAdapter<SpendingItem> {

    LayoutInflater inflater;

    public ListSpendingItemParentAdapter(Context context, ArrayList<SpendingItem> list) {
        super(context, -1,list);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpendingItem spendingItem = getItem(position);
        convertView = inflater.inflate(R.layout.item_receive, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.txtSpendingParentName.setText(spendingItem.getSpendingItemName());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.txt_item_name)
        TextView txtSpendingParentName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
