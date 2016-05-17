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

import com.sreimler.calculator.data.Calculator;
import com.sreimler.calculator.data.Operator;

/**
 * Listens to user input from the {@link CalculatorActivity}, forwards calculations to
 * the {@link com.sreimler.calculator.data.Calculator} and updates the UI if required.
 */
public class CalculatorPresenter implements CalculatorContract.Presenter {

    private final Calculator mCalculator;
    private final CalculatorContract.View mView;

    private String mFirstOperand = "";
    private String mSecondOperand = "";
    private Operator mOperator = null;

    public CalculatorPresenter(Calculator calculator, CalculatorContract.View view) {
        mCalculator = calculator;
        mView = view;
    }

    @Override
    public void deleteCalculation() {
        mFirstOperand = "";
        mSecondOperand = "";
        mOperator = Operator.EMPTY;

        updateDisplay();
    }

    @Override
    public String getSecondOperand() {
        return mSecondOperand;
    }

    @Override
    public String getFirstOperand() {
        return mFirstOperand;
    }

    @Override
    public Operator getOperator() {
        return mOperator;
    }

    @Override
    public void appendValue(String value) {
        mFirstOperand += value;
    }

    @Override
    public void setOperator(Operator operator) {
        mOperator = operator;
        updateDisplay();
    }

    private void updateDisplay() {
        mView.displayOperand(mFirstOperand);
        mView.displayOperator(mOperator.toString());
    }
}
