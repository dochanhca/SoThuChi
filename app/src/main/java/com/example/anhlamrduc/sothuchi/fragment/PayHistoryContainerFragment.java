package com.example.anhlamrduc.sothuchi.fragment;

import android.support.v4.app.FragmentTransaction;

import com.example.anhlamrduc.sothuchi.R;

/**
 * Created by AnhlaMrDuc on 30-Apr-16.
 */
public class PayHistoryContainerFragment extends BaseFragment {

    public static final String PAY_HISTORY_FRAG = "PayHistoryFragment";

    @Override
    protected int layoutID() {
        return R.layout.fr_pay_history_container;
    }

    @Override
    protected void handleData() {
        PayHistoryFragment payHistoryFragment = new PayHistoryFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fr_pay_history_container, payHistoryFragment, PAY_HISTORY_FRAG);
        transaction.commit();
    }

    @Override
    protected void initView() {

    }
}
