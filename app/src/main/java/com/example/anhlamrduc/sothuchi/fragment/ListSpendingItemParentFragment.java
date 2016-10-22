package com.example.anhlamrduc.sothuchi.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListSpendingItemParentAdapter;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 03-May-16.
 */
public class ListSpendingItemParentFragment extends BaseFragment {

    @Bind(android.R.id.list)
    ListView list;
    @Bind(R.id.txt_spending_parent_title)
    TextView txtTitle;
    @Bind(R.id.img_add)
    ImageView imgAdd;

    private ArrayList<SpendingItem> spendingItems;
    private ArrayList<SpendingItem> listSpendingParent;
    private ListSpendingItemParentAdapter adapter;

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String spendingParentName = listSpendingParent.get(position).getSpendingItemName();
            getEditor().putString(MainActivity.SPENDING_ITEM_PARENT_NAME, spendingParentName);
            getEditor().commit();
            getFragmentManager().popBackStack();
        }
    };

    @Override
    protected int layoutID() {
        return R.layout.fr_list_item;
    }

    @Override
    protected void handleData() {
        spendingItems = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB);
        preparingListSpendingParent();
    }

    private void preparingListSpendingParent() {

        listSpendingParent = new ArrayList<>();
        for (SpendingItem spending : spendingItems) {
            if (spending.getParentItem() == null)
                listSpendingParent.add(spending);
            else if (spending.getParentItem().equals(""))
                listSpendingParent.add(spending);

        }
    }

    @Override
    protected void initView() {
        txtTitle.setVisibility(View.VISIBLE);
        imgAdd.setVisibility(View.GONE);
        adapter = new ListSpendingItemParentAdapter(getContext(), listSpendingParent);
        list.setAdapter(adapter);
        list.setOnItemClickListener(onItemClickListener);
    }

    @OnClick(R.id.img_back)
    public void onClick(View view) {
        getFragmentManager().popBackStack();
    }
}
