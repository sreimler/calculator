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

import com.sreimler.calculator.utils.Calculator;
import com.sreimler.calculator.models.Operator;

/**
 * Listens to user input from the {@link CalculatorActivity}, forwards calculations to
 * the {@link Calculator} and updates the UI if required.
 */
public class CalculatorPresenter implements CalculatorContract.Presenter {

    private final Calculator mCalculator;
    private final CalculatorContract.View mView;

    private String mCurrentOperand;
    private String mPreviousOperand;
    private Operator mOperator;
    private boolean wasLastInputOperator = false;

    public CalculatorPresenter(Calculator calculator, CalculatorContract.View view) {
        mCalculator = calculator;
        mView = view;

        resetCalculator();
    }

    @Override
    public void deleteCalculation() {
        resetCalculator();
        updateDisplay();
    }

    private void resetCalculator() {
        mCurrentOperand = "0";
        mPreviousOperand = "0";
        mOperator = Operator.EMPTY;
    }

    @Override
    public String getPreviousOperand() {
        return mPreviousOperand;
    }

    @Override
    public String getCurrentOperand() {
        return mCurrentOperand;
    }

    @Override
    public Operator getOperator() {
        return mOperator;
    }

    @Override
    public void appendValue(String value) {
        if (wasLastInputOperator) {
            // Last input was an operator - start a new operand
            mPreviousOperand = mCurrentOperand;
            mCurrentOperand = "";
        }

        if (mCurrentOperand.equals("0")) {
            mCurrentOperand = value;
        } else {
            mCurrentOperand += value;
        }

        wasLastInputOperator = false;
    }

    @Override
    public void setOperator(Operator operator) {
        if (mOperator != Operator.EMPTY && !wasLastInputOperator) {
            // Previous operator exists - perform partical calculation
            mCurrentOperand = mCalculator.performCalculation(mPreviousOperand, mCurrentOperand, mOperator);

            // Reset the previous operand and store the new operator
            mPreviousOperand = "";
            mOperator = operator;
            wasLastInputOperator = true;
            updateDisplay();
        } else{
            mOperator = operator;
            wasLastInputOperator = true;
            updateDisplay();
        }

    }

    @Override
    public void performCalculation() {
        mCalculator.performCalculation(mPreviousOperand, mCurrentOperand, mOperator);
    }

    private void updateDisplay() {
        mView.displayOperand(mCurrentOperand);
        mView.displayOperator(mOperator.toString());
    }
}
