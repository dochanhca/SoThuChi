package com.example.anhlamrduc.sothuchi.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.adapter.MyPagerAdapter;
import com.example.anhlamrduc.sothuchi.asynctask.DBInsertAAccount;
import com.example.anhlamrduc.sothuchi.asynctask.DBInsertAReceiveItem;
import com.example.anhlamrduc.sothuchi.asynctask.DBInsertAReceiveMoney;
import com.example.anhlamrduc.sothuchi.asynctask.DBInsertASpendingItem;
import com.example.anhlamrduc.sothuchi.asynctask.DBInsertATransfer;
import com.example.anhlamrduc.sothuchi.asynctask.DBInsertPay;
import com.example.anhlamrduc.sothuchi.asynctask.DBUpdateAccount;
import com.example.anhlamrduc.sothuchi.asynctask.DBUpdateAccountAmount;
import com.example.anhlamrduc.sothuchi.fragment.AccountContainerFragment;
import com.example.anhlamrduc.sothuchi.fragment.AddAccountFragment;
import com.example.anhlamrduc.sothuchi.fragment.AddReceiveItemFragment;
import com.example.anhlamrduc.sothuchi.fragment.AddSpendItemFragment;
import com.example.anhlamrduc.sothuchi.fragment.LimitFragment;
import com.example.anhlamrduc.sothuchi.fragment.NoteContainerFragment;
import com.example.anhlamrduc.sothuchi.fragment.NoteFragment;
import com.example.anhlamrduc.sothuchi.fragment.ReportContainerFragment;
import com.example.anhlamrduc.sothuchi.fragment.UtilitiesFragment;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Event;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.example.anhlamrduc.sothuchi.item.Receiver;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.example.anhlamrduc.sothuchi.item.Transfer;
import com.example.anhlamrduc.sothuchi.utils.MyService;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NoteFragment.OnPassDataFromNote,
        AddSpendItemFragment.OnPassDataFromAddSpendItem,
        AddReceiveItemFragment.OnPassDataFromAddReceiveItem,
        AddAccountFragment.OnPassDataFromAddAccount {

    public static final String TAG = "Main activity: ";
    public static final String LIST_ACCOUNT_FROM_DB = "list of account";
    public static final String MONEY_FROM_NOTE = "money";
    public static final String LIST_SPENDING_FROM_DB = "list of spending";
    public static final String SPENDING_ITEM_FROM_LIST_SPENDING = "spending received";
    public static final String DESCRIPTION_SPENDING = "description spending";
    public static final String FROM_ACCOUNT_SELECTED = "From account name";
    public static final String RECEIVER_NAME = "receiver name";
    public static final String LENDER = "lender";
    public static final String LIST_RECEIVER_FROM_MAIN = "list of receiver";
    public static final String TRIP_OR_EVENT = "trip or event";
    public static final String TRANSACTION_DATE = "pay date";
    public static final String DEFAULT_ACCOUNT = "default account";
    public static final String TO_ACCOUNT_SELECTED = "To account name";
    public static final String TRANSFER_FEE_FROM_NOTE = "Transfer fee";
    public static final String LIST_RECEIVE_ITEM_FROM_DB = "list receive item";
    public static final String RECEIVE_ITEM_NAME_FROM_LIST_RECEIVE = "receive item name";
    public static final String SPENDING_ITEM_PARENT_NAME = "Spending Item Parent";
    public static final String NEW_SPENDING_ITEM = "New spending item";
    public static final String NEW_RECEIVE_ITEM = "New receive item";
    public static final String FIRST_MONEY_FROM_ADD_ACCOUNT = "Frist money from add count";
    public static final String NEW_ACCOUNT = "New account";
    public static final String LIST_RECEIVER_FROM_DB = "List receiver from db";
    public static final String LIST_EVENT_FROM_DB = "List event form db";
    public static final String NEW_EVENT = "New event";
    public static final String NEW_RECEIVER = "New receiver";
    public static final String LIST_PAY_FROM_DB = "List pay from db";
    public static final String LIST_RECEIVE_MONEY_FROM_DB = "List receive money from db";
    public static final String NEW_PAY = "New pay";
    public static final String NEW_RECEIVE_MONEY = "New Receive money";

    private MyPagerAdapter myPagerAdapter;

    private Bundle bundle_to_note;
    private Bundle bundle_to_account;
    private Bundle bundle_to_limit;
    private Bundle bundle_to_report;

    private ArrayList<SpendingItem> listSpendingItem = new ArrayList<>();
    private ArrayList<ReceiveItem> listReceiveItem = new ArrayList<>();
    private ArrayList<Account> listAccount = new ArrayList<>();
    private ArrayList<Event> listEvent = new ArrayList<>();
    private ArrayList<Receiver> listReceiver = new ArrayList<>();
    private ArrayList<Pay> listPay = new ArrayList<>();
    private ArrayList<Income> listIncome = new ArrayList<>();

    private ProgressDialog dialog;
    private boolean douubleBackPressed = false;

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

        startService(new Intent(this, MyService.class));
        dialog = new ProgressDialog(this);

        listReceiveItem = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);
        listSpendingItem = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB);
        listAccount = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_DB);
        listReceiver = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB);
        listEvent = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB);
