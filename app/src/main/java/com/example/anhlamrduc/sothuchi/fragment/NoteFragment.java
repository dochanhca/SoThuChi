package com.example.anhlamrduc.sothuchi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.item.TaiKhoan;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class NoteFragment extends Fragment {


    public static final String ACCOUNT_NAME_FROM_NOTE = "acount name";
    public static final String MONEY_FROM_NOTE = "money";
    private String accountName;
    private String[] arrAccountName;
    private TaiKhoan account;
    private ArrayList<TaiKhoan> arrAccount;
    double money = 0;
    //    Bundle bundle;
    OnPassDataFromNote onPassDataFromNote;

//    DataFromNote dataFromNote;

    @Bind(R.id.btn_add)
    Button btnAdd;
    @Bind(R.id.edt_amount)
    EditText edtAmount;
    @Bind(R.id.spn_account)
    Spinner spnAccount;

    public interface OnPassDataFromNote
    {
        public void onDataReceivedFromNote(TaiKhoan account, double money);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Receive a bundle
//        arrAccountName = getArguments().getStringArray(MainActivity.ARR_ACCOUNT_NAME_FROM_MAIN);
        arrAccount = getArguments().getParcelableArrayList(MainActivity.LIST_ACCOUNT_FROM_MAIN);

//        ((MainActivity)getActivity()).getList()
        View v = inflater.inflate(R.layout.note_layout, container, false);
        ButterKnife.bind(this, v);
        //Set Onclick for Account Spinner
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item,
                arrAccount);
        spnAccount.setAdapter(adapter);
        spnAccount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                account = (TaiKhoan) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                accountName = "Ví";
            }
        });
        Log.e(MainActivity.MAIN, "Note Fragment created");
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onPassDataFromNote = (OnPassDataFromNote) activity;
        } catch (ClassCastException e) {
            e.getMessage();
        }
    }

    @OnClick(R.id.btn_add)
    public void addTransaction() {
        if (!edtAmount.getText().equals("")) {
            money = Double.parseDouble(edtAmount.getText().toString());
        }
        //Put the value
//        ((MainActivity)get)
        onPassDataFromNote.onDataReceivedFromNote(account, money);
        Toast.makeText(getContext(), "Ghi thành công", Toast.LENGTH_LONG).show();
    }

//
}
