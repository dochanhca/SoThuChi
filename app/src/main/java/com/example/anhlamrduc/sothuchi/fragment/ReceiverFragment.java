package com.example.anhlamrduc.sothuchi.fragment;

import android.graphics.PorterDuff;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.adapter.ListReceiverApdater;
import com.example.anhlamrduc.sothuchi.item.Receiver;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 21-Apr-16.
 */
public class ReceiverFragment extends BaseFragment {
    private ArrayList<Receiver> arrReceiver;
    private ListReceiverApdater listReceiverApdater;

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String receiver = arrReceiver.get(position).getReceiverName();
            edtReceiver.setText(receiver);
        }
    };

    @Override
    protected int layoutID() {
        return R.layout.fr_receiver;
    }

    @Override
    protected void initView() {
        edtReceiver.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        listReceiverApdater = new ListReceiverApdater(getContext(), arrReceiver);
        lvReceiver.setAdapter(listReceiverApdater);
        lvReceiver.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void handleData() {
        arrReceiver = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_MAIN);
    }

    @OnClick(R.id.txt_done)
    public void selectReceiverDone() {
        String receiverName = edtReceiver.getText().toString();
        getEditor().putString(MainActivity.RECEIVER_NAME, receiverName);
        getEditor().commit();
        getFragmentManager().popBackStack();
    }

    @OnClick(R.id.txt_cancel)
    public void goToBack() {
        getFragmentManager().popBackStack();
    }

    @Bind(R.id.edt_receiver)
    EditText edtReceiver;
    @Bind(android.R.id.list)
    ListView lvReceiver;
}
