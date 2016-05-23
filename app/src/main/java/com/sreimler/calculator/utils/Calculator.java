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

    /**
     * Adds two operands.
     *
     * @param firstOperand  The first summand
     * @param secondOperand The second summand
     * @return The result of the addition
     */
    public String add(Operand firstOperand, Operand secondOperand) {
        double result = getValue(firstOperand) + getValue(secondOperand);
        return formatResult(result);
    }

    /**
     * Subtracts two operands.
     *
     * @param firstOperand  The minuend
     * @param secondOperand The subtrahend
     * @return The result of the subtraction
     */
    public String subtract(Operand firstOperand, Operand secondOperand) {
        double result = getValue(firstOperand) - getValue(secondOperand);
        return formatResult(result);
    }

    /**
     * Multiplies two operands.
     *
     * @param firstOperand  The multiplicant
     * @param secondOperand The multiplier
     * @return The result of the multiplication
     */
    public String multiply(Operand firstOperand, Operand secondOperand) {
        double result = getValue(firstOperand) * getValue(secondOperand);
        return formatResult(result);
    }

    /**
     * Divides one operator by another
     *
     * @param firstOperand  The dividend
     * @param secondOperand The divisor
     * @return The result of the devision
     */
    public String divide(Operand firstOperand, Operand secondOperand) {
        double result = getValue(firstOperand) / getValue(secondOperand);
        return formatResult(result);
    }

    private double getValue(Operand operand) {
        return Double.valueOf(operand.getValue());
    }

    private String formatResult(Double res) {
        // Limit digits
        double digits = Math.pow(10, Operand.MAX_DECIMAL_DIGITS);
        res = Math.round(res * digits) / digits;

        // Split resulting float
        String result = Double.toString(res);
        String decimals = result.substring(0, result.indexOf("."));
        String fractionals = result.substring(result.indexOf(".") + 1);

        // Remove trailing zeros
        while (fractionals.length() > 0 && fractionals.substring(fractionals.length() - 1).equals("0")) {
            fractionals = fractionals.substring(0, fractionals.length() - 1);
        }

        if (fractionals.length() > 0) {
            // Result has fractionals different than zero - return them!
            return decimals + "." + fractionals;
        } else {
            return decimals;
        }
    }
}
