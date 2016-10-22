package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.CustomListAdapterDialog;
import com.example.anhlamrduc.sothuchi.item.Account;
import com.example.anhlamrduc.sothuchi.item.AccountType;
import com.google.gson.Gson;

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
    private ArrayList<Account> listAccount;
    Account account = new Account();
    private double money = 0;
    private boolean editMode = false;

    private Dialog dialog;

    OnPassDataFromAddAccount onPassDataFromAddAccount;

    public interface OnPassDataFromAddAccount {
        public void onAccountInsertToDBFromAddAccount(Account account);

        public void onAccountUpdateToDBFromAddAccount(Account account);
    }


    private TextView.OnEditorActionListener onAmountEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE && edtAmount.getText().length() > 0) {
                Gson gson = new Gson();
                Double money = Double.valueOf(edtAmount.getText().toString());
                String amountJson = gson.toJson(money);
                getEditor().putString(MainActivity.FIRST_MONEY_FROM_ADD_ACCOUNT, amountJson);
            }
            return false;
        }
    };

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
        editMode = getArguments().getBoolean(AccountFragment.EDIT_MODE, false);
        listAccountType = getArguments().getParcelableArrayList(AccountFragment.LIST_ACCOUNT_TYPE);
        listAccount = getArguments().getParcelableArrayList(AccountFragment.LIST_ACCOUNT);

        if (editMode) {
            account = getArguments().getParcelable(AccountFragment.ACCOUNT_EDIT);
            edtAmount.setHint(getString(R.string.current_money));
            money = account.getCurrentMoney();
            if (money > 0)
                edtAmount.setText((long) money + "");
            txtAccountType.setText(account.getAccountType().getTypeName());
            edtAccountName.setText(account.getAccountName());
            edtDescription.setText(account.getNote());
            accountType = account.getAccountType();
        } else {
            accountType = listAccountType.get(0);
            txtAccountType.setText(accountType.getTypeName());
        }

        money = getFirstAmount();

    }

    @Override
    protected void initView() {
        if (money > 0)
            edtAmount.setText((long) money + "");
        // edtAmount.addTextChangedListener(new NumberTextWatcher(edtAmount));
        edtAmount.setOnEditorActionListener(onAmountEditorActionListener);
    }

    @OnClick({R.id.txt_cancel, R.id.btn_save, R.id.ll_account_type, R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_cancel:
                resetView();
                getFragmentManager().popBackStack();
                break;
            case R.id.btn_save:
                createNewAccount();
                break;
            case R.id.ll_account_type:
                showDialog();
                break;
            case R.id.ll_add:
                createNewAccount();
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onPassDataFromAddAccount = (OnPassDataFromAddAccount) activity;
    }

    private void createNewAccount() {
        if (editMode) {
            if (edtAccountName.getText().length() == 0) {
                showAShortToask(getString(R.string.account_name_cant_be_blank));
            } else {
                account.setAccountName(edtAccountName.getText().toString().trim());
                account.setAccountType(accountType);
                money = (edtAmount.getText().length() > 0 ? Double.parseDouble(edtAmount.getText().toString()) : 0);
                account.setCurrentMoney(money);
                account.setNote(edtDescription.getText().toString());

                onPassDataFromAddAccount.onAccountUpdateToDBFromAddAccount(account);
            }

        } else if (checkDataValid()) {
            account.setAccountName(edtAccountName.getText().toString().trim());
            account.setAccountType(accountType);
            money = (edtAmount.getText().length() > 0 ? Double.parseDouble(edtAmount.getText().toString()) : 0);
            account.setFirstMoney(money);
            account.setCurrentMoney(money);
            account.setNote(edtDescription.getText().toString());

            onPassDataFromAddAccount.onAccountInsertToDBFromAddAccount(account);

            /* Save new Account to editor to NotifyDataChanged*/
            Gson gson = new Gson();
            String accountJson = gson.toJson(account);
            getEditor().putString(MainActivity.NEW_ACCOUNT, accountJson);
            getEditor().commit();

        }
        getFragmentManager().popBackStack();
    }

    private boolean checkDataValid() {
        boolean check = true;
        if (edtAccountName.getText().length() == 0) {
            showAShortToask(getString(R.string.account_name_cant_be_blank));
            check = false;
        } else {
            for (Account account : listAccount) {
                if (edtAccountName.getText().toString().trim().equalsIgnoreCase(account.getAccountName().trim())) {
                    showAShortToask(getString(R.string.account_name_already_exists));
                    edtAccountName.setText("");
                    check = false;
                }
            }
        }
        return check;
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

    private void resetView() {
        edtAmount.setText("");
        txtAccountType.setText("");
        edtAccountName.setText("");
        edtDescription.setText("");
    }
}


