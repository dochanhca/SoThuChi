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
import com.example.anhlamrduc.sothuchi.item.TaiKhoan;
import com.example.anhlamrduc.sothuchi.utility.Currency;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class AccountFragment extends ListFragment implements NoteFragment.OnPassDataFromNote {

    @Bind(android.R.id.list)
    ListView lvAccount;
    @Bind(R.id.txt_sum)
    TextView txtSum;
    @Bind(R.id.img_add)
    ImageView imgAdd;

    private ArrayList<TaiKhoan> arrAccount;
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
        View v = inflater.inflate(R.layout.account_layout, container, false);
        ButterKnife.bind(this, v);

        String sumMoney = Currency.getCurrency(totalMoney);
        txtSum.setText("Tổng còn: " + sumMoney + " đ");
        listAccountAdapter = new ListAccountAdapter(getActivity(), arrAccount);
        lvAccount.setAdapter(listAccountAdapter);
        Log.e(MainActivity.MAIN, "Account Fragment created");
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

    @Override
    public void onDataReceivedFromNote(TaiKhoan account, double money) {
        Log.e(ACCOUNT, "Finally! accountName =" + account + ", money=" + money);
        if (arrAccount.contains(account)) {
            TaiKhoan newAccount = new TaiKhoan(account.getMaTaiKhoan(), account.getTenTaiKhoan(),
                    account.getSoTienBanDau(), account.getSoTienHienCo() + money, account.getGhiChu(),
                    account.getLoaiTaiKhoan());
            arrAccount.set(arrAccount.indexOf(account), newAccount);
            listAccountAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.img_add)
    public void gotoAddAccount() {
        Fragment addAccountFragment = new AddAccountFragment();

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, addAccountFragment, AccountContainerFragment.ADD_ACCOUNT_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);

        transaction.commit();

    }
}
