package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.ReceiveItem;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 03-May-16.
 */
public class AddReceiveItemFragment extends BaseFragment {

    @Bind(R.id.edt_description)
    EditText edtDescription;
    @Bind(R.id.edt_receive_item_name)
    EditText edtReceiveItemName;
    @Bind(R.id.ll_add)
    LinearLayout llAdd;

    ArrayList<ReceiveItem> receiveItems;

    OnPassDataFromAddReceiveItem onPassDataFromAddReceiveItem;

    public interface OnPassDataFromAddReceiveItem {
        public void onReceiveItemInsertToDBFromAddReceive(ReceiveItem receiveItem);
    }


    @Override
    protected int layoutID() {
        return R.layout.fr_add_receive_item;
    }

    @Override
    protected void handleData() {
        receiveItems = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onPassDataFromAddReceiveItem = (OnPassDataFromAddReceiveItem) activity;
        } catch (ClassCastException e) {
            e.getMessage();
        }
    }

    @OnClick({R.id.txt_cancel, R.id.btn_save, R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_save:
                createNewReceiveItem();
                break;
            case R.id.ll_add:
                createNewReceiveItem();
                break;
        }
    }

    private void createNewReceiveItem() {
        if (checkDataValid()) {
            ReceiveItem receiveItem = new ReceiveItem();
            receiveItem.setReceiveItemName(edtReceiveItemName.getText().toString());
            receiveItem.setDescription(edtDescription.getText().toString());

            onPassDataFromAddReceiveItem.onReceiveItemInsertToDBFromAddReceive(receiveItem);

            Gson gson = new Gson();
            String receiveItemJson = gson.toJson(receiveItem);
            getEditor().putString(MainActivity.NEW_RECEIVE_ITEM, receiveItemJson);
            getEditor().commit();

            edtReceiveItemName.setText("");
            edtDescription.setText("");
            getFragmentManager().popBackStack();
        }
    }

    private boolean checkDataValid() {
        boolean check = true;
        if (edtReceiveItemName.getText().length() == 0) {
            showAShortToask(getString(R.string.receive_item_cant_be_blank));
            check = false;
        } else {
            for (ReceiveItem receiveItem : receiveItems) {
                if (edtReceiveItemName.getText().toString().trim().equalsIgnoreCase(receiveItem.getReceiveItemName().trim())) {
                    showAShortToask(getString(R.string.receive_item_already_exists));
                    edtReceiveItemName.setText("");
                    check = false;
                }
            }
        }
        return check;
    }
}
