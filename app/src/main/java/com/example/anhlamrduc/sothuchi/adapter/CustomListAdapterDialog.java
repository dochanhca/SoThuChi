package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.item.AccountType;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 04-May-16.
 */
public class CustomListAdapterDialog extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<AccountType> accountTypes;

    public CustomListAdapterDialog(Context context, ArrayList<AccountType> accountTypes) {
        this.context = context;
        this.accountTypes = accountTypes;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return accountTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return accountTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return accountTypes.get(position).getTypeID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AccountType item = accountTypes.get(position);
        ViewHolder viewHolder;
        convertView = layoutInflater.inflate(R.layout.item_type_account, parent, false);
        viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);
        viewHolder.txtTypeAccount.setText(item.getTypeName());
        int imgID = context.getResources().getIdentifier(item.getImage(), "drawable", context.getPackageName());
        viewHolder.imgTypeAccount.setImageResource(imgID);

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.img_type_account)
        ImageView imgTypeAccount;
        @Bind(R.id.txt_type_account)
        TextView txtTypeAccount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
