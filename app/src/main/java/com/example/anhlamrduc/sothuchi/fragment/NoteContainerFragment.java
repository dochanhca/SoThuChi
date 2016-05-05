package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 28-Mar-16.
 */
public class NoteContainerFragment extends BaseFragment {

    public static final String NOTE_FRAG = "NoteFragment";
    public static final String LIST_SPEND_ITEM_FRAG = "ListSpendItemFragment";
    public static final String SPENDING_RECEIVED = "SpendingReceived";
    public static final String DESCRIPTION_FRAG = "DescriptionFragment";
    public static final String RECEIVER_FRAGMENT = "ReceiverFragment";
    public static final String TRIP_FRAGMENT = "TripFragment";
    public static final String SELECT_ACCOUNT_FRAG = "SelectAccountFragment";
    public static final String LIST_RECEIVE_ITEM_FRAG = "ListReceiveItemFragment";

    private ArrayList<SpendingItem> spendingItems;
    private ArrayList<Receiver> receivers;
    private ArrayList<ReceiveItem> receiveItems;
    private Account account;

    @Override
    protected int layoutID() {
        return R.layout.fr_note_container;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void handleData() {
        //Receive a bundle
        spendingItems = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB);
        receivers = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN);
        receiveItems = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);

        account = getArguments().getParcelable(MainActivity.DEFAULT_ACCOUNT);

        //Put bundle to Note Frag
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB, spendingItems);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN, receivers);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB, receiveItems);

        args.putParcelable(MainActivity.DEFAULT_ACCOUNT, account);
        // Initiate Note Frag
        NoteFragment noteFragment = new NoteFragment();
        noteFragment.setArguments(args);
        //Begin Transaction
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fr_note_container, noteFragment, NOTE_FRAG);
        transaction.commit();
    }

}
