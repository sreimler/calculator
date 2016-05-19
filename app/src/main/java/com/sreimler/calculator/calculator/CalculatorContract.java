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

import com.sreimler.calculator.models.Operator;

/**
 * Specification of the contract between calculator view and presenter.
 */
public class CalculatorContract {

    interface View {

        void displayOperand(String calculation);

        void displayOperator(String operator);
    }

    interface Presenter {

        void clearCalculation();

        String getPreviousOperand();

        String getCurrentOperand();

        Operator getOperator();

        void appendValue(String value);

        void setOperator(String operator);

        void performCalculation();
    }
}
