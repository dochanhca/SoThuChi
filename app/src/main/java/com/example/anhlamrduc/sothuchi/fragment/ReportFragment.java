package com.example.anhlamrduc.sothuchi.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.anhlamrduc.sothuchi.R;
import com.example.anhlamrduc.sothuchi.activity.MainActivity;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllPay;
import com.example.anhlamrduc.sothuchi.asynctask.DBGetAllIncome;
import com.example.anhlamrduc.sothuchi.item.Income;
import com.example.anhlamrduc.sothuchi.item.Pay;
import com.example.anhlamrduc.sothuchi.item.SpendingItem;
import com.example.anhlamrduc.sothuchi.item.Transfer;
import com.example.anhlamrduc.sothuchi.utils.Constant;
import com.example.anhlamrduc.sothuchi.utils.Currency;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by AnhlaMrDuc on 14-Mar-16.
 */
public class ReportFragment extends BaseFragment implements NoteFragment.OnPassDataFromNote{

    @Bind(R.id.txt_calendar_day)
    TextView txtCalendarDay;
    @Bind(R.id.txt_pay_money_day)
    TextView txtPayMoneyDay;
    @Bind(R.id.txt_receive_money_day)
    TextView txtReceiveMoneyDay;
    @Bind(R.id.txt_calendar_month)
    TextView txtCalendarMonth;
    @Bind(R.id.txt_pay_money_month)
    TextView txtPayMoneyMonth;
    @Bind(R.id.txt_receive_money_month)
    TextView txtReceiveMoneyMonth;
    @Bind(R.id.txt_calendar_year)
    TextView txtCalendarYear;
    @Bind(R.id.txt_pay_money_year)
    TextView txtPayMoneyYear;
    @Bind(R.id.txt_receive_money_year)
    TextView txtReceiveMoneyYear;
    @Bind(R.id.txt_calendar_sum)
    TextView txtCalendarSum;
    @Bind(R.id.txt_pay_money_sum)
    TextView txtPayMoneySum;
    @Bind(R.id.txt_receive_money_sum)
    TextView txtReceiveMoneySum;

    private ArrayList<SpendingItem> listSpendingItem = new ArrayList<>();
//    private ArrayList<Receiver> listReceiver = new ArrayList<>();
//    private ArrayList<ReceiveItem> listReceiveItem = new ArrayList<>();
//    private ArrayList<Event> listEvent = new ArrayList<>();
    private ArrayList<Pay> listPay = new ArrayList<>();
    private ArrayList<Income> listIncome = new ArrayList<>();

//    ArrayList<Entry> money = new ArrayList<Entry>();
//    ArrayList<String> title = new ArrayList<String>();
//    ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

    private ArrayList<SpendingItem> listSpendingParent;
    private HashMap<String, List<SpendingItem>> listSpendingChild;

    Date currentDay = Calendar.getInstance().getTime();

    private double sumPay = 0, sumPayDay = 0, sumPayWeek = 0, sumPayMonth = 0, sumPayYear = 0;
    private double sumReceive = 0, sumReceiveDay = 0, sumReceiveWeek = 0, sumReceiveMonth = 0, sumReceiveYear = 0;

    @Override
    protected int layoutID() {
        return R.layout.fr_report;
    }

    @Override
    protected void handleData() {
//        listReceiveItem = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVE_ITEM_FROM_DB);
        listSpendingItem = getArguments().getParcelableArrayList(MainActivity.LIST_SPENDING_FROM_DB);
//        listReceiver = getArguments().getParcelableArrayList(MainActivity.LIST_RECEIVER_FROM_DB);
//        listEvent = getArguments().getParcelableArrayList(MainActivity.LIST_EVENT_FROM_DB);

        DBGetAllPay dbGetAllPay = new DBGetAllPay(getContext()) {
            @Override
            protected void onPostExecute(ArrayList<Pay> pays) {
                super.onPostExecute(pays);
                listPay = pays;
            }
        };
        dbGetAllPay.execute();

        DBGetAllIncome dbGetAllIncome = new DBGetAllIncome(getContext()) {
            @Override
            protected void onPostExecute(ArrayList<Income> receiveMoneys) {
                super.onPostExecute(receiveMoneys);
                listIncome = receiveMoneys;
                getSumOfPay();
                getSumOfReceive();
                setText();
            }
        };
        dbGetAllIncome.execute();
    }

    private void setText() {

        txtPayMoneyDay.setText(Currency.getCurrency(sumReceiveDay) + " " + getResources().getString(R.string.currency));
        txtReceiveMoneyDay.setText(Currency.getCurrency(sumPayDay) + " " + getResources().getString(R.string.currency));


        txtPayMoneyMonth.setText(Currency.getCurrency(sumReceiveMonth) + " " + getResources().getString(R.string.currency));
        txtReceiveMoneyMonth.setText(Currency.getCurrency(sumPayMonth) + " " + getResources().getString(R.string.currency));


        txtPayMoneyYear.setText(Currency.getCurrency(sumReceiveYear)+ " " + getResources().getString(R.string.currency));
        txtReceiveMoneyYear.setText(Currency.getCurrency(sumPayYear) + " " + getResources().getString(R.string.currency));

        txtCalendarSum.setText(getString(R.string.sum) + " Thu - Chi = " + Currency.getCurrency(sumReceive - sumPay));
        txtPayMoneySum.setText(Currency.getCurrency(sumReceive)+ " " + getResources().getString(R.string.currency));
        txtReceiveMoneySum.setText(Currency.getCurrency(sumPay) + " " + getResources().getString(R.string.currency));
    }

