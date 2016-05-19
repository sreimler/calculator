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
import android.widget.Button;
import android.widget.TextView;

import com.sreimler.calculator.R;
import com.sreimler.calculator.utils.Calculator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Displays the user interface of the calculator.
 */
public class CalculatorActivity extends AppCompatActivity implements CalculatorContract.View {

    private CalculatorContract.Presenter mPresenter;

    @BindView(R.id.txtv_display_calculation)
    TextView mCalculationView;

    @BindView(R.id.txtv_display_operator)
    TextView mOperatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mPresenter = new CalculatorPresenter(new Calculator(), this);
    }

    @Override
    public void displayOperand(String calculation) {
        mCalculationView.setText(calculation);
    }

    @Override
    public void displayOperator(String operator) {
        mOperatorView.setText(operator);
    }

    @OnClick({R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9})
    public void numberButtonClicked(Button button) {
        mPresenter.appendValue((String) button.getText());
    }

    @OnClick({R.id.btn_plus, R.id.btn_minus, R.id.btn_multiply, R.id.btn_divide})
    public void operatorButtonClicked(Button button) {
        mPresenter.appendOperator((String) button.getText());
    }

    @OnClick(R.id.btn_clear)
    public void clearButtonClicked(Button button) {
        mPresenter.clearCalculation();
    }

    @OnClick(R.id.btn_equals)
    public void equalsButtonClicked(Button button) {
        mPresenter.performCalculation();
    }
}
