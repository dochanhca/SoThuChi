package com.example.anhlamrduc.sothuchi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Lender;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.example.anhlamrduc.sothuchi.utils.Constant;
import com.google.gson.Gson;

import java.util.Date;

import butterknife.ButterKnife;

/**
 * Created by AnhlaMrDuc on 01-May-16.
 */
public abstract class BaseFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private Toast toast;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(layoutID(), container, false);
        ButterKnife.bind(this, v);
        handleData();
        initView();
        setupSharePrefrence();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void showAShortToask(String text) {
        if (toast == null || toast.getView().getWindowVisibility() != View.VISIBLE) {
            toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    protected abstract int layoutID();

    protected abstract void handleData();

    protected abstract void initView();

    public void setupSharePrefrence() {
        if (sharedPreferences == null) {

            sharedPreferences = getContext().getSharedPreferences(Constant.SHARED_PREFRENCES, Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            gson = new Gson();
        }
    }


    public SharedPreferences getSharedPreferences() {
        if (sharedPreferences == null) {
            setupSharePrefrence();
        }
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        if (editor == null) {
            setupSharePrefrence();
        }
        return editor;
    }

    public Double getAmount() {
        setupSharePrefrence();
        String amountJson = sharedPreferences.getString(MainActivity.MONEY_FROM_NOTE, "0");
        return gson.fromJson(amountJson, Double.class);
    }

    public Double getTransferFee() {
        setupSharePrefrence();
        String amountJson = sharedPreferences.getString(MainActivity.TRANSFER_FEE_FROM_NOTE, "0");
        return gson.fromJson(amountJson, Double.class);
    }

    public SpendingItem getSpendingItem() {
        setupSharePrefrence();
        String spendingItemJSon = sharedPreferences.getString(MainActivity.SPENDING_ITEM_FROM_LIST_SPENDING, null);
        return gson.fromJson(spendingItemJSon, SpendingItem.class);
    }

    public SpendingItem getNewSpendingItem() {
        setupSharePrefrence();
        String spendingItemJSon = sharedPreferences.getString(MainActivity.NEW_SPENDING_ITEM, null);
        return gson.fromJson(spendingItemJSon, SpendingItem.class);
    }

    public String getSpendingItemParentName() {
        setupSharePrefrence();
        return sharedPreferences.getString(MainActivity.SPENDING_ITEM_PARENT_NAME, "");
    }

    public ReceiveItem getReceiveItem() {
        setupSharePrefrence();
        String receiveItemJson = sharedPreferences.getString(MainActivity.RECEIVE_ITEM_NAME_FROM_LIST_RECEIVE, null);
        return gson.fromJson(receiveItemJson, ReceiveItem.class);
    }

    public ReceiveItem getNewReceiveItem() {
        setupSharePrefrence();
        String receiveItemJson = sharedPreferences.getString(MainActivity.NEW_RECEIVE_ITEM, null);
        return gson.fromJson(receiveItemJson, ReceiveItem.class);
    }

    public String getDescription() {
        setupSharePrefrence();
        return sharedPreferences.getString(MainActivity.DESCRIPTION_SPENDING, "");
    }

    public Account getFromAccount() {
        setupSharePrefrence();
        String account = sharedPreferences.getString(MainActivity.FROM_ACCOUNT_SELECTED, null);
        return gson.fromJson(account, Account.class);
    }

    public Account getToAccount() {
        setupSharePrefrence();
        String account = sharedPreferences.getString(MainActivity.TO_ACCOUNT_SELECTED, null);
        return gson.fromJson(account, Account.class);
    }

    public Date getPayDate() {
        setupSharePrefrence();
        String payDate = sharedPreferences.getString(MainActivity.PAY_DATE, null);
        return gson.fromJson(payDate, Date.class);
    }

    public String getRecevier() {
        setupSharePrefrence();
        return sharedPreferences.getString(MainActivity.RECEIVER_NAME, "");
    }

    public String getEvent() {
        setupSharePrefrence();
        return sharedPreferences.getString(MainActivity.TRIP_OR_EVENT, "");
    }

    public Lender getLender() {
        setupSharePrefrence();
        String lenderJson = sharedPreferences.getString(MainActivity.LENDER, null);
        return gson.fromJson(lenderJson, Lender.class);
    }
}