    @Override
    protected void initView() {
        txtCalendarDay.setText(getString(R.string.today) + " (" + Constant.DATE_FORMAT.format(currentDay) + ")");
        txtCalendarMonth.setText(getString(R.string.this_month) + " (" + Constant.MONTH_FORMAT.format(currentDay) + ")");
        txtCalendarYear.setText(getString(R.string.this_year) + " (" + Constant.YEAR_FORMAT.format(currentDay) + ")");
    }

    private void getSumOfReceive() {
        for (int i = 0; i < listIncome.size(); i++) {
            sumReceive += listIncome.get(i).getAmount();
            Date today = listIncome.get(i).getReceiveDate();

            /*Get Sum of day*/
            if (Constant.DATE_FORMAT.format(today).equals(Constant.DATE_FORMAT.format(currentDay))) {
                sumReceiveDay += listIncome.get(i).getAmount();
            }

            /*Get Sum Of Month*/
            if (Constant.MONTH_FORMAT.format(today).equals(Constant.MONTH_FORMAT.format(currentDay))) {

                sumReceiveMonth += listIncome.get(i).getAmount();
            }

            /*Get Sum of Year*/
            if (Constant.YEAR_FORMAT.format(today).equals(Constant.YEAR_FORMAT.format(currentDay))) {

                sumReceiveYear += listIncome.get(i).getAmount();
            }
        }
    }

    private void getSumOfPay() {
        for (int i = 0; i < listPay.size(); i++) {
            sumPay += listPay.get(i).getMoney();
            Date today = listPay.get(i).getPayDate();

            /*Get Sum of day*/
            if (Constant.DATE_FORMAT.format(today).equals(Constant.DATE_FORMAT.format(currentDay))) {

                sumPayDay += listPay.get(i).getMoney();
            }
            if (Constant.MONTH_FORMAT.format(today).equals(Constant.MONTH_FORMAT.format(currentDay))) {

                sumPayMonth += listPay.get(i).getMoney();
            }

            /*Get Sum of Year*/
            if (Constant.YEAR_FORMAT.format(today).equals(Constant.YEAR_FORMAT.format(currentDay))) {

                sumPayYear += listPay.get(i).getMoney();
            }
        }
    }

    private void prepareListData() {

        listSpendingParent = new ArrayList<SpendingItem>();
        listSpendingChild = new HashMap<String, List<SpendingItem>>();

        for (SpendingItem spending : listSpendingItem) {
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
        for (int i = 0; i < listSpendingItem.size(); i++) {
            if (listSpendingItem.get(i).getParentItem() != null) {
                if (listSpendingItem.get(i).getParentItem().equals(parentName))
                    child.add(listSpendingItem.get(i));
            }
        }
        return child;
    }

    @OnClick({R.id.rl_day, R.id.rl_month, R.id.rl_year, R.id.rl_sum})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_day:
                break;
            case R.id.rl_month:
                break;
            case R.id.rl_year:
                break;
            case R.id.rl_sum:
                break;
        }
    }

    @Override
    public void onPayInsertToDBFromNote(Pay pay) {
        listPay.add(pay);
        sumPay += pay.getMoney();

        if (Constant.DATE_FORMAT.format(pay.getPayDate()).equals(Constant.DATE_FORMAT.format(currentDay)))
            sumPayDay += pay.getMoney();
        if (Constant.MONTH_FORMAT.format(pay.getPayDate()).equals(Constant.MONTH_FORMAT.format(currentDay)))
            sumPayMonth += pay.getMoney();
        if (Constant.YEAR_FORMAT.format(pay.getPayDate()).equals(Constant.YEAR_FORMAT.format(currentDay)))
            sumPayYear += pay.getMoney();

        setText();
    }

    @Override
    public void onReceiveMoneyInsertToDBFromNote(Income income) {
        listIncome.add(income);
        sumReceive += income.getAmount();


        if (Constant.DATE_FORMAT.format(income.getReceiveDate()).equals(Constant.DATE_FORMAT.format(currentDay)))
            sumReceiveDay += income.getAmount();
        if (Constant.MONTH_FORMAT.format(income.getReceiveDate()).equals(Constant.MONTH_FORMAT.format(currentDay)))
            sumReceiveMonth += income.getAmount();
        if (Constant.YEAR_FORMAT.format(income.getReceiveDate()).equals(Constant.YEAR_FORMAT.format(currentDay)))
            sumReceiveYear += income.getAmount();

        setText();
        Log.e("Reprot: ", "Received " + income.getAmount());
    }

    @Override
    public void onTransferInsertToDBFromNote(Transfer transfer) {

    }
}
