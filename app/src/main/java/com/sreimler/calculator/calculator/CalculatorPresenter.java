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
    private boolean hasLastInputOperator;
    private boolean hasLastInputEquals;
    private boolean isInErrorState;

    public CalculatorPresenter(Calculator calculator,
                               CalculatorContract.View view,
                               Operand currentOperand,
                               Operand previousOperand) {
        mCalculator = calculator;
        mView = view;

        mCurrentOperand = currentOperand;
        mPreviousOperand = previousOperand;
        resetCalculator();
        updateDisplay();
    }

    @Override
    public void clearCalculation() {
        resetCalculator();
        updateDisplay();
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
        if (hasLastInputOperator) {
            // Last input was an operator - start a new operand
            mPreviousOperand.setValue(mCurrentOperand.getValue());
            mCurrentOperand.reset();
        } else if (hasLastInputEquals) {
            // Last input was calculate - start a new calculation
            resetCalculator();
        }

        mCurrentOperand.appendValue(value);
        hasLastInputOperator = false;
        hasLastInputEquals = false;
        isInErrorState = false;
        updateDisplay();
    }

    @Override
    public void appendOperator(String operator) {
        // Dont append new operators when in error state
        if (!isInErrorState) {
            if (mOperator != Operator.EMPTY && !hasLastInputOperator) {
                // Previous operator exists - perform partical calculation
                performCalculation();

                // When the partial calculation has led to an error state, stop here
                if (isInErrorState) {
                    return;
                }
            }

            mOperator = Operator.getOperator(operator);
            hasLastInputOperator = true;
            updateDisplay();
        }
    }

    @Override
    public void performCalculation() {
        switch (mOperator) {
            case PLUS:
                mCurrentOperand.setValue(mCalculator.add(mPreviousOperand, mCurrentOperand));
                break;
            case MINUS:
                mCurrentOperand.setValue(mCalculator.subtract(mPreviousOperand, mCurrentOperand));
                break;
            case MULTIPLY:
                mCurrentOperand.setValue(mCalculator.multiply(mPreviousOperand, mCurrentOperand));
                break;
            case DIVIDE:
                if (mCurrentOperand.getValue().equals(Operand.EMPTY_VALUE)) {
                    // Fordbidden division by zero - ERROR
                    switchToErrorState();
                } else {
                    mCurrentOperand.setValue(mCalculator.divide(mPreviousOperand, mCurrentOperand));
                }
                break;
        }

        // Reset the previous operand and operator
        mPreviousOperand.reset();
        mOperator = Operator.EMPTY;
        hasLastInputEquals = true;
        updateDisplay();
    }

    private void switchToErrorState() {
        mCurrentOperand.setValue(Operand.ERROR_VALUE);
        isInErrorState = true;
    }

    private void resetCalculator() {
        mCurrentOperand.reset();
        mPreviousOperand.reset();
        hasLastInputEquals = false;
        hasLastInputOperator = false;
        isInErrorState = false;
        mOperator = Operator.EMPTY;
    }

    private void updateDisplay() {
        mView.displayOperand(mCurrentOperand.getValue());
        mView.displayOperator(mOperator.toString());
    }
}
