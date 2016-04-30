package com.example.anhlamrduc.sothuchi.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.adapter.MyPagerAdapter;
import com.example.anhlamrduc.sothuchi.asynctask.DBInsertPay;
import com.example.anhlamrduc.sothuchi.db.AccountController;
import com.example.anhlamrduc.sothuchi.db.ReceiverController;
import com.example.anhlamrduc.sothuchi.db.SpendingController;
import com.example.anhlamrduc.sothuchi.fragment.AccountContainerFragment;
import com.example.anhlamrduc.sothuchi.fragment.DescriptionFragment;
import com.example.anhlamrduc.sothuchi.fragment.LimitFragment;
import com.example.anhlamrduc.sothuchi.fragment.ListSpendItemFragment;
import com.example.anhlamrduc.sothuchi.fragment.NoteContainerFragment;
import com.example.anhlamrduc.sothuchi.fragment.NoteFragment;
import com.example.anhlamrduc.sothuchi.fragment.ReceiverFragment;
import com.example.anhlamrduc.sothuchi.fragment.ReportFragment;
import com.example.anhlamrduc.sothuchi.fragment.SelectAccountFragment;
import com.example.anhlamrduc.sothuchi.fragment.TripFragment;
import com.example.anhlamrduc.sothuchi.fragment.UtilitiesFragment;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NoteFragment.OnPassDataFromNote,
        ListSpendItemFragment.OnDataPassFromListSpendItem,
        DescriptionFragment.OnDataPassDataFromDescription,
        SelectAccountFragment.OnDataPassFromSelectAccount,
        ReceiverFragment.onDataPassFromReceiver,
        TripFragment.OnDataPassFromTrip{

    public static final String TAG = "Main activity: ";
    public static final String LIST_ACCOUNT_FROM_MAIN = "list of account";
    public static final String TOTAL_MONEY_FROM_MAIN = "total money";
    public static final String LIST_SPENDING_FROM_MAIN = "list of spending";
    public static final String SPENDING_RECEIVE_FROM_LIST_SPENDING = "spending received";
    public static final String DESCRIPTION_SPENDING = "description spending";
    public static final String ACCOUNT_FROM_SELECT_ACCOUNT = "account name";
    public static final String RECEIVER_NAME = "receiver name";
    public static final String LIST_RECEIVER_FROM_MAIN = "list of receiver";
    public static final String TRIP_OR_EVENT = "trip or event";

    private AccountController db_account;
    private SpendingController db_spending;
    private ReceiverController db_receiver;
    private MyPagerAdapter myPagerAdapter;

    private Bundle bundle_to_note;
    private Bundle bundle_to_account;
    private Bundle bundle_to_limit;
    private Bundle bundle_to_report;
    private Bundle bundle_to_utility;

    private SpendingItem spendingReceived;
    private Account accountReceived;
    private String descriptionReceived = "";
    private String receiverReceived = "";
    private String tripReceived = "";

    private FragmentManager fragmentManager;

    @Bind(R.id.viewpager)
    ViewPager viewPager;
    @Bind(R.id.tabs)
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        db_account = new AccountController(this);
        db_spending = new SpendingController(this);
        db_receiver = new ReceiverController(this);
        //Send data to Fragment via bundle
        putBundleToNote();
        putBundleToAcount();

        fragmentManager = getSupportFragmentManager();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        Log.e(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        db_account.close();
        db_spending.close();
    }

    @Override
    public void onDataInsertToDBFromNote(Pay pay) {
        Log.e(TAG, "Pay received");
        final ProgressDialog dialog = new ProgressDialog(this);
        DBInsertPay dbInsertPay = new DBInsertPay(this) {
            @Override
            protected void onPreExecute() {
//                dialog.setTitle("Wait");
//                dialog.setMessage("Đang ghi");
                dialog.show();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(MainActivity.this, getString(R.string.write_success), Toast.LENGTH_SHORT).show();
            }
        };
        dbInsertPay.execute(pay);
        myPagerAdapter.onDataInsertToDBFromNote(pay);
    }

    @Override
    public void onDataReceivedFromListSpendItem(SpendingItem spending) {
        spendingReceived = spending;
        replaceView(R.id.fr_list_spend_item_container);
    }

    @Override
    public void onReceivedDataFromDescription(String description) {
        descriptionReceived = description;
        replaceView(R.id.fr_description);
    }

    @Override
    public void onDataReceivedFromSelectAccount(Account account) {
        accountReceived = account;
        replaceView(R.id.fr_account);
    }

    @Override
    public void onReceivedDataFromReceiver(String receiver) {
        receiverReceived = receiver;
        replaceView(R.id.fr_receiver);
    }

    @Override
    public void onReceivedDataFromTrip(String trip) {
        tripReceived = trip;
        replaceView(R.id.fr_trip);
    }

    /**
     * Send data to Fragment
     */
    private void putBundleToAcount() {
        //
        bundle_to_account = new Bundle();
        bundle_to_account.putParcelableArrayList(LIST_ACCOUNT_FROM_MAIN, getListAccount());
        bundle_to_account.putDouble(TOTAL_MONEY_FROM_MAIN, getSumMoney());
    }

    private void putBundleToNote() {
        bundle_to_note = new Bundle();
//        bundle_to_note.putParcelableArrayList(LIST_ACCOUNT_FROM_MAIN, getListAccount());
        bundle_to_note.putParcelableArrayList(LIST_SPENDING_FROM_MAIN, getListSpending());
        bundle_to_note.putParcelableArrayList(LIST_RECEIVER_FROM_MAIN, getListReceiver());
        bundle_to_note.putParcelable(ACCOUNT_FROM_SELECT_ACCOUNT, getListAccount().get(0));
        //
        bundle_to_note.putParcelable(SPENDING_RECEIVE_FROM_LIST_SPENDING, spendingReceived);
        bundle_to_note.putString(TRIP_OR_EVENT, tripReceived);
        bundle_to_note.putString(RECEIVER_NAME, receiverReceived);
        bundle_to_note.putParcelable(ACCOUNT_FROM_SELECT_ACCOUNT, accountReceived);
        bundle_to_note.putString(DESCRIPTION_SPENDING, descriptionReceived);
    }

    /**
     * Add Fragment to ViewPagerAdapter
     *
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.addFrag(new NoteContainerFragment(), "Ghi chép", bundle_to_note);
        myPagerAdapter.addFrag(new AccountContainerFragment(), "Tài khoản", bundle_to_account);
        myPagerAdapter.addFrag(new LimitFragment(), "Hạn mức chi", bundle_to_limit);
        myPagerAdapter.addFrag(new ReportFragment(), "Báo cáo", bundle_to_report);
        myPagerAdapter.addFrag(new UtilitiesFragment(), "Tiện ích", bundle_to_utility);
        viewPager.setAdapter(myPagerAdapter);
    }

    /**
     * Customize Title And Icon for Tabs
     */
    private void setupTabIcons() {

        tabLayout.getTabAt(0).setCustomView(R.layout.tab_layout_note);
        //First Tab isn't selected when launch app, i have to setSelected = true.
        tabLayout.getTabAt(0).getCustomView().setSelected(true);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_layout_account);
        tabLayout.getTabAt(2).setCustomView(R.layout.tab_layout_limit);
        tabLayout.getTabAt(3).setCustomView(R.layout.tab_layout_report);
        tabLayout.getTabAt(4).setCustomView(R.layout.tab_layout_utility);
    }

    private void replaceView(int cotainerViewID) {
        NoteContainerFragment noteContainerFragment = new NoteContainerFragment();
        putBundleToNote();
        noteContainerFragment.setArguments(bundle_to_note);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(cotainerViewID, noteContainerFragment, NoteContainerFragment.NOTE_CONTAINER_FRAG);
        transaction.commit();
        fragmentManager.popBackStack();
    }

    public ArrayList<Account> getListAccount() {

        ArrayList<Account> arr = db_account.getListAccount();
        return arr;
    }

    public ArrayList<SpendingItem> getListSpending() {
        ArrayList<SpendingItem> arr = db_spending.getListSpending();
        return arr;
    }

    private ArrayList<? extends Parcelable> getListReceiver() {
        ArrayList arr = db_receiver.getListReceiver();
        return arr;
    }

    public long insertAReceiver(Receiver receiver) {
        return db_receiver.addReceiver(receiver);
    }

    public Receiver getAReceiver(int id) {
        Receiver receiver = db_receiver.getReceiverByID(id);
        return receiver;
    }


    private double getSumMoney() {
        db_account = new AccountController(this);
        return db_account.getSumMoney();
    }
}
