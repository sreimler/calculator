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
 * Represents a single calculation operand.
 */
public class Operand {

    public static final String EMPTY_VALUE = "0";
    public static final String ERROR_VALUE = "ERROR";
    public static final int MAX_LENGTH = 10;
    public static final int MAX_DECIMAL_DIGITS = 1;

    private String mValue = EMPTY_VALUE;

    /**
     * Retrieves the value of an {@link Operand}.
     * @return The operand value
     */
    public String getValue() {
        return mValue;
    }

    /**
     * Sets the value of an {@link Operand}.
     * @param value The operand value
     */
    public void setValue(String value) {
        mValue = value;
    }

    /**
     * Appends a value to the existing {@link Operand} value.
     * @param value The value to be appended
     */
    public void appendValue(String value) {
        if (mValue.equals(EMPTY_VALUE)) {
            mValue = value;
        } else {
            mValue += value;
        }
    }

    /**
     * Resets the operand.
     */
    public void reset() {
        mValue = EMPTY_VALUE;
    }
}
