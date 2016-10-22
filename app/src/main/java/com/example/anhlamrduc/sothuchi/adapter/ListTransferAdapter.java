package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.item.Transfer;
import com.example.anhlamrduc.sothuchi.utils.Constant;
import com.example.anhlamrduc.sothuchi.utils.Currency;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 15-Jul-16.
 */
public class ListTransferAdapter extends ArrayAdapter<Transfer> {

    private LayoutInflater inflater;
    private Context context;

    public ListTransferAdapter(Context context, ArrayList<Transfer> list) {
        super(context, -1, list);
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Transfer transfer = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_pay_child, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.txtReasonTransaction.setText(context.getString(R.string.transfer_to)+" "+
        transfer.getToAccount());
        viewHolder.txtAmount.setText(Currency.getCurrency(transfer.getAmount()
                + transfer.getTransferFee()));
        viewHolder.txtFromAccountName.setText(transfer.getFromAccount().getAccountName());
        viewHolder.txt_date_spending.setText(String.format(Constant.VND_FORMAT, transfer.getTransferDate()));

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.txt_reason_transaction)
        TextView txtReasonTransaction;
        @Bind(R.id.txt_date_spending)
        TextView txt_date_spending;
        @Bind(R.id.txt_amount)
        TextView txtAmount;
        @Bind(R.id.txt_account_name)
        TextView txtFromAccountName;
        @Bind(R.id.img_account)
        ImageView imgAccount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
