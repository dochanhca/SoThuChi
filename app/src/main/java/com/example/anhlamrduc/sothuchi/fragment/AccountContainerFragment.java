package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.ReceiveMoney;
import com.example.anhlamrduc.sothuchi.item.Transfer;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 25-Mar-16.
 */
public class AccountContainerFragment extends BaseFragment implements NoteFragment.OnPassDataFromNote {

    private static final String TAG = "Account Container ";
    private ArrayList<Account> arrAccount;
    private double totalMoney;

    public static final String ADD_ACCOUNT_FRAG = "AddAccountFragment";
    public static final String ACCOUNT_FRAG = "AccountFragment";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.e(TAG, "on Attach (Activity)");
    }

    @Override
    protected int layoutID() {
        return R.layout.fr_account_container;
    }

    @Override
    protected void handleData() {
        arrAccount = getArguments().getParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_DB);
        totalMoney = getArguments().getDouble(MainActivity.TOTAL_MONEY_FROM_MAIN);
        //replace fragment_container = fragment account
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_DB, arrAccount);
        args.putDouble(MainActivity.TOTAL_MONEY_FROM_MAIN, totalMoney);
        AccountFragment accountFragment = new AccountFragment();
        accountFragment.setArguments(args);
        //
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_account_container, accountFragment, ACCOUNT_FRAG);
        transaction.commit();
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onPayInsertToDBFromNote(Pay pay) {
        AccountFragment fragment = (AccountFragment) getFragmentManager().findFragmentByTag(ACCOUNT_FRAG);
        if (fragment != null) {
            fragment.onPayInsertToDBFromNote(pay);
        }
    }

    @Override
    public void onReceiveMoneyInsertToDBFromNote(ReceiveMoney receiveMoney) {
        AccountFragment fragment = (AccountFragment) getFragmentManager().findFragmentByTag(ACCOUNT_FRAG);
        if (fragment != null) {
            fragment.onReceiveMoneyInsertToDBFromNote(receiveMoney);
        }
    }

    @Override
    public void onTransferInsertToDBFromNote(Transfer transfer) {
        AccountFragment fragment = (AccountFragment) getFragmentManager().findFragmentByTag(ACCOUNT_FRAG);
        if (fragment != null) {
            fragment.onTransferInsertToDBFromNote(transfer);
        }
    }
}
