package com.example.anhlamrduc.sothuchi.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.adapter.MyPagerAdapter;
import com.example.anhlamrduc.sothuchi.db.AccountController;
import com.example.anhlamrduc.sothuchi.fragment.AccountFragment;
import com.example.anhlamrduc.sothuchi.fragment.LimitFragment;
import com.example.anhlamrduc.sothuchi.fragment.NoteFragment;
import com.example.anhlamrduc.sothuchi.fragment.ReportFragment;
import com.example.anhlamrduc.sothuchi.fragment.UtilitiesFragment;
import com.example.anhlamrduc.sothuchi.item.TaiKhoan;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String MAIN = "Main activity: ";
    public static final String LIST_ACCOUNT_FROM_MAIN = "list of account";
    public static final String TOTAL_MONEY_FROM_MAIN = "total money";
    public static final String ARR_ACCOUNT_NAME_FROM_MAIN = "account name";
    private AccountController db_account;
    private Bundle bundle_to_note;
    private Bundle bundle_to_account;
    private Bundle bundle_to_limit;
    private Bundle bundle_to_report;
    private Bundle bundle_to_utility;

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
        //Send data to Fragment via bundle
        putBundle();

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        Log.e(MAIN, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(MAIN, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(MAIN, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(MAIN, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(MAIN, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(MAIN, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(MAIN, "onDestroy");
        db_account.close();
    }

    /**
     * Send data to Fragment
     */
    private void putBundle() {
        //
        bundle_to_account = new Bundle();
        bundle_to_account.putParcelableArrayList(LIST_ACCOUNT_FROM_MAIN, getListAccount());
        bundle_to_account.putDouble(TOTAL_MONEY_FROM_MAIN, getSumMoney());
        //
        bundle_to_note = new Bundle();
        bundle_to_note.putStringArray(ARR_ACCOUNT_NAME_FROM_MAIN, getAccountName());
        //
    }

    /**
     * Add Fragment to ViewPagerAdapter
     * @param viewPager
     */
    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        myPagerAdapter.addFrag(new NoteFragment(), "Ghi chép", bundle_to_note);
        myPagerAdapter.addFrag(new AccountFragment(), "Tài khoản", bundle_to_account);
        myPagerAdapter.addFrag(new LimitFragment(), "Hạn mức chi", bundle_to_limit);
        myPagerAdapter.addFrag(new ReportFragment(), "Báo cáo", bundle_to_report);
        myPagerAdapter.addFrag(new UtilitiesFragment(), "Tiện ích", bundle_to_utility);
        viewPager.setAdapter(myPagerAdapter);
    }

    /**
     * Customize Title And Icon for Tabs
     */
    private void setupTabIcons() {
        TextView tab_note = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        tab_note.setText("Ghi chép");
        tab_note.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_note, 0, 0);
        tabLayout.getTabAt(0).setCustomView(R.layout.tab_layout_account);

        TextView tab_account = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        tab_account.setText("Tài khoản");
        tab_account.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_account, 0, 0);
        tabLayout.getTabAt(1).setCustomView(R.layout.tab_layout_account);

        TextView tab_limit = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        tab_limit.setText("Hạn mức chi");
        tab_limit.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_limit, 0, 0);
        tabLayout.getTabAt(2).setCustomView(R.layout.tab_layout_account);

        TextView tab_report = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        tab_report.setText("Báo cáo");
        tab_report.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_report, 0, 0);
        tabLayout.getTabAt(3).setCustomView(R.layout.tab_layout_account);

        TextView tab_utility = (TextView) LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);
        tab_utility.setText("Tiện ích");
        tab_utility.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_utility, 0, 0);
        tabLayout.getTabAt(4).setCustomView(R.layout.tab_layout_account);
    }


    /**
     * Retrieve all account from TaiKhoan Table
     *
     * @return
     */
    private String[] getAccountName() {

        ArrayList<TaiKhoan> arr = db_account.getListAccount();
        String accountName[] = new String[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            accountName[i] = arr.get(i).getTenTaiKhoan();
        }
        return accountName;
    }

    /**
     * Retrieve all account from TaiKhoan Table
     *
     * @return
     */
    private ArrayList<TaiKhoan> getListAccount() {

        ArrayList<TaiKhoan> arr = db_account.getListAccount();
        return arr;
    }

    /**
     * Get sum currently money
     *
     * @return sum
     */
    private double getSumMoney() {
        db_account = new AccountController(this);
        return db_account.getSumMoney();
    }
}
