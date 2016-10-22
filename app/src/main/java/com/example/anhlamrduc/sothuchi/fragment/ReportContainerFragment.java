package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.example.anhlamrduc.sothuchi.item.Transfer;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 06-May-16.
 */
public class ReportContainerFragment extends BaseFragment implements NoteFragment.OnPassDataFromNote {
    public static final String REPORT_FRAG = "ReportFragmentTag";

    private ArrayList<SpendingItem> listSpendingItem = new ArrayList<>();
    private ArrayList<Receiver> listReceiver = new ArrayList<>();
    private ArrayList<ReceiveItem> listReceiveItem = new ArrayList<>();
    private ArrayList<Event> listEvent = new ArrayList<>();
    private ArrayList<Pay> listPay = new ArrayList<>();
    private ArrayList<Income> listIncome = new ArrayList<>();

    @Override
    protected int layoutID() {
        return R.layout.fr_report_container;
    }

    @Override
    protected void handleData() {
        Bundle args = new Bundle();

        listReceiveItem = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);
        listSpendingItem = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB);
        listReceiver = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB);
        listEvent = getArguments().getParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB);

        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB, listSpendingItem);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB, listReceiver);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB, listReceiveItem);
        args.putParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB, listEvent);


        // Initiate Report Frag
        ReportFragment reportFragment = new ReportFragment();
        reportFragment.setArguments(args);
        //Begin Transaction
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fr_report_container, reportFragment, REPORT_FRAG);
        transaction.commit();
    }

    @Override
    protected void initView() {

    }


    @Override
    public void onPayInsertToDBFromNote(Pay pay) {
        ReportFragment fragment = (ReportFragment) getFragmentManager().findFragmentByTag(REPORT_FRAG);
        if (fragment != null) {
            fragment.onPayInsertToDBFromNote(pay);
        }
    }

    @Override
    public void onReceiveMoneyInsertToDBFromNote(Income income) {
        ReportFragment fragment = (ReportFragment) getFragmentManager().findFragmentByTag(REPORT_FRAG);
        if (fragment != null) {
            fragment.onReceiveMoneyInsertToDBFromNote(income);
        }
    }

    @Override
    public void onTransferInsertToDBFromNote(Transfer transfer) {

    }
}
