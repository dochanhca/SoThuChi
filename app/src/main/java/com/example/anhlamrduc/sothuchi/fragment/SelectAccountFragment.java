package com.example.anhlamrduc.sothuchi.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListAccountAdapter;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 20-Apr-16.
 */
public class SelectAccountFragment extends BaseFragment {

    private ArrayList<Account> arrAccount;
    private ListAccountAdapter listAccountAdapter;
    private ViewPager viewPager;
    private MainActivity mainActivity;
    private int typeSelectAccount;

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Account account = arrAccount.get(position);
            Gson gson = new Gson();
            String accountJson = gson.toJson(account);
            if (typeSelectAccount == NoteFragment.FROM_ACCOUNT)
                getEditor().putString(MainActivity.FROM_ACCOUNT_SELECTED, accountJson);
            else
                getEditor().putString(MainActivity.TO_ACCOUNT_SELECTED, accountJson);
            getEditor().commit();
            getFragmentManager().popBackStack();
        }
    };

    @Override
    protected int layoutID() {
        return R.layout.fr_account;
    }

    @Override
    protected void initView() {
        //Get data from main activity
        viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        imgAdd.setVisibility(View.GONE);
        llSumMoney.setVisibility(View.GONE);

        listAccountAdapter = new ListAccountAdapter(getActivity(), arrAccount);
        lvAccount.setAdapter(listAccountAdapter);
        lvAccount.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void handleData() {

        typeSelectAccount = getArguments().getInt(NoteFragment.SELECT_ACCOUNT);
        mainActivity = (MainActivity) getActivity();
        arrAccount = mainActivity.getListAccount();
    }

    @OnClick(R.id.img_back)
    public void goToBack() {
        getFragmentManager().popBackStack();
    }

    @Bind(android.R.id.list)
    ListView lvAccount;
    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.ll_sum_money)
    LinearLayout llSumMoney;
}
