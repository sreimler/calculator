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

package com.sreimler.calculator.models;

/**
 * Represents the operators available in the calculations.
 */
public enum Operator {
    EMPTY(""),
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return operator;
    }

    public static Operator getOperator(String operator) {
        Operator op = Operator.EMPTY;

        switch (operator) {
            case "+":
                op = PLUS;
                break;
            case "-":
                op = MINUS;
                break;
            case "*":
                op = MULTIPLY;
                break;
            case "/":
                op = DIVIDE;
                break;
        }

        return op;
    }
}
