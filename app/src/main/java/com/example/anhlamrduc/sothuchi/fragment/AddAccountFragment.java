package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Dialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.adapter.CustomListAdapterDialog;
import com.example.anhlamrduc.sothuchi.item.AccountType;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 25-Mar-16.
 */
public class AddAccountFragment extends BaseFragment {
    @Bind(R.id.edt_account_name)
    EditText edtAccountName;
    @Bind(R.id.txt_account_type)
    TextView txtAccountType;
    @Bind(R.id.edt_amount)
    EditText edtAmount;
    @Bind(R.id.edt_description)
    EditText edtDescription;

    private ArrayList<AccountType> listAccountType;
    private AccountType accountType;
    private Dialog dialog;

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            accountType = listAccountType.get(position);
            dialog.dismiss();
            txtAccountType.setText(accountType.getTypeName());
        }
    };

    @Override
    protected int layoutID() {
        return R.layout.fr_add_account;
    }

    @Override
    protected void handleData() {
        listAccountType = getArguments().getParcelableArrayList(AccountFragment.LIST_ACCOUNT_TYPE);
        accountType = listAccountType.get(0);
        txtAccountType.setText(accountType.getTypeName());
    }

    @Override
    protected void initView() {
        accountType = listAccountType.get(0);
        txtAccountType.setText(accountType.getTypeName());
    }

    @OnClick({R.id.txt_cancel, R.id.btn_save, R.id.ll_account_type, R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_save:
                break;
            case R.id.ll_account_type:
                showDialog();
                break;
            case R.id.ll_add:
                break;
        }
    }

    private void showDialog() {
        dialog = new Dialog(getContext());
        ViewGroup viewGroup = (ViewGroup) getView().getParent();
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_type_account, viewGroup, false);
        ListView listView = (ListView) view.findViewById(R.id.lv_type_account);

        CustomListAdapterDialog adapterDialog = new CustomListAdapterDialog(getContext(), listAccountType);
        listView.setAdapter(adapterDialog);
        listView.setOnItemClickListener(onItemClickListener);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.show();
    }
}


