package com.example.anhlamrduc.sothuchi.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.item.Lender;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.Receiver;
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
        String TAG = getClass().getSimpleName();
        setupSharePrefrence();
        Log.e(TAG, " create");
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

    public Double getFirstAmount() {
        setupSharePrefrence();
        String amountJson = sharedPreferences.getString(MainActivity.FIRST_MONEY_FROM_ADD_ACCOUNT, "0");
        return gson.fromJson(amountJson, Double.class);
    }

    public Double getTransferFee() {
        setupSharePrefrence();
        String amountJson = sharedPreferences.getString(MainActivity.TRANSFER_FEE_FROM_NOTE, "0");
        return gson.fromJson(amountJson, Double.class);
    }

    public void setSpendingItem(SpendingItem spendingItem) {
        String spendingItemJson = gson.toJson(spendingItem);
        getEditor().putString(MainActivity.SPENDING_ITEM_FROM_LIST_SPENDING, spendingItemJson);
        getEditor().commit();
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

    public void setReceiveItem(ReceiveItem receiveItem) {
        String receiveItemJson = gson.toJson(receiveItem);
        getEditor().putString(MainActivity.RECEIVE_ITEM_NAME_FROM_LIST_RECEIVE, receiveItemJson);
        getEditor().commit();
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

    public void setDescription(String desciption) {
        getEditor().putString(MainActivity.DESCRIPTION_SPENDING, desciption);
        getEditor().commit();
    }

    public String getDescription() {
        setupSharePrefrence();
        return sharedPreferences.getString(MainActivity.DESCRIPTION_SPENDING, "");
    }

    public void setAccount(Account account, int type) {
        Gson gson = new Gson();
        String accountJson = gson.toJson(account);
        if (type == NoteFragment.FROM_ACCOUNT)
            getEditor().putString(MainActivity.FROM_ACCOUNT_SELECTED, accountJson);
        else
            getEditor().putString(MainActivity.TO_ACCOUNT_SELECTED, accountJson);
        getEditor().commit();
        getFragmentManager().popBackStack();
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

    public Account getNewAccount() {
        setupSharePrefrence();
        String account = sharedPreferences.getString(MainActivity.NEW_ACCOUNT, null);
        return gson.fromJson(account, Account.class);
    }

    public void setTransactionDate(Date transactionDate) {
        String payDateJson = gson.toJson(transactionDate);
        getEditor().putString(MainActivity.TRANSACTION_DATE, payDateJson);
        getEditor().commit();
    }

    public Date getTransactionDate() {
        setupSharePrefrence();
        String payDate = sharedPreferences.getString(MainActivity.TRANSACTION_DATE, null);
        return gson.fromJson(payDate, Date.class);
    }

    public void setReceiver(String receiverName) {
        getEditor().putString(MainActivity.RECEIVER_NAME, receiverName);
        getEditor().commit();
    }

    public String getReceiver() {
        setupSharePrefrence();
        return sharedPreferences.getString(MainActivity.RECEIVER_NAME, "");
    }

    public Receiver getNewRecevier() {
        setupSharePrefrence();
        String receiver = sharedPreferences.getString(MainActivity.NEW_RECEIVER, null);
        return gson.fromJson(receiver, Receiver.class);
    }

    public void setEvent(String tripOrEvent) {
        getEditor().putString(MainActivity.TRIP_OR_EVENT, tripOrEvent);
        getEditor().commit();
    }

    public String getEvent() {
        setupSharePrefrence();
        return sharedPreferences.getString(MainActivity.TRIP_OR_EVENT, "");
    }

    public Event getNewEvent() {
        setupSharePrefrence();
        String event = sharedPreferences.getString(MainActivity.NEW_EVENT, null);
        return gson.fromJson(event, Event.class);
    }

    public Lender getLender() {
        setupSharePrefrence();
        String lenderJson = sharedPreferences.getString(MainActivity.LENDER, null);
        return gson.fromJson(lenderJson, Lender.class);
    }

    public Income getNewReceiveMoney() {
        setupSharePrefrence();
        String receiveMoneyJson = sharedPreferences.getString(MainActivity.NEW_RECEIVE_MONEY, null);
        return gson.fromJson(receiveMoneyJson, Income.class);
    }

    public Pay getNewPay() {
        setupSharePrefrence();
        String payJson = sharedPreferences.getString(MainActivity.NEW_PAY, null);
        return gson.fromJson(payJson, Pay.class);
    }
}
