package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 28-Mar-16.
 */
public class AddSpendItemFragment extends BaseFragment {

    ArrayList<SpendingItem> spendingItems;
    String spendingParentName = "";

    OnPassDataFromAddSpendItem onPassDataFromAddSpendItem;

    public interface OnPassDataFromAddSpendItem {
        public void onSpendItemInsertToDB(SpendingItem spendingItem);
    }

    @Override
    protected int layoutID() {
        return R.layout.fr_add_spend_item;
    }

    @Override
    protected void handleData() {
        spendingItems = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB);
        spendingParentName = getSpendingItemParentName();
    }

    @Override
    protected void initView() {
        txtSpendingParent.setText(spendingParentName);
    }

    @OnClick({R.id.txt_cancel, R.id.btn_save, R.id.ll_spending_item_parent, R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_save:
                createNewSpendingItem();
                break;
            case R.id.ll_spending_item_parent:
                showListSpendingItemParentFragment();
                break;
            case R.id.ll_add:
                createNewSpendingItem();
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onPassDataFromAddSpendItem = (OnPassDataFromAddSpendItem) activity;
        } catch (ClassCastException e) {
            e.getMessage();
        }
    }

    private void createNewSpendingItem() {
        if (checkDataValid()) {
            SpendingItem spendingItem = new SpendingItem();
            spendingItem.setSpendingItemName(edtSpendingItemName.getText().toString());
            spendingItem.setNote(edtDescription.getText().toString());
            spendingItem.setParentItem(txtSpendingParent.getText().toString());

            onPassDataFromAddSpendItem.onSpendItemInsertToDB(spendingItem);

            Gson gson = new Gson();
            String spendingItemJson = gson.toJson(spendingItem);
            getEditor().putString(MainActivity.NEW_SPENDING_ITEM, spendingItemJson);
            getEditor().remove(MainActivity.SPENDING_ITEM_PARENT_NAME);
            getEditor().commit();

            txtSpendingParent.setText("");
            edtSpendingItemName.setText("");
            edtDescription.setText("");
            getFragmentManager().popBackStack();
        }
    }

    private boolean checkDataValid() {
        boolean check = true;
        if (edtSpendingItemName.getText().length() == 0) {
            showAShortToask(getString(R.string.spend_item_cant_be_blank));
            check = false;
        } else {
            for (SpendingItem spendingItem : spendingItems) {
                if (edtSpendingItemName.getText().toString().trim().equalsIgnoreCase(spendingItem.getSpendingItemName().trim())) {
                    showAShortToask(getString(R.string.spend_item_already_exists));
                    edtSpendingItemName.setText("");
                    check = false;
                }
            }
        }
        return check;
    }

    private void showListSpendingItemParentFragment() {
        ListSpendingItemParentFragment listSpendingItemParentFragment = new ListSpendingItemParentFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB, spendingItems);
        listSpendingItemParentFragment.setArguments(args);
        //
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fr_list_spend_item_container, listSpendingItemParentFragment,
                ListSpendItemContainerFragment.LIST_SPEND_PARENT_ITEM_FRAG);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Bind(R.id.txt_spending_parent)
    TextView txtSpendingParent;
    @Bind(R.id.ll_add)
    LinearLayout llAdd;
    @Bind(R.id.edt_spending_item_name)
    EditText edtSpendingItemName;
    @Bind(R.id.edt_description)
    EditText edtDescription;

}
