package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 28-Mar-16.
 */
public class ListSpendItemContainerFragment extends Fragment {


    public static final String LIST_SPEND_ITEM_FRAG = "ListSpendItemFragment";
    public static final String Add_SPEND_ITEM_FRAG = "AddSpendItemFragment";
    public static final String SPEND_ITEM_TAB1_FRAG = "SpendItemTab1Fragment";
    private String spendingReceived = "";
    private ArrayList<SpendingItem> arrSpending;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_list_spend_item_container, container, false);

        arrSpending = new ArrayList<>();
        arrSpending = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_MAIN);

        ListSpendItemFragment listSpendItemFragment = new ListSpendItemFragment();
        //put bundle
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_MAIN, arrSpending);
        listSpendItemFragment.setArguments(args);
        //
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fr_list_spend_item_container, listSpendItemFragment, LIST_SPEND_ITEM_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
        return v;
    }
}
