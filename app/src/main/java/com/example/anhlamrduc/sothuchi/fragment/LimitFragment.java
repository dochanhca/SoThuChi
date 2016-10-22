package com.example.anhlamrduc.sothuchi.fragment;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class LimitFragment extends BaseFragment {

    @Bind(R.id.edt_limit_name)
    EditText edtLimitName;
    @Bind(R.id.edt_amount)
    EditText edtAmount;
    @Bind(R.id.txt_repeat)
    TextView txtRepeat;
    @Bind(R.id.txt_start_date)
    TextView txtStartDate;
    @Bind(R.id.ck_limit)
    CheckBox ckLimit;

    @Override
    protected int layoutID() {
        return R.layout.fr_limit;
    }

    @Override
    protected void handleData() {

    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.btn_save, R.id.ll_repeat, R.id.ll_start_date, R.id.ck_limit, R.id.ll_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                showAShortToask(getString(R.string.function_updating));
                break;
            case R.id.ll_repeat:
                break;
            case R.id.ll_start_date:
                break;
            case R.id.ck_limit:
                break;
            case R.id.ll_add:
                showAShortToask(getString(R.string.function_updating));
                break;
        }
    }
}
