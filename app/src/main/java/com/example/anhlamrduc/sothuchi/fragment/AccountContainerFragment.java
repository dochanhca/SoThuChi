package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Pay;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 25-Mar-16.
 */
public class AccountContainerFragment extends Fragment implements NoteFragment.OnPassDataFromNote {

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
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "on Attach (context)");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "on Created");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_account_container, container, false);

        AddAccountFragment fragment = (AddAccountFragment) getFragmentManager().findFragmentByTag(ADD_ACCOUNT_FRAG);

        if (v.findViewById(R.id.fragment_account_container) != null) {
            if (fragment == null) {
                arrAccount = getArguments().getParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_MAIN);
                totalMoney = getArguments().getDouble(MainActivity.TOTAL_MONEY_FROM_MAIN);
                //replace fragment_container = fragment account
                Bundle args = new Bundle();
                args.putParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_MAIN, arrAccount);
                args.putDouble(MainActivity.TOTAL_MONEY_FROM_MAIN, totalMoney);
                AccountFragment accountFragment = new AccountFragment();
                accountFragment.setArguments(args);
                //
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_account_container, accountFragment, ACCOUNT_FRAG);
                transaction.commit();
            }
        }

        Log.e(TAG, "On create View");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "on Activity Created");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "on Activity Started");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "on Activity Resume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "on Activity Pause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "on Activity Stop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e(TAG, "on Destroy view");
    }

    @Override
    public void onDataInsertToDBFromNote(Pay pay) {
        AccountFragment fragment = (AccountFragment) getFragmentManager().findFragmentByTag(ACCOUNT_FRAG);
        if (fragment != null) {
            fragment.onDataInsertToDBFromNote(pay);
        }
    }
}
