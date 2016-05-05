package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.NoteSpinnerAdapter;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Borrower;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Lender;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.ReceiveMoney;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.example.anhlamrduc.sothuchi.item.Transfer;
import com.example.anhlamrduc.sothuchi.utils.Constant;
import com.example.anhlamrduc.sothuchi.utils.Currency;
import com.example.anhlamrduc.sothuchi.utils.NumberTextWatcher;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class NoteFragment extends BaseFragment {

    public static final String SELECT_ACCOUNT = "Select account";
    public static final int FROM_ACCOUNT = 1;
    public static final int TO_ACCOUNT = 2;
    private static final String TAG = "Note fragment:";

    Date currentDay = Calendar.getInstance().getTime();
    Gson gson = new Gson();

    private ArrayList<SpendingItem> spendingItems;
    private ArrayList<ReceiveItem> receiveItems;
    private ArrayList<Parcelable> arrReceiver;

    private SpendingItem spendingReceived = new SpendingItem();
    private ReceiveItem receiveItemReceived = new ReceiveItem();
    private Account fromAccount, toAccount;
    private String description = "";
    private String receiver = "";
    Date transactionDate = currentDay;
    private String event = "";
    double money = 0, transferFee = 0;

    OnPassDataFromNote onPassDataFromNote;

    public interface OnPassDataFromNote {
        public void onPayInsertToDBFromNote(Pay pay);

        public void onReceiveMoneyInsertToDBFromNote(ReceiveMoney receiveMoney);

        public void onTransferInsertToDBFromNote(Transfer transfer);
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            setTextFromDate(date);
            transactionDate = date;
            String payDateJson = gson.toJson(transactionDate);
            getEditor().putString(MainActivity.PAY_DATE, payDateJson);
            getEditor().commit();
        }
    };

    private TextView.OnEditorActionListener onAmountEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE && edtAmount.getText().length() > 0) {
                    Double money = Double.valueOf(edtAmount.getText().toString());
                    String amountJson = gson.toJson(money);
                    getEditor().putString(MainActivity.MONEY_FROM_NOTE, amountJson);
            }
            return false;
        }
    };

    private TextView.OnEditorActionListener onTransferFeeActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE && edtTransferFee.getText().length() > 0) {
                Double money = Double.valueOf(edtTransferFee.getText().toString());
                String amountJson = gson.toJson(money);
                getEditor().putString(MainActivity.TRANSFER_FEE_FROM_NOTE, amountJson);
            }
            return false;
        }
    };


    private AdapterView.OnItemSelectedListener onItemSelectedListtener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                showSpendingView();
            } else if (position == 1) {
                showReceiveView();
            } else if (position == 2) {
                showTransferView();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int layoutID() {
        return R.layout.fr_note;
    }

    @Override
    protected void handleData() {
        spendingItems = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB);
        receiveItems = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);
        arrReceiver = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN);

        Log.e(TAG, " onCreate view");
        if (getSpendingItem() != null)
            spendingReceived = getSpendingItem();
        if(getReceiveItem() != null)
            receiveItemReceived = getReceiveItem();
        if (getFromAccount() != null)
            fromAccount = getFromAccount();
        else
            fromAccount = getArguments().getParcelable(MainActivity.DEFAULT_ACCOUNT);
        if (getToAccount() != null)
            toAccount = getToAccount();
        else
            toAccount = getArguments().getParcelable(MainActivity.DEFAULT_ACCOUNT);
        if (getPayDate() != null)
            transactionDate = getPayDate();
        description = getDescription();
        money = getAmount();
        transferFee = getTransferFee();
        receiver = getRecevier();
        event = getEvent();
    }

    @Override
    protected void initView() {

        txtSpending.setText(spendingReceived.getSpendingItemName());
        txtNoteDetail.setText(description);
        txtFromAccountName.setText(fromAccount.getAccountName());

        if (getPayDate() != null)
            setTextFromDate(getPayDate());
        else
            txtDate.setText(Constant.HOUR_FORMAT.format(currentDay) + " " + getString(R.string.today));

        txtReceiver.setText(receiver);
        if (event.length() > 0)
            txtEvent.setText(event);
        if (money > 0)
            edtAmount.setText(Currency.getCurrency(money));
        edtAmount.addTextChangedListener(new NumberTextWatcher(edtAmount));

        edtAmount.setOnEditorActionListener(onAmountEditorActionListener);
        //Set Onclick for Account Spinner
        spnNoteAction.setAdapter(new NoteSpinnerAdapter(getContext(), R.layout.spinner_dropdown_custom, getResources().getStringArray(R.array.spn_note_title)));
        spnNoteAction.setOnItemSelectedListener(onItemSelectedListtener);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onPassDataFromNote = (OnPassDataFromNote) activity;
        } catch (ClassCastException e) {
            e.getMessage();
        }
    }

    @OnClick({R.id.ll_add, R.id.btn_write})
    public void addTransaction() {
        if (llSendingItem.getVisibility() == View.VISIBLE) {
            insertAPay();
        } else if (llReceiveItem.getVisibility() == View.VISIBLE) {
            insertAReceiveMoney();
        } else if (llTransferFee.getVisibility() == View.VISIBLE) {
            insertATransfer();
        }

    }

    @OnClick(R.id.ll_spending_item)
    public void showListSpendingItem() {
        ListSpendItemContainerFragment listSpendItemContainerFragment = new ListSpendItemContainerFragment();
        //put bundle
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB, spendingItems);
        listSpendItemContainerFragment.setArguments(args);
        //
        replaceView(listSpendItemContainerFragment, NoteContainerFragment.LIST_SPEND_ITEM_FRAG);
    }

    @OnClick(R.id.ll_receive_item)
    public void showListReceiveItem() {
        ListReceiveItemContainerFragment listReceiveItemContainerFragment = new ListReceiveItemContainerFragment();
        /* Put list of receive item*/
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB, receiveItems);
        listReceiveItemContainerFragment.setArguments(args);
        replaceView(listReceiveItemContainerFragment, NoteContainerFragment.LIST_RECEIVE_ITEM_FRAG);
    }

    @OnClick(R.id.ll_note_detail)
    public void showDescriptionFrag() {
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fr_note_container, descriptionFragment,
                NoteContainerFragment.DESCRIPTION_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @OnClick({R.id.ll_from_account, R.id.ll_to_account})
    public void goToSelectAccountFrag(View view) {
        SelectAccountFragment selectAccountFragment = new SelectAccountFragment();
        Bundle args = new Bundle();
        args.putInt(SELECT_ACCOUNT, view.getId() == R.id.ll_from_account ? FROM_ACCOUNT : TO_ACCOUNT);

        selectAccountFragment.setArguments(args);
        replaceView(selectAccountFragment, NoteContainerFragment.SELECT_ACCOUNT_FRAG);
    }

    @OnClick(R.id.ll_date_spending)
    public void selectDateSpending() {
        new SlideDateTimePicker.Builder(getFragmentManager())
                .setListener(listener)
                .setInitialDate(new Date())
                //.setMinDate(minDate)
                //.setMaxDate(maxDate)
                .setIs24HourTime(true)
                //.setTheme(SlideDateTimePicker.HOLO_DARK)
                //.setIndicatorColor(Color.parseColor("#990000"))
                .build()
                .show();
    }

    @OnClick(R.id.ll_receiver)
    public void selectReceiver() {
        ReceiverFragment receiverFragment = new ReceiverFragment();
        //put bundle
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN, arrReceiver);
        receiverFragment.setArguments(args);
        //
        replaceView(receiverFragment, NoteContainerFragment.RECEIVER_FRAGMENT);
    }

    @OnClick(R.id.ll_trip)
    public void selectTrip() {
        TripFragment tripFragment = new TripFragment();
        //put bundle
        Bundle args = new Bundle();
        tripFragment.setArguments(args);
        replaceView(tripFragment, NoteContainerFragment.TRIP_FRAGMENT);
    }

    @OnClick(R.id.img_view_history)
    public void viewHistory() {
        PayHistoryContainerFragment payHistoryFragment = new PayHistoryContainerFragment();
    }

    private void insertAPay() {
         /* Insert a transaction to db */
        if (txtSpending.getText().length() < 1) {
            showAShortToask(getString(R.string.spending_cant_be_blank));
        } else if (receiver.length() < 1 && event.length() < 1) {
            money = (edtAmount.getText().length() > 0 ? Double.parseDouble(edtAmount.getText().toString()) * 1000 : 0);
            Pay pay = initiatePay(3, 1);
            onPassDataFromNote.onPayInsertToDBFromNote(pay);
            txtSpending.setText("");
            txtReceiver.setText("");
            txtEvent.setText(getString(R.string.trip_or_event));
            resetView();
        }
    }

    private void insertATransfer() {
        if (edtAmount.getText().length() < 1 || edtAmount.getText().toString().equals("0")) {
            showAShortToask(getString(R.string.amount_must_greater_than_0));
        } else if (fromAccount.equals(toAccount)) {
            showAShortToask(getString(R.string.account_cant_same));
        } else {
            money = Double.parseDouble(edtAmount.getText().toString()) * 1000;
            transferFee = (edtTransferFee.getText().length() > 0 ?
                    Double.parseDouble(edtTransferFee.getText().toString()) * 1000 : 0);
            Transfer transfer = initiateTransfer();
            onPassDataFromNote.onTransferInsertToDBFromNote(transfer);
            edtTransferFee.setText("");
            resetView();
        }
    }

    private Transfer initiateTransfer() {
        Transfer transfer = new Transfer();
        transfer.setAmount(money);
        transfer.setDescription(description);
        transfer.setTransferDate(transactionDate);
        transfer.setTransferFee(transferFee);
        transfer.setFromAccount(fromAccount);
        transfer.setToAccount(toAccount);
        return transfer;
    }

    private void insertAReceiveMoney() {
        if (txtReceive.getText().length() < 1) {
            showAShortToask(getString(R.string.receive_cant_be_blank));
        } else if (event.length() < 1) {
            money = (edtAmount.getText().length() > 0 ? Double.parseDouble(edtAmount.getText().toString()) * 1000 : 0);
            ReceiveMoney receiveMoney = initiateReceiveMoney(1);
            onPassDataFromNote.onReceiveMoneyInsertToDBFromNote(receiveMoney);
            txtReceive.setText("");
            txtEvent.setText(getString(R.string.trip_or_event));
            resetView();
        }
    }

    private void resetView() {

        txtNoteDetail.setText("");
        edtAmount.setText("");
        getEditor().clear();
        getEditor().commit();
    }


    /**
     * Initialize a Pay send to the Main to save to db
     *
     * @param receverID
     * @param eventID
     * @return a pay
     */
    private Pay initiatePay(int receverID, int eventID) {
        SpendingItem spending = new SpendingItem();
        spending.setSpendingItemID(spendingReceived.getSpendingItemID());

        Lender lender = new Lender();
        lender.setLenderID(1);

        Borrower borrower = new Borrower();
        borrower.setBorrowerID(1);

        Receiver receiver = new Receiver();
        receiver.setReceiverID(receverID);

        Event event = new Event();
        event.setEventID(eventID);

        Pay pay = new Pay();
        pay.setDescription(description);
        pay.setMoney(money);
        pay.setPayDate(transactionDate);
        pay.setLender(lender);
        pay.setReceiver(receiver);
        pay.setBorrower(borrower);
        pay.setEvent(event);
        pay.setAccount(fromAccount);
        return pay;
    }

    /**
     * Initialize  a ReceiveMoney send to the Main to save to db
     * @param eventID
     * @return a receiveMoney
     */
    private ReceiveMoney initiateReceiveMoney(int eventID) {
        ReceiveItem receiveItem = new ReceiveItem();
        receiveItem.setReceiveItemID(receiveItemReceived.getReceiveItemID());

        Event event = new Event();
        event.setEventID(eventID);

        Lender lender = new Lender();
        lender.setLenderID(1);

        Borrower borrower = new Borrower();
        borrower.setBorrowerID(1);

        ReceiveMoney receiveMoney = new ReceiveMoney();
        receiveMoney.setAmount(money);
        receiveMoney.setDescription(description);
        receiveMoney.setReceiveDate(transactionDate);
        receiveMoney.setLender(lender);
        receiveMoney.setBorrower(borrower);
        receiveMoney.setReceiveItem(receiveItem);
        receiveMoney.setAccount(toAccount);

        return receiveMoney;
    }

    private void setTextFromDate(Date date) {
        if (Constant.DATE_FORMAT.format(date).equals(Constant.DATE_FORMAT.format(currentDay))) {
            txtDate.setText(Constant.HOUR_FORMAT.format(date) + " " + getString(R.string.today));
        } else {
            txtDate.setText(Constant.HOUR_FORMAT.format(date) + " " + Constant.DATE_FORMAT.format(date));
        }
    }

    /**
     * Replace NoteFragment with difference fragment
     *
     * @param fragment
     * @param tag
     */
    private void replaceView(Fragment fragment, String tag) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fr_note_container, fragment,
                tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showTransferView() {
        /* Hide view */
        if (llReceiveItem.getVisibility() == View.VISIBLE)
            llReceiveItem.setVisibility(View.GONE);
        if (llSendingItem.getVisibility() == View.VISIBLE)
            llSendingItem.setVisibility(View.GONE);
        if (llReceiver.getVisibility() == View.VISIBLE)
            llReceiver.setVisibility(View.GONE);
        if (llEvent.getVisibility() == View.VISIBLE)
            llEvent.setVisibility(View.GONE);
        /* Show View */
        if (llFromAccount.getVisibility() == View.GONE)
            llFromAccount.setVisibility(View.VISIBLE);
        if (llToAccount.getVisibility() == View.GONE)
            llToAccount.setVisibility(View.VISIBLE);
        if (llTransferFee.getVisibility() == View.GONE)
            llTransferFee.setVisibility(View.VISIBLE);
        if (transferFee > 0)
            edtTransferFee.setText(Currency.getCurrency(transferFee));
        txtToAccountName.setText(toAccount.getAccountName());
        edtTransferFee.setOnEditorActionListener(onTransferFeeActionListener);
        edtTransferFee.addTextChangedListener(new NumberTextWatcher(edtTransferFee));
    }


    private void showSpendingView() {
        /* Hide view */
        if (llReceiveItem.getVisibility() == View.VISIBLE)
            llReceiveItem.setVisibility(View.GONE);
        if (llToAccount.getVisibility() == View.VISIBLE)
            llToAccount.setVisibility(View.GONE);
        if (llTransferFee.getVisibility() == View.VISIBLE)
            llTransferFee.setVisibility(View.GONE);
        /* Show view */
        if (llSendingItem.getVisibility() == View.GONE)
            llSendingItem.setVisibility(View.VISIBLE);
        if (llFromAccount.getVisibility() == View.GONE)
            llFromAccount.setVisibility(View.VISIBLE);
        if (llReceiver.getVisibility() == View.GONE)
            llReceiver.setVisibility(View.VISIBLE);
        if (llEvent.getVisibility() == View.GONE)
            llEvent.setVisibility(View.GONE);
    }

    private void showReceiveView() {
        /* Hide view */
        if (llSendingItem.getVisibility() == View.VISIBLE)
            llSendingItem.setVisibility(View.GONE);
        if (llFromAccount.getVisibility() == View.VISIBLE)
            llFromAccount.setVisibility(View.GONE);
        if (llReceiver.getVisibility() == View.VISIBLE)
            llReceiver.setVisibility(View.GONE);
        if (llTransferFee.getVisibility() == View.VISIBLE)
            llTransferFee.setVisibility(View.GONE);
        //
        if (llReceiveItem.getVisibility() == View.GONE)
            llReceiveItem.setVisibility(View.VISIBLE);
        if (llToAccount.getVisibility() == View.GONE)
            llToAccount.setVisibility(View.VISIBLE);
        if (llEvent.getVisibility() == View.GONE)
            llEvent.setVisibility(View.GONE);
        txtToAccountName.setText(toAccount.getAccountName());
        txtReceive.setText(receiveItemReceived.getReceiveItemName());
    }


    @Bind(R.id.edt_amount)
    EditText edtAmount;
    @Bind(R.id.spn_note_action)
    Spinner spnNoteAction;
    @Bind(R.id.txt_spending)
    TextView txtSpending;
    @Bind(R.id.txt_note_detail)
    TextView txtNoteDetail;
    @Bind(R.id.txt_from_account_name)
    TextView txtFromAccountName;
    @Bind(R.id.txt_date_spending)
    TextView txtDate;
    @Bind(R.id.txt_receiver)
    TextView txtReceiver;
    @Bind(R.id.txt_trip)
    TextView txtEvent;
    @Bind(R.id.txt_receive)
    TextView txtReceive;
    @Bind(R.id.ll_from_account)
    LinearLayout llFromAccount;
    @Bind(R.id.ll_receiver)
    LinearLayout llReceiver;
    @Bind(R.id.ll_spending_item)
    LinearLayout llSendingItem;
    @Bind(R.id.ll_receive_item)
    LinearLayout llReceiveItem;
    @Bind(R.id.ll_to_account)
    LinearLayout llToAccount;
    @Bind(R.id.txt_to_account_name)
    TextView txtToAccountName;
    @Bind(R.id.ll_trip)
    LinearLayout llEvent;
    @Bind(R.id.edt_transfer_fee)
    EditText edtTransferFee;
    @Bind(R.id.ll_transfer_fee)
    LinearLayout llTransferFee;
}
