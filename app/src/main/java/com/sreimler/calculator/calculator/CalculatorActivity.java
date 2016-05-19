/*
 * Copyright 2016 Sören Reimler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sreimler.calculator.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sreimler.calculator.R;
import com.sreimler.calculator.utils.Calculator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Displays the user interface of the calculator.
 */
public class CalculatorActivity extends AppCompatActivity implements CalculatorContract.View, View.OnClickListener {

    private CalculatorContract.Presenter mPresenter;

    @BindView(R.id.txtv_display_calculation)
    TextView mCalculationView;

    @BindView(R.id.txtv_display_operator)
    TextView mOperatorView;

    @BindView(R.id.btn_0)
    Button mButton0;

    @BindView(R.id.btn_1)
    Button mButton1;

    @BindView(R.id.btn_2)
    Button mButton2;

    @BindView(R.id.btn_3)
    Button button3;

    @BindView(R.id.btn_4)
    Button button4;

    @BindView(R.id.btn_5)
    Button button5;

    @BindView(R.id.btn_6)
    Button button6;

    @BindView(R.id.btn_7)
    Button button7;

    @BindView(R.id.btn_8)
    Button button8;

    @BindView(R.id.btn_9)
    Button button9;

    @BindView(R.id.btn_plus)
    Button plusButton;

    @BindView(R.id.btn_minus)
    Button minusButton;

    @BindView(R.id.btn_multiply)
    Button multiplyButton;

    @BindView(R.id.btn_divide)
    Button divideButton;

    @BindView(R.id.btn_clear)
    Button clearButton;

    @BindView(R.id.btn_equals)
    Button equalsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPresenter = new CalculatorPresenter(new Calculator(), this);

        mButton0.setOnClickListener(this);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        equalsButton.setOnClickListener(this);
    }

    @Override
    public void displayOperand(String calculation) {
        mCalculationView.setText(calculation);
    }

    @Override
    public void displayOperator(String operator) {
        // TODO: set ui text

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_plus:
                break;
            case R.id.btn_minus:
                break;
            case R.id.btn_multiply:
                break;
            case R.id.btn_divide:
                break;
            case R.id.btn_clear:
                break;
            case R.id.btn_equals:
                break;
            default:
                mPresenter.appendValue((String) ((Button) v).getText());
        }
    }
}
