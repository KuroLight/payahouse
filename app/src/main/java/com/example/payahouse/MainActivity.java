package com.example.payahouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText et_money_principal;
    EditText et_time_years;
    EditText et_rate_annual;
    int money_principal;
    int time_years;
    double rate_annual;

    TextView tv_results_1;
    TextView tv_results_2;


    public void compute(View v) {

        try {
            money_principal = Integer.parseInt(et_money_principal.getText().toString());
        } catch (IllegalArgumentException e) {
            money_principal = 20;
        }
        money_principal *= 10000;

        try {
            System.out.println(et_time_years.getText());
            time_years = Integer.parseInt(et_time_years.getText().toString());
        } catch (IllegalArgumentException e) {
            time_years = 15;
        }

        try {
            rate_annual = Double.parseDouble(et_rate_annual.getText().toString());
        } catch (IllegalArgumentException e) {
            rate_annual = 4.9;
        }

        pay_equal_loan(rate_annual, time_years, money_principal);
        pay_equal_principal(rate_annual, time_years, money_principal);

    }

    private void pay_equal_loan(double rate_annual, int time_years, int money_principal) {
        tv_results_1.setText("计算中。。。");

        double rate_month = rate_annual * .01 / 12.;
        int time_months = time_years * 12;

        double tmp = Math.pow((1 + rate_month), time_months);
        double pay_per_month = money_principal * rate_month * tmp / (tmp - 1);
        double pay_all = pay_per_month * time_months;
        double interest_all = pay_all - money_principal;

        String title = "等额本息还款法：\n";
        String monthly_pay = String.format("每月还款 %,.2f 元\n", pay_per_month);
        String all_interest = String.format("总支付利息 %,.2f 元\n", interest_all);
        String all_pay = String.format("本息合计 %,.2f 元\n", pay_all);

        String results_1 = title + monthly_pay + all_interest + all_pay;
        tv_results_1.setText(results_1);

    }

    private void pay_equal_principal(double rate_annual, int time_years, int money_principal) {
        tv_results_2.setText("计算中。。。");

        int ori_money_principal = money_principal;
        int time_months = time_years * 12;

        double principal_per_month = money_principal / time_months;

        double rate_month = rate_annual * .01 / 12.;

        ArrayList interest_all_months = new ArrayList<Double>();
        for (int i = 0; i < time_months; i++) {
            double interest_per_month = money_principal * rate_month;
            interest_all_months.add(interest_per_month);
            money_principal -= principal_per_month;
        }

        double pay_first_month = principal_per_month + (double) interest_all_months.get(0);

        double interest_all = 0;
        for (Object interest : interest_all_months) {
            interest_all += (double) interest;
        }

        String title = "等额本金还款法：\n";
        String monthly_pay = String.format("每月还款 %,.2f 元\n", pay_first_month);
        String all_interest = String.format("总支付利息 %,.2f 元\n", interest_all);
        String all_pay = String.format("本息合计 %,.2f 元\n", ori_money_principal + interest_all);

        String results_2 = title + monthly_pay + all_interest + all_pay;
        tv_results_2.setText(results_2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_money_principal = (EditText) findViewById(R.id.et_money_principal);
        et_time_years = (EditText) findViewById(R.id.et_time_years);
        et_rate_annual = (EditText) findViewById(R.id.et_rate_annual);
        tv_results_1 = (TextView) findViewById(R.id.tv_results_1);
        tv_results_2 = (TextView) findViewById(R.id.tv_results_2);
    }
}