//        listPay = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_PAY_FROM_DB);
//        listIncome = getIntent().getExtras().getParcelableArrayList(MainActivity.LIST_RECEIVE_MONEY_FROM_DB);
        //Send data to Fragment via bundle
        putBundleToNote();
        putBundleToAcount();
        putBundleToReport();

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
    }

    /**
     * Receive a pay from NoteFragment and insert to db with asynctask
     *
     * @param pay
     */
    @Override
    public void onPayInsertToDBFromNote(Pay pay) {
        myPagerAdapter.onPayInsertToDBFromNote(pay);
        Log.e(TAG, "Pay received");
        double money = pay.getAccount().getCurrentMoney() - pay.getMoney();
        pay.getAccount().setCurrentMoney(money);
        /* Insert a new pay */
        DBInsertPay dbInsertPay = new DBInsertPay(this) {
            @Override
            protected void onPreExecute() {
                dialog.show();
            }
        };
        dbInsertPay.execute(pay);
        /* Update amount of account*/
        DBUpdateAccountAmount dbUpdateAccountAmount = new DBUpdateAccountAmount(this) {
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(MainActivity.this, getString(R.string.write_success), Toast.LENGTH_SHORT).show();
            }
        };
        dbUpdateAccountAmount.execute(pay.getAccount());

    }

    /**
     * Receive a Income from NoteFragment and insert to db with asynctask
     *
     * @param income
     */
    @Override
    public void onReceiveMoneyInsertToDBFromNote(Income income) {
        myPagerAdapter.onReceiveMoneyInsertToDBFromNote(income);
        double money = income.getAccount().getCurrentMoney() + income.getAmount();
        income.getAccount().setCurrentMoney(money);
//        final ProgressDialog dialog = new ProgressDialog(this);
        /* Insert New Receive Money*/
        DBInsertAReceiveMoney dbInsertAReceiveMoney = new DBInsertAReceiveMoney(this) {
            @Override
            protected void onPreExecute() {
                dialog.show();
            }
        };
        dbInsertAReceiveMoney.execute(income);
        /* Update amount of account */
        DBUpdateAccountAmount dbUpdateAccountAmount = new DBUpdateAccountAmount(this) {
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(MainActivity.this, getString(R.string.write_success), Toast.LENGTH_SHORT).show();
            }
        };
        dbUpdateAccountAmount.execute(income.getAccount());

    }

    /**
     * Receive a transfer from NoteFragment and insert to db with asyntask
     *
     * @param transfer
     */
    @Override
    public void onTransferInsertToDBFromNote(Transfer transfer) {
        /* Insert a new transfer */
        DBInsertATransfer dbInsertATransfer = new DBInsertATransfer(this) {
            @Override
            protected void onPreExecute() {
                dialog.show();
            }
        };
        dbInsertATransfer.execute(transfer);
        /* Update amount of send account*/
        double amountSendAccount = transfer.getFromAccount().getCurrentMoney() - transfer.getAmount()
                - transfer.getTransferFee();
        transfer.getFromAccount().setCurrentMoney(amountSendAccount);
        DBUpdateAccountAmount dbUpdateAccountAmount = new DBUpdateAccountAmount(this);
        dbUpdateAccountAmount.execute(transfer.getFromAccount());
        /*Update amount of receive account*/
        double amountReceiveAccount = transfer.getToAccount().getCurrentMoney() + transfer.getAmount();
        transfer.getToAccount().setCurrentMoney(amountReceiveAccount);
        DBUpdateAccountAmount dbUpdateAccountAmount1 = new DBUpdateAccountAmount(this) {
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, getString(R.string.write_success), Toast.LENGTH_SHORT).show();
            }
        };
        dbUpdateAccountAmount1.execute(transfer.getToAccount());
        myPagerAdapter.onTransferInsertToDBFromNote(transfer);
    }

    /**
     * Receive a spending item from AddSpendingItemFragment and insert to db with asynctask
     *
     * @param spendingItem
     */
    @Override
    public void onSpendItemInsertToDB(SpendingItem spendingItem) {
        /* Insert new SpendingItem */
        DBInsertASpendingItem dbInsertASpendingItem = new DBInsertASpendingItem(this) {
            @Override
            protected void onPreExecute() {
                dialog.show();
            }

            @Override
            protected void onPostExecute(Long aLong) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
            }
        };
        dbInsertASpendingItem.execute(spendingItem);
    }

    /**
     * Receive a receive item from AddReceiveItemFragment and insert to db with asynctask
     *
     * @param receiveItem
     */
    @Override
    public void onReceiveItemInsertToDBFromAddReceive(ReceiveItem receiveItem) {
        /* Insert new ReceiveItem */
        DBInsertAReceiveItem dbInsertAReceiveItem = new DBInsertAReceiveItem(this) {
            @Override
            protected void onPreExecute() {
                dialog.show();
            }

            @Override
            protected void onPostExecute(Long aLong) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
            }
        };
        dbInsertAReceiveItem.execute(receiveItem);

    }

    @Override
    public void onAccountInsertToDBFromAddAccount(Account account) {
        /* Insert new Account */
        DBInsertAAccount dbInsertAAccount = new DBInsertAAccount(this) {
            @Override
            protected void onPreExecute() {
                dialog.show();
            }

            @Override
            protected void onPostExecute(Long aLong) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, getString(R.string.insert_success), Toast.LENGTH_SHORT).show();
            }
        };
        dbInsertAAccount.execute(account);
    }

    @Override
    public void onAccountUpdateToDBFromAddAccount(Account account) {
         /* Update Account */
        DBUpdateAccount dbUpdateAccount = new DBUpdateAccount(this) {
            @Override
            protected void onPreExecute() {
                dialog.show();
            }

            @Override
            protected void onPostExecute(Long aLong) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, getString(R.string.update_success), Toast.LENGTH_SHORT).show();
            }
        };
        dbUpdateAccount.execute(account);
    }

    public static void startActivity(Activity activity, ArrayList<ReceiveItem> listReceiveItem,
                                     ArrayList<SpendingItem> listSpendingItem, ArrayList<Account> listAccount,
                                     ArrayList<Receiver> listReceiver, ArrayList<Event> listEvent) {
        Intent intent = new Intent(activity, MainActivity.class);
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB, listSpendingItem);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB, listReceiveItem);
        args.putParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_DB, listAccount);
        args.putParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB, listReceiver);
        args.putParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB, listEvent);
        intent.putExtras(args);

        activity.startActivity(intent);
    }

    /**
     * Send data to Fragment
     */
    private void putBundleToAcount() {
        //
        bundle_to_account = new Bundle();
        bundle_to_account.putParcelableArrayList(LIST_ACCOUNT_FROM_DB, listAccount);
    }

    private void putBundleToNote() {
        bundle_to_note = new Bundle();
        bundle_to_note.putParcelableArrayList(LIST_SPENDING_FROM_DB, listSpendingItem);
        bundle_to_note.putParcelableArrayList(LIST_RECEIVER_FROM_DB, listReceiver);
        bundle_to_note.putParcelableArrayList(LIST_RECEIVE_ITEM_FROM_DB, listReceiveItem);
        bundle_to_note.putParcelableArrayList(LIST_EVENT_FROM_DB, listEvent);
        bundle_to_note.putParcelable(DEFAULT_ACCOUNT, listAccount.get(0));
    }

    private void putBundleToReport() {
        bundle_to_report = new Bundle();
        bundle_to_report.putParcelableArrayList(LIST_SPENDING_FROM_DB, listSpendingItem);
        bundle_to_report.putParcelableArrayList(LIST_RECEIVER_FROM_DB, listReceiver);
        bundle_to_report.putParcelableArrayList(LIST_RECEIVE_ITEM_FROM_DB, listReceiveItem);
        bundle_to_report.putParcelableArrayList(LIST_EVENT_FROM_DB, listEvent);
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
        myPagerAdapter.addFrag(new ReportContainerFragment(), "Báo cáo", bundle_to_report);
        myPagerAdapter.addFrag(new UtilitiesFragment(), "Tiện ích", null);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
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
        transaction.replace(cotainerViewID, noteContainerFragment, NoteContainerFragment.NOTE_FRAG);
        transaction.commit();
        fragmentManager.popBackStack();
    }

//    public Receiver getAReceiver(int id) {
//        Receiver receiver = db_receiver.getReceiverByID(id);
//        return receiver;
//    }

    public ArrayList<Account> getListAccount() {
        return listAccount;
    }

    @Override
    public void onBackPressed() {
        if (douubleBackPressed || getSupportFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
            return;
        }
        this.douubleBackPressed = true;
        Toast.makeText(this, getString(R.string.double_press_to_exit_app), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                douubleBackPressed = false;
            }
        }, 2000);
    }
}
