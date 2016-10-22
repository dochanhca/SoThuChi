package com.example.anhlamrduc.sothuchi.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListReceiveItemAdapter;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 03-May-16.
 */
public class ListReceiveItemFragment extends BaseFragment {
    private ReceiveItem receiveItem;

    @Bind(android.R.id.list)
    ListView list;
    @Bind(R.id.txt_spending_parent_title)
    TextView title;

    private ArrayList<ReceiveItem> receiveItems;
    private ListReceiveItemAdapter listReceiveItemAdapter;

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            ReceiveItem receiveItem = receiveItems.get(position);
            setReceiveItem(receiveItem);

            getFragmentManager().popBackStack();
        }
    };

    @Override
    protected int layoutID() {
        return R.layout.fr_list_item;
    }

    @Override
    protected void handleData() {
        receiveItems = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);
        if (getNewReceiveItem() != null) {
            receiveItem = getNewReceiveItem();
            receiveItems.add(receiveItem);

            getEditor().remove(MainActivity.NEW_RECEIVE_ITEM);
            getEditor().commit();
        }
    }

    @Override
    protected void initView() {
        title.setText(R.string.receive_item);
        listReceiveItemAdapter = new ListReceiveItemAdapter(getActivity(), receiveItems);
        list.setAdapter(listReceiveItemAdapter);
        list.setOnItemClickListener(onItemClickListener);
    }

    @OnClick({R.id.img_back, R.id.img_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                getFragmentManager().popBackStack();
                break;
            case R.id.img_add:
                showAddReceiveItemFragment();
                break;
        }
    }

    private void showAddReceiveItemFragment() {
        AddReceiveItemFragment addReceiveItemFragment = new AddReceiveItemFragment();

        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB, receiveItems);
        addReceiveItemFragment.setArguments(args);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fr_list_receive_item_container, addReceiveItemFragment,
                ListReceiveItemContainerFragment.ADD_RECEIVE_ITEM_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
