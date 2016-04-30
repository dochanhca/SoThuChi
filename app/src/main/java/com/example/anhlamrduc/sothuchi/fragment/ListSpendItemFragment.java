package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ExpandableListAdapter;
import com.example.anhlamrduc.sothuchi.adapter.NoteSpinnerAdapter;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 28-Mar-16.
 */
public class ListSpendItemFragment extends Fragment {

    private static final String TAG = "ListSpendItem: ";
    public static final String SPENDING_FROM_LIST_ITEM = "Spending";
    private ArrayList<SpendingItem> arrSpending;
    ExpandableListAdapter listAdapter;
    List<SpendingItem> listSpendingParent;
    HashMap<String, List<SpendingItem>> listSpendingChild;

    OnDataPassFromListSpendItem onPassDataFromListSpendItem;

    public interface OnDataPassFromListSpendItem {
        public void onDataReceivedFromListSpendItem(SpendingItem spending);
    }

    private ExpandableListView.OnGroupClickListener onGroupClickListener = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
            onPassDataFromListSpendItem.onDataReceivedFromListSpendItem(listSpendingParent.get(groupPosition));
//            if (getFragmentManager().getBackStackEntryCount() > 0) {
//                getFragmentManager().popBackStack();
//            }
            return true;
        }
    };

    private ExpandableListView.OnChildClickListener onChildClickListener = new ExpandableListView.OnChildClickListener() {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {
            // TODO Auto-generated method stub
            SpendingItem spending = listSpendingChild.
                    get(listSpendingParent.get(groupPosition).getSpendingItemName()).get(childPosition);
            onPassDataFromListSpendItem.onDataReceivedFromListSpendItem(spending);
            return true;
        }
    };

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onPassDataFromListSpendItem = (OnDataPassFromListSpendItem) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_list_spend_item, container, false);
        ButterKnife.bind(this, v);

        // receive bundle
        arrSpending = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_MAIN);

        initiateListview();

        spnSpendAction.setAdapter(new NoteSpinnerAdapter(getContext(),
                R.layout.spinner_dropdown_custom,
                getResources().getStringArray(R.array.spn_spend_title)));

        edtSearch.addTextChangedListener(textWatcher);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);
//        searchView.setOnQueryTextListener(this);
//        searchView.setOnCloseListener(this);

        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick(R.id.img_add)
    public void showAddSpendItem() {
        AddSpendItemFragment addSpendItemFragment = new AddSpendItemFragment();
//                accountFragment.setArguments(args);
        //
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fr_list_spend_item_container, addSpendItemFragment,
                ListSpendItemContainerFragment.Add_SPEND_ITEM_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.img_back)
    public void gotoBack() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            return;
        }
    }

    private void initiateListview() {

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getContext(), listSpendingParent, listSpendingChild);
        // setting list adapter
        lvSpending.setAdapter(listAdapter);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            lvSpending.expandGroup(i);
        }

        lvSpending.setOnGroupClickListener(onGroupClickListener);

        // Listview on child click listener
        lvSpending.setOnChildClickListener(onChildClickListener);


    }

    private void prepareListData() {

        listSpendingParent = new ArrayList<SpendingItem>();
        listSpendingChild = new HashMap<String, List<SpendingItem>>();

        for (SpendingItem spending : arrSpending) {
            if (spending.getParentItem() == null)
                listSpendingParent.add(spending);
            else if (spending.getParentItem().equals(""))
                listSpendingParent.add(spending);

        }

        for (int i = 0; i < listSpendingParent.size(); i++) {
            listSpendingChild.put(listSpendingParent.get(i).getSpendingItemName(), listSpendingChild(listSpendingParent.get(i).getSpendingItemName()));
        }
    }

    /**
     * @param parentName
     * @return Array contain spending child name has spending parent name = parentName
     */
    private List<SpendingItem> listSpendingChild(String parentName) {
        List<SpendingItem> child = new ArrayList<>();
        for (int i = 0; i < arrSpending.size(); i++) {
            if (arrSpending.get(i).getParentItem() != null) {
                if (arrSpending.get(i).getParentItem().equals(parentName))
                    child.add(arrSpending.get(i));
            }
        }
        return child;
    }

    @Bind(R.id.img_add)
    ImageView imgAdd;
    @Bind(R.id.img_back)
    ImageView imgBack;
    @Bind(R.id.spn_spend_action)
    Spinner spnSpendAction;
    @Bind(R.id.lv_spending)
    ExpandableListView lvSpending;
    @Bind(R.id.edt_search)
    EditText edtSearch;

}
