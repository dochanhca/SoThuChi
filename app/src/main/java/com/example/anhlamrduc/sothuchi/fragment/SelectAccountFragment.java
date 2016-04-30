package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListAccountAdapter;
import com.example.anhlamrduc.sothuchi.item.Account;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 20-Apr-16.
 */
public class SelectAccountFragment extends Fragment {

    private ArrayList<Account> arrAccount;
    private ListAccountAdapter listAccountAdapter;
    private ViewPager viewPager;
    private MainActivity mainActivity;

    OnDataPassFromSelectAccount onDataPassFromSelectAccount;

    public interface OnDataPassFromSelectAccount {
        public void onDataReceivedFromSelectAccount(Account account);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Account account = arrAccount.get(position);
            onDataPassFromSelectAccount.onDataReceivedFromSelectAccount(account);
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onDataPassFromSelectAccount = (OnDataPassFromSelectAccount) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        //Get data from main activity
        mainActivity = (MainActivity) getActivity();
        arrAccount = mainActivity.getListAccount();

        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);

        View v = inflater.inflate(R.layout.fr_account, container, false);
        ButterKnife.bind(this, v);
        imgAdd.setVisibility(View.GONE);
        llSumMoney.setVisibility(View.GONE);

        listAccountAdapter = new ListAccountAdapter(getActivity(), arrAccount);
        lvAccount.setAdapter(listAccountAdapter);
        lvAccount.setOnItemClickListener(onItemClickListener);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.img_back)
    public void goToBack() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
    }

    @Bind(android.R.id.list)
    ListView lvAccount;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.ll_sum_money)
    LinearLayout llSumMoney;
}
