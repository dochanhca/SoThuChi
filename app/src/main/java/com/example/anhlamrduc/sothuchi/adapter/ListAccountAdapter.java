package com.example.anhlamrduc.sothuchi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.db.AccountController;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.utility.Currency;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 20-Mar-16.
 */
public class ListAccountAdapter extends ArrayAdapter<Account> {

    LayoutInflater layoutInflater;
    private AccountController db;

    public ListAccountAdapter(Context context, ArrayList<Account> list) {
        super(context, -1, list);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Account account = getItem(position);
        ViewHolder viewHolder;

        String imgName = getImageName(account.getAccountID());

        convertView = layoutInflater.inflate(R.layout.item_account, parent, false);
        viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);

        viewHolder.txtAccountName.setText(account.getAccountName());
        String currently_money = Currency.getCurrency(account.getCurrentMoney());
        viewHolder.txtMoney.setText("Còn: " + currently_money + " đ");
        int imgID = getContext().getResources().getIdentifier(imgName, "drawable", getContext().getPackageName());
        viewHolder.imgIcon.setImageResource(imgID);
        return convertView;
    }

    static class ViewHolder {

        @Bind(R.id.img_icon)
        ImageView imgIcon;
        @Bind(R.id.img_edit)
        ImageView imgEdit;
        @Bind(R.id.txt_account_name)
        TextView txtAccountName;
        @Bind(R.id.txt_account_money)
        TextView txtMoney;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }

    /**
     * Get ImageName From DB
     *
     * @param typeAccountID
     * @return
     */
    private String getImageName(int typeAccountID) {

        db = new AccountController(getContext());
        return db.getImageName(typeAccountID);
    }

}
