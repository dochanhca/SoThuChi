package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.utils.Constant;
import com.example.anhlamrduc.sothuchi.utils.Currency;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 07-May-16.
 */
public class ListIncomeAdapter extends ArrayAdapter<Income> {

    LayoutInflater inflater;

    public ListIncomeAdapter(Context context, ArrayList<Income> list) {
        super(context, -1, list);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Income income = getItem(position);
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pay_child, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.txtSpendingItem.setText(income.getReceiveItem().getReceiveItemName());
        viewHolder.txtAmount.setText(Currency.getCurrency(income.getAmount()));
        viewHolder.txt_date_spending.setText(Constant.VN_DATE_FORMAT.format(income.getReceiveDate()));
        viewHolder.accountName.setText(income.getAccount().getAccountName());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.txt_reason_transaction)
        TextView txtSpendingItem;
        @Bind(R.id.txt_date_spending)
        TextView txt_date_spending;
        @Bind(R.id.txt_amount)
        TextView txtAmount;
        @Bind(R.id.txt_account_name)
        TextView accountName;
        @Bind(R.id.img_account)
        ImageView imgAccount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
