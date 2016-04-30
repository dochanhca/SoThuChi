package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListAccountAdapter;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.utility.Currency;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class AccountFragment extends ListFragment implements NoteFragment.OnPassDataFromNote {

    private ArrayList<Account> arrAccount;
    private double totalMoney;
    private ListAccountAdapter listAccountAdapter;
    private String ACCOUNT = "account fragment";
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Receive a Bundle from main activity
        arrAccount = getArguments().getParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_MAIN);
        totalMoney = getArguments().getDouble(MainActivity.TOTAL_MONEY_FROM_MAIN);
        //Receive a Bundle from note fragment

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
//        money = getArguments().getDouble(NoteFragment.MONEY_FROM_NOTE);
//        accountName = getArguments().getString(NoteFragment.ACCOUNT_NAME_FROM_NOTE);
        // Inflate layout for AccountFragment
        View v = inflater.inflate(R.layout.fr_account, container, false);
        ButterKnife.bind(this, v);
        imgBack.setVisibility(View.GONE);

        String sumMoney = Currency.getCurrency(totalMoney);
        txtSum.setText("Tổng còn: " + sumMoney + " "+getResources().getString(R.string.currency));
        listAccountAdapter = new ListAccountAdapter(getActivity(), arrAccount);
        lvAccount.setAdapter(listAccountAdapter);
        Log.e(MainActivity.TAG, "Account Fragment created");
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        Log.e(ACCOUNT, "view destroy");

    }

//    @Override
//    public void onDataReceivedFromNote(double money, Account account) {
//
//    }

    @Override
    public void onDataInsertToDBFromNote(Pay pay) {
        Log.e(ACCOUNT, "money= " + pay.getMoney());
        if (arrAccount.contains(pay.getAccount())) {
            Account newAccount = new Account(pay.getAccount().getAccountID(), pay.getAccount().getAccountName(),
                    pay.getAccount().getFirstMoney(), pay.getAccount().getCurrentMoney() + pay.getMoney(), pay.getAccount().getNote(),
                    pay.getAccount().getAccountType());
            arrAccount.set(arrAccount.indexOf(pay.getAccount()), newAccount);
            listAccountAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.img_add)
    public void gotoAddAccount() {
        Fragment addAccountFragment = new AddAccountFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_account_container, addAccountFragment, AccountContainerFragment.ADD_ACCOUNT_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);

        transaction.commit();

    }

    @Bind(android.R.id.list)
    ListView lvAccount;
    @Bind(R.id.txt_sum)
    TextView txtSum;
    @Bind(R.id.img_back)
    ImageView imgBack;
}
