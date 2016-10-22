package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListAccountAdapter;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllAccountType;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.AccountType;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.Transfer;
import com.example.anhlamrduc.sothuchi.utils.Currency;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class AccountFragment extends BaseFragment implements NoteFragment.OnPassDataFromNote {

    public static final String LIST_ACCOUNT_TYPE = "list account type";
    public static final String LIST_ACCOUNT = "list account";
    public static final String ACCOUNT_EDIT = "account edit";
    public static final String EDIT_MODE = "edit mode";

    private ArrayList<Account> accounts;
    private double totalMoney;
    private ListAccountAdapter listAccountAdapter;
    private String ACCOUNT = "account fragment";
    private ViewPager viewPager;
    private boolean selectMode;

    private ListAccountAdapter.OnAccountChildItemClickListener onAccountChildItemClickListener = new ListAccountAdapter.OnAccountChildItemClickListener() {
        @Override
        public void onEditClick(int position) {
            showAddAccountFragment(position);
        }
    };

    @Override
    protected int layoutID() {
        return R.layout.fr_account;
    }

    @Override
    protected void handleData() {
        //Receive a Bundle from main activity
        accounts = getArguments().getParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_DB);
        totalMoney = getSumMoney();

        //Receive a New Account
        if (getNewAccount() != null) {
            Account account = getNewAccount();
            accounts.add(account);
            totalMoney = getSumMoney();

            getEditor().remove(MainActivity.NEW_ACCOUNT);
            getEditor().commit();
        }

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
    }

    @Override
    protected void initView() {
        imgBack.setVisibility(View.GONE);

        String sumMoney = Currency.getCurrency(totalMoney);
        txtSum.setText(getString(R.string.sum_title) + " " + sumMoney + " " + getResources().getString(R.string.currency));
        listAccountAdapter = new ListAccountAdapter(getActivity(), accounts, false);
        listAccountAdapter.setOnAccountChildItemClickListener(onAccountChildItemClickListener);
        lvAccount.setAdapter(listAccountAdapter);

    }


    @Override
    public void onPayInsertToDBFromNote(Pay pay) {
        Log.e(ACCOUNT, "money= " + pay.getMoney());
        updateListAccount(pay.getAccount(), pay.getMoney());
    }

    private void updateListAccount(Account account, double money) {
        if (accounts.contains(account)) {
            accounts.set(accounts.indexOf(account), account);
            listAccountAdapter.notifyDataSetChanged();
            totalMoney -= money;
            String sumMoney = Currency.getCurrency(totalMoney);
            txtSum.setText(getString(R.string.sum_title) + " " + sumMoney + " " + getResources().getString(R.string.currency));
        }
    }

    @Override
    public void onReceiveMoneyInsertToDBFromNote(Income income) {
        Log.e(ACCOUNT, "money= " + income.getAmount());
        if (accounts.contains(income.getAccount())) {
            updateListAccount(income.getAccount(), -income.getAmount());
        }
    }

    @Override
    public void onTransferInsertToDBFromNote(Transfer transfer) {
        if (accounts.contains(transfer.getFromAccount()) && accounts.contains(transfer.getToAccount())) {
            double amount = transfer.getAmount() + transfer.getTransferFee();
            updateListAccount(transfer.getFromAccount(), amount);
            updateListAccount(transfer.getToAccount(), -transfer.getAmount());
        }
    }

    @OnClick(R.id.img_add)
    public void gotoAddAccount() {
        final Fragment addAccountFragment = new AddAccountFragment();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        DBGetAllAccountType dbGetAllAccountType = new DBGetAllAccountType(getContext()) {
            @Override
            protected void onPostExecute(ArrayList<AccountType> accountTypes) {
                super.onPostExecute(accountTypes);
                Bundle args = new Bundle();
                args.putParcelableArrayList(LIST_ACCOUNT_TYPE, accountTypes);
                args.putParcelableArrayList(LIST_ACCOUNT, accounts);
                addAccountFragment.setArguments(args);

                transaction.replace(R.id.fragment_account_container, addAccountFragment, AccountContainerFragment.ADD_ACCOUNT_FRAG);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

                transaction.commit();

            }
        };
        dbGetAllAccountType.execute();
    }

    private double getSumMoney() {
        double sum = 0;
        for (int i = 0; i < accounts.size(); i++) {
            sum += accounts.get(i).getCurrentMoney();
        }
        return sum;
    }

    private void showAddAccountFragment(final int position) {
        final Fragment addAccountFragment = new AddAccountFragment();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        DBGetAllAccountType dbGetAllAccountType = new DBGetAllAccountType(getContext()) {
            @Override
            protected void onPostExecute(ArrayList<AccountType> accountTypes) {
                super.onPostExecute(accountTypes);
                Bundle args = new Bundle();
                args.putParcelableArrayList(LIST_ACCOUNT_TYPE, accountTypes);
                args.putParcelableArrayList(LIST_ACCOUNT, accounts);
                args.putParcelable(ACCOUNT_EDIT, accounts.get(position));
                args.putBoolean(EDIT_MODE, true);
                addAccountFragment.setArguments(args);

                transaction.replace(R.id.fragment_account_container, addAccountFragment, AccountContainerFragment.ADD_ACCOUNT_FRAG);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);

                transaction.commit();
            }
        };
        dbGetAllAccountType.execute();
    }

    @Bind(android.R.id.list)
    ListView lvAccount;
    @Bind(R.id.txt_sum)
    TextView txtSum;
    @Bind(R.id.img_back)
    ImageView imgBack;
}
