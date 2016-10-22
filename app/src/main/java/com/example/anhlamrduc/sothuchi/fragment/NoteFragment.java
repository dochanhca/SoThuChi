package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.NoteSpinnerAdapter;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetEventByName;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetReceiverByName;
import com.example.anhlamrduc.sothuchi.asynctask.DBInsertAEvent;
import com.example.anhlamrduc.sothuchi.asynctask.DBInsertAReceiver;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Borrower;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.item.Lender;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.example.anhlamrduc.sothuchi.item.Transfer;
import com.example.anhlamrduc.sothuchi.utils.Constant;
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
    public static final String TRANSACTION_HISTORY = "Transaction History";
    public static final String TRANSFER = "Transfer";
    public static final String INCOME = "Income";
    public static final String PAY = "Pay";
    public static final String EDIT_MODE = "EditMode";

    Date currentDay = Calendar.getInstance().getTime();
    Gson gson = new Gson();

    private ArrayList<SpendingItem> spendingItems;
    private ArrayList<ReceiveItem> receiveItems;
    private ArrayList<Receiver> receivers;
    private ArrayList<Event> events;

    private SpendingItem spendingReceived = new SpendingItem();
    private ReceiveItem receiveItemReceived = new ReceiveItem();
    private Account fromAccount, toAccount;
    private String description = "";
    private String receiverName = "";
    private Date transactionDate = currentDay;
    private String event = "";
    private double money = 0, transferFee = 0;

    private Receiver receiverFromDB;
    private Event eventFromDB;
    private long receiverID = 3;
    private long eventID = 1;
    private int transactionHistory;

    OnPassDataFromNote onPassDataFromNote;

    public interface OnPassDataFromNote {
        public void onPayInsertToDBFromNote(Pay pay);

        public void onReceiveMoneyInsertToDBFromNote(Income income);

        public void onTransferInsertToDBFromNote(Transfer transfer);
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            setTextFromDate(date);
            transactionDate = date;
            setTransactionDate(transactionDate);

        }
    };

