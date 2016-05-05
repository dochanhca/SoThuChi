package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 28-Mar-16.
 */
public class ListSpendItemContainerFragment extends BaseFragment {


    public static final String LIST_SPEND_ITEM_FRAG = "ListSpendItemFragment";
    public static final String ADD_SPEND_ITEM_FRAG = "AddSpendItemFragment";
    public static final String SPEND_ITEM_TAB1_FRAG = "SpendItemTab1Fragment";
    public static final String LIST_SPEND_PARENT_ITEM_FRAG = "ListSpendParentItem";
    private String spendingReceived = "";
    private ArrayList<SpendingItem> spendingItems;

    @Override
    protected int layoutID() {
        return R.layout.fr_list_spend_item_container;
    }

    @Override
    protected void handleData() {
        spendingItems = new ArrayList<>();
        spendingItems = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB);

        ListSpendItemFragment listSpendItemFragment = new ListSpendItemFragment();
        //put bundle
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB, spendingItems);
        listSpendItemFragment.setArguments(args);
        //
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fr_list_spend_item_container, listSpendItemFragment, LIST_SPEND_ITEM_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    protected void initView() {

    }
}
