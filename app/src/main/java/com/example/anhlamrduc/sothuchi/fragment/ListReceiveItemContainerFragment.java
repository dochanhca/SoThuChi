package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;

import java.util.ArrayList;

/**
 * Created by AnhlaMrDuc on 02-May-16.
 */
public class ListReceiveItemContainerFragment extends BaseFragment {
    public static final String ADD_RECEIVE_ITEM_FRAG = "AddReceiveItem";
    public static final String LIST_RECEIVE_ITEM_FRAG = "ListReceiveItem";
    private ArrayList<ReceiveItem> receiveItems;

    @Override
    protected int layoutID() {
        return R.layout.fr_list_receive_item_container;
    }

    @Override
    protected void handleData() {
        receiveItems = new ArrayList<>();
        receiveItems = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);

        ListReceiveItemFragment listReceiveItemFragment = new ListReceiveItemFragment();
        //put bundle
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB, receiveItems);
        listReceiveItemFragment.setArguments(args);
        //
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fr_list_receive_item_container, listReceiveItemFragment, LIST_RECEIVE_ITEM_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.commit();
    }

    @Override
    protected void initView() {

    }
}