//    private TextView.OnEditorActionListener onAmountEditorActionListener = new TextView.OnEditorActionListener() {
//        @Override
//        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//            if (actionId == EditorInfo.IME_ACTION_DONE && edtAmount.getText().length() > 0) {
//                Double money = Double.valueOf(edtAmount.getText().toString());
//                String amountJson = gson.toJson(money);
//                getEditor().putString(MainActivity.MONEY_FROM_NOTE, amountJson);
//                getEditor().commit();
//            }
//            return false;
//        }
//    };
//
//    private TextView.OnEditorActionListener onTransferFeeActionListener = new TextView.OnEditorActionListener() {
//        @Override
//        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//            if (actionId == EditorInfo.IME_ACTION_DONE && edtTransferFee.getText().length() > 0) {
//                Double money = Double.valueOf(edtTransferFee.getText().toString());
//                String amountJson = gson.toJson(money);
//                getEditor().putString(MainActivity.TRANSFER_FEE_FROM_NOTE, amountJson);
//                getEditor().commit();
//            }
//            return false;
//        }
//    };


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
        receivers = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB);
        events = getArguments().getParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB);

        if (getArguments().getBoolean(NoteFragment.EDIT_MODE, false))
            handleWithEditMode();

        Log.e(TAG, " onCreate view");
        if (getSpendingItem() != null)
            spendingReceived = getSpendingItem();
        if (getReceiveItem() != null)
            receiveItemReceived = getReceiveItem();
        if (getFromAccount() != null)
            fromAccount = getFromAccount();
        else
            fromAccount = getArguments().getParcelable(MainActivity.DEFAULT_ACCOUNT);
        if (getToAccount() != null)
            toAccount = getToAccount();
        else
            toAccount = getArguments().getParcelable(MainActivity.DEFAULT_ACCOUNT);
        if (getTransactionDate() != null)
            transactionDate = getTransactionDate();
        description = getDescription();
        money = getAmount();
        transferFee = getTransferFee();
        receiverName = getReceiver();
        event = getEvent();
    }

    @Override
    protected void initView() {

        txtSpending.setText(spendingReceived.getSpendingItemName());
        txtNoteDetail.setText(description);
        if (fromAccount != null)
            txtFromAccountName.setText(fromAccount.getAccountName());

        setTextFromDate(transactionDate);
        if (receiverName.length() > 0)
            txtReceiver.setText(receiverName);
        if (event.length() > 0)
            txtEvent.setText(event);
        if (money > 0)
            edtAmount.setText((long) money + "");
//        edtAmount.addTextChangedListener(new NumberTextWatcher(edtAmount));

//        edtAmount.setOnEditorActionListener(onAmountEditorActionListener);
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
            insertAIncome();
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
        args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN, receivers);
        receiverFragment.setArguments(args);
        //
        replaceView(receiverFragment, NoteContainerFragment.RECEIVER_FRAGMENT);
    }

    @OnClick(R.id.ll_trip)
    public void selectTrip() {
        TripFragment tripFragment = new TripFragment();
        //put bundle
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB, events);
        tripFragment.setArguments(args);
        replaceView(tripFragment, NoteContainerFragment.TRIP_FRAGMENT);
    }

    @OnClick(R.id.img_view_history)
    public void viewHistory() {
        TransactionContainerFragment transactionContainerFragment = new TransactionContainerFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB, spendingItems);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB, receiveItems);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB, receivers);
        args.putParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB, events);

        if (llSendingItem.getVisibility() == View.VISIBLE)
            args.putInt(NoteFragment.TRANSACTION_HISTORY, 0);
        else if (llReceiveItem.getVisibility() == View.VISIBLE)
            args.putInt(NoteFragment.TRANSACTION_HISTORY, 1);
        else
            args.putInt(NoteFragment.TRANSACTION_HISTORY, 2);

        transactionContainerFragment.setArguments(args);
        replaceView(transactionContainerFragment, NoteContainerFragment.TRANSACTION_FRAGMENT);

    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        getFragmentManager().popBackStack();
    }

    private void handleWithEditMode() {
        transactionHistory = getArguments().getInt(NoteFragment.TRANSACTION_HISTORY);
        llDelete.setVisibility(View.VISIBLE);
//        imgViewHistory.setVisibility(View.GONE);


        if (transactionHistory == 0) {
            showSpendingView();
            spnNoteAction.setSelection(0);

            Pay pay = getArguments().getParcelable(NoteFragment.PAY);
            edtAmount.setText(pay.getMoney() + "");

            txtSpending.setText(pay.getSpendingItem().getSpendingItemName());
//            setSpendingItem(pay.getSpendingItem());

            setAccount(pay.getAccount(), NoteFragment.FROM_ACCOUNT);
            txtFromAccountName.setText(pay.getAccount().getAccountName());

            setTransactionDate(pay.getPayDate());
            txtDate.setText(String.format(Constant.VND_FORMAT, pay.getPayDate()));

            setDescription(pay.getDescription());
            txtNoteDetail.setText(pay.getDescription());

            txtReceiver.setText(pay.getReceiver().getReceiverName());
            setReceiver(pay.getReceiver().getReceiverName());

            txtEvent.setText(pay.getEvent().getEventName());
            setEvent(pay.getEvent().getEventName());

        } else if (transactionHistory == 1) {
            showReceiveView();
            spnNoteAction.setSelection(1);

            Income income = getArguments().getParcelable(NoteFragment.INCOME);
            edtAmount.setText(income.getAmount() + "");

            txtSpending.setText(income.getReceiveItem().getReceiveItemName());
            setReceiveItem(income.getReceiveItem());

            setAccount(income.getAccount(), NoteFragment.TO_ACCOUNT);
            txtToAccountName.setText(income.getAccount().getAccountName());

            setTransactionDate(income.getReceiveDate());
            txtDate.setText(String.format(Constant.VND_FORMAT, income.getReceiveDate()));

            setDescription(income.getDescription());
            txtNoteDetail.setText(income.getDescription());

            txtEvent.setText(income.getEvent().getEventName());
            setEvent(income.getEvent().getEventName());

        } else {
            showTransferView();
            spnNoteAction.setSelection(2);

            Transfer transfer = getArguments().getParcelable(NoteFragment.TRANSFER);
            edtAmount.setText(transfer.getAmount() + "");
            edtTransferFee.setText(transfer.getTransferFee() + "");

            setAccount(transfer.getFromAccount(), NoteFragment.FROM_ACCOUNT);
            txtFromAccountName.setText(transfer.getFromAccount().getAccountName());

            setAccount(transfer.getToAccount(), NoteFragment.TO_ACCOUNT);
            txtToAccountName.setText(transfer.getToAccount().getAccountName());

            setTransactionDate(transfer.getTransferDate());
            txtDate.setText(String.format(Constant.VND_FORMAT, transfer.getTransferDate()));

            setDescription(transfer.getDescription());
            txtNoteDetail.setText(transfer.getDescription());

        }
    }

    private void insertAPay() {
         /* Insert a Pay to db */

        if (txtSpending.getText().length() < 1) {
            showAShortToask(getString(R.string.spending_cant_be_blank));
        } else if (txtReceiver.getText().length() < 1 && txtEvent.getText().length() < 1) {
            money = (edtAmount.getText().length() > 0 ? Double.parseDouble(edtAmount.getText().toString()) : 0);

            Receiver receiver = new Receiver((int) receiverID);
            Event event = new Event((int) eventID);
            Pay pay = initiatePay(receiver, event);
            onPassDataFromNote.onPayInsertToDBFromNote(pay);
            saveToSharedPreference(pay);
        } else if (txtReceiver.getText().length() > 0) {
            money = (edtAmount.getText().length() > 0 ? Double.parseDouble(edtAmount.getText().toString()) : 0);

            Event event = new Event((int) eventID);

            Pay pay = initiatePay(getCurrentReceiver(), event);

            onPassDataFromNote.onPayInsertToDBFromNote(pay);
            saveToSharedPreference(pay);
        } else if (txtEvent.getText().length() > 0) {

            Receiver receiver = new Receiver((int) receiverID);

            Pay pay = initiatePay(receiver, getCurrentEvent());

            onPassDataFromNote.onPayInsertToDBFromNote(pay);
            saveToSharedPreference(pay);
        } else if (txtEvent.getText().length() > 0 && txtReceiver.getText().length() > 0) {

            Pay pay = initiatePay(getCurrentReceiver(), getCurrentEvent());
            onPassDataFromNote.onPayInsertToDBFromNote(pay);
            saveToSharedPreference(pay);
        }

        txtSpending.setText("");
        txtReceiver.setText("");
        txtEvent.setText("");
        resetView();
    }

    private void saveToSharedPreference(Pay pay) {
        String payJson = gson.toJson(pay);
        getEditor().putString(MainActivity.NEW_PAY, payJson);
        getEditor().commit();
    }

    private Event getCurrentEvent() {
        /**/
        DBGetEventByName dbGetEventByName = new DBGetEventByName(getContext()) {
            @Override
            protected void onPostExecute(Event event) {
                super.onPostExecute(event);
                eventFromDB = event;
            }
        };
        dbGetEventByName.execute(txtEvent.getText().toString().trim());
        /**/
        if (eventFromDB == null) {
            DBInsertAEvent dbInsertAEvent = new DBInsertAEvent(getContext()) {
                @Override
                protected void onPostExecute(Long aLong) {
                    eventID = aLong;
                }
            };
            Event event = new Event();
            event.setEventName(txtEvent.getText().toString().trim());
            dbInsertAEvent.execute(event);
            /**/
            String eventJson = gson.toJson(event);
            getEditor().putString(MainActivity.NEW_EVENT, eventJson);
            getEditor().commit();
            return event;

        } else {
            /**/
            eventID = eventFromDB.getEventID();
            return eventFromDB;
        }
    }

    private Receiver getCurrentReceiver() {
        /**/
        DBGetReceiverByName dbGetReceiverByName = new DBGetReceiverByName(getContext()) {
            @Override
            protected void onPostExecute(Receiver receiver) {
                receiverFromDB = receiver;
            }
        };
        dbGetReceiverByName.execute(txtReceiver.getText().toString().trim());
        /**/
        if (receiverFromDB == null) {
            DBInsertAReceiver dbInsertAReceiver = new DBInsertAReceiver(getContext()) {
                @Override
                protected void onPostExecute(Long aLong) {
                    receiverID = aLong;
                }
            };
            /* */
            Receiver receiverInsert = new Receiver();
            receiverInsert.setReceiverName(txtReceiver.getText().toString().trim());
            dbInsertAReceiver.execute(receiverInsert);
            /**/
            String receiverJSON = gson.toJson(receiverInsert);
            getEditor().putString(MainActivity.NEW_RECEIVER, receiverJSON);
            getEditor().commit();
            return receiverInsert;
        } else {
            receiverID = receiverFromDB.getReceiverID();
            return receiverFromDB;
        }
    }

    private void insertATransfer() {
        if (edtAmount.getText().length() < 1 || edtAmount.getText().toString().equals("0")) {
            showAShortToask(getString(R.string.amount_must_greater_than_0));
        } else if (fromAccount.equals(toAccount)) {
            showAShortToask(getString(R.string.account_cant_same));
        } else {
            money = Double.parseDouble(edtAmount.getText().toString());
            transferFee = (edtTransferFee.getText().length() > 0 ?
                    Double.parseDouble(edtTransferFee.getText().toString()) : 0);
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
        String date = Constant.VN_DATE_FORMAT.format(transactionDate);
        transfer.setTransferDate(transactionDate);
        transfer.setTransferFee(transferFee);
        transfer.setFromAccount(fromAccount);
        transfer.setToAccount(toAccount);
        return transfer;
    }

    private void insertAIncome() {
        if (txtReceive.getText().length() < 1) {
            showAShortToask(getString(R.string.receive_cant_be_blank));
        } else if (txtEvent.getText().length() < 1) {
            money = (edtAmount.getText().length() > 0 ? Double.parseDouble(edtAmount.getText().toString()) : 0);
            Income income = initiateIncome(1);
            onPassDataFromNote.onReceiveMoneyInsertToDBFromNote(income);
            saveReceiveToSharePrefrence(income);
            txtReceive.setText("");
            txtEvent.setText("");
            resetView();
        } else if (txtEvent.getText().length() > 0) {
            money = (edtAmount.getText().length() > 0 ? Double.parseDouble(edtAmount.getText().toString()) : 0);
            getCurrentEvent();

            Income income = initiateIncome(eventID);
            onPassDataFromNote.onReceiveMoneyInsertToDBFromNote(income);
            saveReceiveToSharePrefrence(income);
            txtReceive.setText("");
            txtEvent.setText("");
            resetView();
        }
    }

    private void saveReceiveToSharePrefrence(Income income) {
        String receiveJson = gson.toJson(income);
        getEditor().putString(MainActivity.NEW_RECEIVE_MONEY, receiveJson);
        getEditor().commit();
    }

    private void resetView() {
        txtNoteDetail.setText("");
        edtAmount.setText("");
        getEditor().remove(MainActivity.TO_ACCOUNT_SELECTED);
        getEditor().remove(MainActivity.FROM_ACCOUNT_SELECTED);
        getEditor().remove(MainActivity.SPENDING_ITEM_FROM_LIST_SPENDING);
        getEditor().remove(MainActivity.DESCRIPTION_SPENDING);
        getEditor().commit();
    }


    /**
     * Initialize a Pay send to the Main to save to db
     *
     * @param receiver
     * @param event
     * @return a pay
     */
    private Pay initiatePay(Receiver receiver, Event event) {

        Lender lender = new Lender();
        lender.setLenderID(1);

        Borrower borrower = new Borrower();
        borrower.setBorrowerID(1);

        Pay pay = new Pay();
        pay.setSpendingItem(spendingReceived);
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
     * Initialize  a Income send to the Main to save to db
     *
     * @param eventID
     * @return a receiveMoney
     */
    private Income initiateIncome(long eventID) {
//        ReceiveItem receiveItem = new ReceiveItem();
//        receiveItem.setReceiveItemID(receiveItemReceived.getReceiveItemID());

        Event event = new Event();
        event.setEventID((int) eventID);

        Lender lender = new Lender();
        lender.setLenderID(1);

        Borrower borrower = new Borrower();
        borrower.setBorrowerID(1);

        Income income = new Income();
        income.setAmount(money);
        income.setDescription(description);
        income.setReceiveDate(transactionDate);
        income.setLender(lender);
        income.setBorrower(borrower);
        income.setReceiveItem(receiveItemReceived);
        income.setAccount(toAccount);
        income.setEvent(event);

        return income;
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
            edtTransferFee.setText((long) transferFee + "");
        txtToAccountName.setText(toAccount.getAccountName());
//        edtTransferFee.setOnEditorActionListener(onTransferFeeActionListener);
        // edtTransferFee.addTextChangedListener(new NumberTextWatcher(edtTransferFee));
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
            llEvent.setVisibility(View.VISIBLE);
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
            llEvent.setVisibility(View.VISIBLE);
        if (toAccount != null)
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
    @Bind(R.id.txt_account_name)
    TextView txtFromAccountName;
    @Bind(R.id.txt_date_spending)
    TextView txtDate;
    @Bind(R.id.txt_receiver)
    TextView txtReceiver;
    @Bind(R.id.txt_trip)
    TextView txtEvent;
    @Bind(R.id.txt_receive)
    TextView txtReceive;
    @Bind(R.id.txt_cancel)
    TextView txtCancel;
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
    @Bind(R.id.ll_delete)
    LinearLayout llDelete;
    @Bind(R.id.img_view_history)
    ImageView imgViewHistory;
}
