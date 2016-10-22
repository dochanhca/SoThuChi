package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 18-Jul-16.
 */
public class TransactionContainerFragment extends BaseFragment {
    public static final String TRANSACTION_FRAG = "TransactionFrag";

    private ArrayList<SpendingItem> spendingItems;
    private ArrayList<ReceiveItem> receiveItems;
    private ArrayList<Receiver> receivers;
    private ArrayList<Event> events;

    private int transactionMode;

    @Override
    protected int layoutID() {
        return R.layout.fr_transaction_container;
    }

    @Override
    protected void handleData() {

        receiveItems = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);
        receivers = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB);
        events = getArguments().getParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB);
        transactionMode = getArguments().getInt(NoteFragment.TRANSACTION_HISTORY, 0);

        TransactionFragment transactionFragment= new TransactionFragment();
        //put bundle
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB, spendingItems);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB, receiveItems);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB, receivers);
        args.putParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB, events);
        args.putInt(NoteFragment.TRANSACTION_HISTORY, transactionMode);
        transactionFragment.setArguments(args);
        //
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fr_transaction_container, transactionFragment, TRANSACTION_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    protected void initView() {

    }
}
