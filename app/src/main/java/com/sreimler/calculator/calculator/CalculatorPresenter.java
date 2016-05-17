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

/**
 * Listens to user input from the {@link CalculatorActivity}, forwards calculations to
 * the {@link com.sreimler.calculator.data.Calculator} and updates the UI if required.
 */
public class CalculatorPresenter implements CalculatorContract.Presenter {

    private final Calculator mCalculator;
    private final CalculatorContract.View mView;

    public CalculatorPresenter(Calculator calculator, CalculatorContract.View view) {
        mCalculator = calculator;
        mView = view;
    }

    @Override
    public void deleteCalculation() {
        // Reset the UI calculation text
        mView.setCalculationText("");
    }
}
