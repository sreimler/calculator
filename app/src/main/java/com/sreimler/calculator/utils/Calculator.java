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

package com.sreimler.calculator.utils;

import com.sreimler.calculator.models.Operand;

/**
 * Performs the actual calculations.
 */
public class Calculator {

    public String add(Operand firstOperand, Operand secondOperand) {
        int result = getIntValue(firstOperand) + getIntValue(secondOperand);
        return Integer.toString(result);
    }

    public String subtract(Operand firstOperand, Operand secondOperand) {
        int result = getIntValue(firstOperand) - getIntValue(secondOperand);
        return Integer.toString(result);
    }

    public String multiply(Operand firstOperand, Operand secondOperand) {
        int result = getIntValue(firstOperand) * getIntValue(secondOperand);
        return Integer.toString(result);
    }

    public String divide(Operand firstOperand, Operand secondOperand) {
        int result = getIntValue(firstOperand) / getIntValue(secondOperand);
        return Integer.toString(result);
    }

    private int getIntValue(Operand operand) {
        return Integer.valueOf(operand.getValue());
    }
}
