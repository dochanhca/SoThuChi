package com.example.anhlamrduc.sothuchi.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListIncomeAdapter;
import com.example.anhlamrduc.sothuchi.adapter.ListPayAdapter;
import com.example.anhlamrduc.sothuchi.adapter.ListTransferAdapter;
import com.example.anhlamrduc.sothuchi.adapter.NoteSpinnerAdapter;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllIncome;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllPay;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllTransfer;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.example.anhlamrduc.sothuchi.item.Transfer;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 30-Apr-16.
 */
public class TransactionFragment extends BaseFragment {

    @Bind(android.R.id.list)
    ListView lvTransaction;
    @Bind(R.id.spn_note_action)
    Spinner spnNoteAction;

    private ArrayList<SpendingItem> spendingItems;
    private ArrayList<ReceiveItem> receiveItems;
    private ArrayList<Receiver> receivers;
    private ArrayList<Event> events;

    private ArrayList<Pay> listPay = new ArrayList<>();
    private ListPayAdapter payAdapter;

    private ArrayList<Income> listIncome = new ArrayList<>();
    private ListIncomeAdapter incomeAdapter;

    private ArrayList<Transfer> listTransfer = new ArrayList<>();
    private ListTransferAdapter transferAdapter;

    private int transactionMode;
    private ProgressDialog dialog;
    private AdapterView.OnItemClickListener onlvTransferClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Bundle args = new Bundle();
//            args.putInt(NoteFragment.TRANSACTION_HISTORY, 2);
//            args.putParcelable(NoteFragment.TRANSFER, listTransfer.get(position));
//            goNoteFragment(args);
        }
    };
    private AdapterView.OnItemClickListener onLvIncomeClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Bundle args = new Bundle();
//            args.putInt(NoteFragment.TRANSACTION_HISTORY, 1);
//            args.putParcelable(NoteFragment.INCOME, listIncome.get(position));
//            goNoteFragment(args);
        }
    };
    private AdapterView.OnItemClickListener onLvPayClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Bundle args = new Bundle();
//            args.putInt(NoteFragment.TRANSACTION_HISTORY, 0);
//            args.putParcelable(NoteFragment.PAY, listPay.get(position));
//            goNoteFragment(args);
        }
    };

    @Override
    protected int layoutID() {
        return R.layout.fr_history;
    }

    @Override
    protected void handleData() {
        dialog = new ProgressDialog(getContext());

        spendingItems = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB);
        receiveItems = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);
        receivers = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB);
        events = getArguments().getParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB);

        transactionMode = getArguments().getInt(NoteFragment.TRANSACTION_HISTORY, 0);

        if (transactionMode == 0)
            showListPay();
        else if (transactionMode == 1)
            showListIncome();
        else
            showListTransfer();


    }

    private void showListTransfer() {
        DBGetAllTransfer dbGetAllTransfer = new DBGetAllTransfer(getContext()) {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.show();
            }

            @Override
            protected void onPostExecute(ArrayList<Transfer> transfers) {
                super.onPostExecute(transfers);
                listTransfer = transfers;
                Collections.sort(listTransfer);

                transferAdapter = new ListTransferAdapter(getContext(), listTransfer);
                lvTransaction.setAdapter(transferAdapter);
                lvTransaction.setOnItemClickListener(onlvTransferClickListener);
                dialog.dismiss();
                spnNoteAction.setSelection(2);
            }
        };
        dbGetAllTransfer.execute();
    }

    private void showListIncome() {
        DBGetAllIncome dbGetAllIncome = new DBGetAllIncome(getContext()) {
            @Override
            protected void onPreExecute() {
                dialog.show();
            }

            @Override
            protected void onPostExecute(ArrayList<Income> receiveMoneys) {
                super.onPostExecute(receiveMoneys);
                listIncome = receiveMoneys;
                Collections.sort(listIncome);

                incomeAdapter = new ListIncomeAdapter(getContext(), listIncome);
                lvTransaction.setAdapter(incomeAdapter);
                lvTransaction.setOnItemClickListener(onLvIncomeClickListener);
                dialog.dismiss();
                spnNoteAction.setSelection(1);
            }
        };
        dbGetAllIncome.execute();
    }

    private void showListPay() {
        DBGetAllPay dbGetAllPay = new DBGetAllPay(getContext()) {
            @Override
            protected void onPreExecute() {
                dialog.show();
            }

            @Override
            protected void onPostExecute(ArrayList<Pay> pays) {
                super.onPostExecute(pays);
                listPay = pays;

                Collections.sort(listPay);
                payAdapter = new ListPayAdapter(getContext(), listPay);
                lvTransaction.setAdapter(payAdapter);
                lvTransaction.setOnItemClickListener(onLvPayClickListener);
                dialog.dismiss();
                spnNoteAction.setSelection(0);
            }
        };
        dbGetAllPay.execute();
    }

    private void goNoteFragment(Bundle args) {
        args.putBoolean(NoteFragment.EDIT_MODE, true);

        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB, spendingItems);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB, receiveItems);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB, receivers);
        args.putParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB, events);

        NoteFragment noteFragment = new NoteFragment();
        noteFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fr_transaction_container, noteFragment,
                NoteContainerFragment.NOTE_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void initView() {
        spnNoteAction.setAdapter(new NoteSpinnerAdapter(getContext(), R.layout.spinner_dropdown_custom, getResources().getStringArray(R.array.spn_note_title)));
        spnNoteAction.setOnItemSelectedListener(onItemSelectedListtener);
    }

    @OnClick(R.id.img_back)
    public void goToBack() {
        getFragmentManager().popBackStack();
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListtener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                showListPay();
            } else if (position == 1) {
                showListIncome();
            } else if (position == 2) {
                showListTransfer();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}
