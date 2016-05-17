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

import com.sreimler.calculator.models.Operand;
import com.sreimler.calculator.models.Operator;
import com.sreimler.calculator.utils.Calculator;

/**
 * Listens to user input from the {@link CalculatorActivity}, forwards calculations to
 * the {@link Calculator} and updates the UI if required.
 */
public class CalculatorPresenter implements CalculatorContract.Presenter {

    private Calculator mCalculator;
    private CalculatorContract.View mView;

    private Operand mCurrentOperand;
    private Operand mPreviousOperand;
    private Operator mOperator;
    private boolean wasLastInputOperator = false;

    public CalculatorPresenter(Calculator calculator, CalculatorContract.View view) {
        mCalculator = calculator;
        mView = view;

        mCurrentOperand = new Operand();
        mPreviousOperand = new Operand();
        resetCalculator();
    }

    @Override
    public void deleteCalculation() {
        resetCalculator();
        updateDisplay();
    }

    private void resetCalculator() {
        mCurrentOperand.reset();
        mPreviousOperand.reset();
        mOperator = Operator.EMPTY;
    }

    @Override
    public String getPreviousOperand() {
        return mPreviousOperand.getValue();
    }

    @Override
    public String getCurrentOperand() {
        return mCurrentOperand.getValue();
    }

    @Override
    public Operator getOperator() {
        return mOperator;
    }

    @Override
    public void appendValue(String value) {
        if (wasLastInputOperator) {
            // Last input was an operator - start a new operand
            mPreviousOperand.setValue(mCurrentOperand.getValue());
            mCurrentOperand.reset();
        }

        mCurrentOperand.appendValue(value);

        wasLastInputOperator = false;
    }

    @Override
    public void setOperator(Operator operator) {
        if (mOperator != Operator.EMPTY && !wasLastInputOperator) {
            // Previous operator exists - perform partical calculation
            mCurrentOperand.setValue(
                    mCalculator.performCalculation(mPreviousOperand, mCurrentOperand, mOperator));

            // Reset the previous operand and store the new operator
            mPreviousOperand.reset();
        }

        mOperator = operator;
        wasLastInputOperator = true;
        updateDisplay();
    }

    @Override
    public void performCalculation() {
        mCalculator.performCalculation(mPreviousOperand, mCurrentOperand, mOperator);
    }

    private void updateDisplay() {
        mView.displayOperand(mCurrentOperand.getValue());
        mView.displayOperator(mOperator.toString());
    }
}
