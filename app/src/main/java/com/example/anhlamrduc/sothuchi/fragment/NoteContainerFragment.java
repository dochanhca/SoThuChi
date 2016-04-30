package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 28-Mar-16.
 */
public class NoteContainerFragment extends Fragment {

    public static final String NOTE_CONTAINER_FRAG = "NoteContainerFragment";
    public static final String LIST_SPEND_ITEM_FRAG = "ListSpendItemFragment";
    public static final String SPENDING_RECEIVED = "SpendingReceived";
    public static final String DESCRIPTION_FRAG = "DescriptionFragment";
    public static final String RECEIVER_FRAGMENT = "ReceiverFragment";
    public static final String TRIP_FRAGMENT = "TripFragment";
    public static final String SELECT_ACCOUNT_FRAG = "SelectAccountFragment";

    private ArrayList<SpendingItem> arrSpending;
    private ArrayList<Receiver> arrReceiver;
    private SpendingItem spendingReceived = new SpendingItem();
    private Account account = new Account();
    private String description = "";
    private String receiver = "";
    private String trip = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_note_container, container, false);

        //Receive a bundle
        arrSpending = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_MAIN);
        arrReceiver = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN);

        if (getArguments().getParcelable(MainActivity.SPENDING_RECEIVE_FROM_LIST_SPENDING) != null)
            spendingReceived = getArguments().getParcelable(MainActivity.SPENDING_RECEIVE_FROM_LIST_SPENDING);
        if (getArguments().getParcelable(MainActivity.ACCOUNT_FROM_SELECT_ACCOUNT) != null)
            account = getArguments().getParcelable(MainActivity.ACCOUNT_FROM_SELECT_ACCOUNT);

        description = getArguments().getString(MainActivity.DESCRIPTION_SPENDING, "");
        receiver = getArguments().getString(MainActivity.RECEIVER_NAME, "");
        trip = getArguments().getString(MainActivity.TRIP_OR_EVENT, "");

        if (v.findViewById(R.id.fr_note_container) != null) {
            //Put bundle to Note Frag
            Bundle args = new Bundle();
//            args.putParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_MAIN, arrAccount);
            args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_MAIN, arrSpending);
            args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN, arrReceiver);

            args.putParcelable(MainActivity.SPENDING_RECEIVE_FROM_LIST_SPENDING, spendingReceived);
            args.putParcelable(MainActivity.ACCOUNT_FROM_SELECT_ACCOUNT, account);

            args.putString(MainActivity.DESCRIPTION_SPENDING, description);
            args.putString(MainActivity.RECEIVER_NAME, receiver);
            args.putString(MainActivity.TRIP_OR_EVENT, trip);
            // Initiate Note Frag
            NoteFragment noteFragment = new NoteFragment();
            noteFragment.setArguments(args);
            //Begin Transaction
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fr_note_container, noteFragment, NOTE_CONTAINER_FRAG);
            transaction.commit();
        }

        return v;
    }

}
