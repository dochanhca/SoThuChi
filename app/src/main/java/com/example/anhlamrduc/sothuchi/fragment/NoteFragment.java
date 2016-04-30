package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.NoteSpinnerAdapter;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Borrower;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Lender;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class NoteFragment extends Fragment {
    @Bind(R.id.btn_write)
    Button btnWrite;
    @Bind(R.id.ll_list_account)
    LinearLayout llListAccount;
    @Bind(R.id.ll_spending_item)
    LinearLayout llSpendingItem;
    @Bind(R.id.ll_note_detail)
    LinearLayout llNoteDetail;
    @Bind(R.id.ll_note_account)
    LinearLayout llNoteAccount;
    @Bind(R.id.ll_date_spending)
    LinearLayout llDateSpending;
    @Bind(R.id.ll_receiver)
    LinearLayout llReceiver;
    @Bind(R.id.ll_trip)
    LinearLayout llTrip;
    @Bind(R.id.btn_add)
    Button btnAdd;
    private SimpleDateFormat hourFormatter = new SimpleDateFormat("hh:mm");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat fullDateFormat = new SimpleDateFormat("hh:mm dd/MM/yyyy");
    Date currentDay = Calendar.getInstance().getTime();

    private static final String TAG = "Note fragment:";
    private ArrayList<SpendingItem> arrSpending;
    private ArrayList<Parcelable> arrReceiver;

    private SpendingItem spendingReceived;
    private Account account;
    private String description = "";
    private String receiver = "";
    String payDate = fullDateFormat.format(currentDay);
    private String trip = "";
    double money = 0;

    OnPassDataFromNote onPassDataFromNote;
    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        @Override
        public void onDateTimeSet(Date date) {
            if (dateFormat.format(date).equals(dateFormat.format(currentDay))) {
                txtDate.setText(hourFormatter.format(date) + " " + getString(R.string.today));
            } else {
                txtDate.setText(hourFormatter.format(date) + " " + dateFormat.format(date));
            }
            payDate = fullDateFormat.format(date);
        }
    };

    public interface OnPassDataFromNote {
//        public void onDataReceivedFromNote(double money, Account account);

        public void onDataInsertToDBFromNote(Pay pay);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Receive a bundle
        arrSpending = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_MAIN);
        arrReceiver = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN);

        spendingReceived = getArguments().getParcelable(MainActivity.SPENDING_RECEIVE_FROM_LIST_SPENDING);
        description = getArguments().getString(MainActivity.DESCRIPTION_SPENDING);
        account = getArguments().getParcelable(MainActivity.ACCOUNT_FROM_SELECT_ACCOUNT);
        receiver = getArguments().getString(MainActivity.RECEIVER_NAME);
        trip = getArguments().getString(MainActivity.TRIP_OR_EVENT);

        View v = inflater.inflate(R.layout.fr_note, container, false);
        ButterKnife.bind(this, v);
        txtSpending.setText(spendingReceived.getSpendingItemName());
        txtNoteDetail.setText(description);
        txtAccountName.setText(account.getAccountName());
        txtDate.setText(hourFormatter.format(currentDay) + " " + getString(R.string.today));
        txtReceiver.setText(receiver);
        if (trip.length() > 0)
            txtTrip.setText(trip);
        //Set Onclick for Account Spinner
        spnNoteAction.setAdapter(new NoteSpinnerAdapter(getContext(), R.layout.spinner_dropdown_custom, getResources().getStringArray(R.array.spn_note_title)));
//        spnNoteAction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                account = (Account) parent.getItemAtPosition(position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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

    @OnClick(R.id.btn_add)
    public void addTransaction() {

        /* Insert a transaction to db */
        if (txtSpending.getText().length() < 1) {
            Toast.makeText(getContext(), getString(R.string.spending_cant_be_blank), Toast.LENGTH_LONG).show();
        } else if (receiver.length() < 1 && trip.length() < 1) {
            money = (edtAmount.getText().length() > 0 ? Double.parseDouble(edtAmount.getText().toString()) : 0);
            Pay pay = initiatePay(3, 1);
            onPassDataFromNote.onDataInsertToDBFromNote(pay);
        }
    }

    @OnClick(R.id.ll_spending_item)
    public void showListSpendingItem() {
        ListSpendItemContainerFragment listSpendItemContainerFragment = new ListSpendItemContainerFragment();
        //put bundle
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_MAIN, arrSpending);
        listSpendItemContainerFragment.setArguments(args);
        //
        replaceView(listSpendItemContainerFragment, NoteContainerFragment.LIST_SPEND_ITEM_FRAG);

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

    @OnClick(R.id.ll_note_account)
    public void goToSelectAccountFrag() {
        SelectAccountFragment selectAccountFragment = new SelectAccountFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fr_note_container, selectAccountFragment,
                NoteContainerFragment.SELECT_ACCOUNT_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
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
//        args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN, arrReceiver);
        tripFragment.setArguments(args);
        //
        replaceView(tripFragment, NoteContainerFragment.TRIP_FRAGMENT);
    }

    /**
     * Initialize a Pay send to the Main to save to db
     * @param receverID
     * @param eventID
     * @return
     */
    private Pay initiatePay(int receverID, int eventID) {
        SpendingItem spending = new SpendingItem();
        spending.setSpendingItemID(spending.getSpendingItemID());

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
        pay.setPayDate(payDate);
        pay.setLender(lender);
        pay.setReceiver(receiver);
        pay.setBorrower(borrower);
        pay.setEvent(event);
        pay.setAccount(account);
        return pay;
    }

    /**
     * Replace NoteFragment with difference fragment
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

    @Bind(R.id.edt_amount)
    EditText edtAmount;
    @Bind(R.id.spn_note_action)
    Spinner spnNoteAction;
    @Bind(R.id.txt_spending)
    TextView txtSpending;
    @Bind(R.id.txt_note_detail)
    TextView txtNoteDetail;
    @Bind(R.id.txt_account_name)
    TextView txtAccountName;
    @Bind(R.id.txt_date_spending)
    TextView txtDate;
    @Bind(R.id.txt_receiver)
    TextView txtReceiver;
    @Bind(R.id.txt_trip)
    TextView txtTrip;
}
