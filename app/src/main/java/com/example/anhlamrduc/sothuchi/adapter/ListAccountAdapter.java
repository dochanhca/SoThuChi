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
import com.example.anhlamrduc.sothuchi.utils.Currency;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 20-Mar-16.
 */
public class ListAccountAdapter extends ArrayAdapter<Account> {

    LayoutInflater layoutInflater;
    private AccountController db;
    private boolean selectMode;

    private OnAccountChildItemClickListener onAccountChildItemClickListener;

    public interface OnAccountChildItemClickListener {
        public void onEditClick(int position);
    }

    public ListAccountAdapter(Context context, ArrayList<Account> list, boolean selectMode) {
        super(context, -1, list);
        this.selectMode = selectMode;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Account account = getItem(position);
        ViewHolder viewHolder;

        String imgName = account.getAccountType().getImage();

        convertView = layoutInflater.inflate(R.layout.item_account, parent, false);
        viewHolder = new ViewHolder(convertView);
        convertView.setTag(viewHolder);

        viewHolder.txtAccountName.setText(account.getAccountName());
        String currently_money = Currency.getCurrency(account.getCurrentMoney());
        viewHolder.txtMoney.setText("Còn: " + currently_money + " đ");
        if (selectMode) {
            viewHolder.imgEdit.setVisibility(View.GONE);
        } else {
            viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onAccountChildItemClickListener.onEditClick(position);
                }
            });
        }
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

//    /**
//     * Get ImageName From DB
//     *
//     * @param typeAccountID
//     * @return
//     */
//    private String getImageName(int typeAccountID) {
//
//        db = new AccountController(getContext());
//        return db.getImageName(typeAccountID);
//    }

    public void setOnAccountChildItemClickListener(OnAccountChildItemClickListener onAccountChildItemClickListener) {
        this.onAccountChildItemClickListener = onAccountChildItemClickListener;
    }

}
