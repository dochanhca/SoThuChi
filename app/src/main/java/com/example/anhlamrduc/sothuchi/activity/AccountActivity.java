package com.example.anhlamrduc.sothuchi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.adapter.ListAccountAdapter;
import com.example.anhlamrduc.sothuchi.db.AccountController;
import com.example.anhlamrduc.sothuchi.item.TaiKhoan;
import com.example.anhlamrduc.sothuchi.utility.Currency;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class AccountActivity extends ListFragment {

    private ListView lvAccount;
    private TextView txtSum;

    private ArrayList<TaiKhoan> arrAccount;
    private ListAccountAdapter listAccountAdapter;
    private AccountController db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new AccountController(getActivity());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout for AccountActivity
        View v = inflater.inflate(R.layout.account_layout, container, false);


        txtSum = (TextView) v.findViewById(R.id.txt_sum);
        String sumMoney = Currency.getCurrency(getSumMoney());
        txtSum.setText("Tổng còn: " + sumMoney + " đ");
        lvAccount = (ListView) v.findViewById(android.R.id.list);
        arrAccount = getListAccount();
        listAccountAdapter = new ListAccountAdapter(getActivity(), arrAccount);
        lvAccount.setAdapter(listAccountAdapter);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        db.close();
    }

    /**
     * Retrieve all account from TaiKhoan Table
     *
     * @return
     */
    private ArrayList<TaiKhoan> getListAccount() {

        ArrayList<TaiKhoan> arr = db.getListAccount();
        return arr;

    }

    /**
     * Get sum currently money
     *
     * @return sum
     */
    private double getSumMoney() {
        db = new AccountController(getContext());
        return db.getSumMoney();
    }
}
